package com.rafalbiarda.medcinedontforgetversion8.ui.fragments

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.rafalbiarda.medcinedontforgetversion8.R
import com.rafalbiarda.medcinedontforgetversion8.firestore.FirestoreClass
import com.rafalbiarda.medcinedontforgetversion8.models.User
import com.rafalbiarda.medcinedontforgetversion8.util.Constants
import com.rafalbiarda.medcinedontforgetversion8.util.GlideLoader
import kotlinx.android.synthetic.main.fragment_edit_profile.*
import java.io.IOException


class EditProfileFragment : BaseFragment(), View.OnClickListener {

    val args: EditProfileFragmentArgs by navArgs()

    private lateinit var mUserDetails: User

    private var mSelectedImageFileUri: Uri? = null

    private var mUserProfileImageURL: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_edit_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mUserDetails = args.user

        et_first_name.setText(mUserDetails.firstName)
        et_last_name.setText(mUserDetails.lastName)
        et_date_of_birth.setText(mUserDetails.dateOfBirth)
        et_phone_number.setText(mUserDetails.mobile.toString())
        et_user_pin.setText(mUserDetails.pin.toString())
        GlideLoader(requireActivity()).loadUserPicture(mUserDetails.image, iv_user_photo)
        if (mUserDetails.gender == Constants.MALE) {
            rb_male.isChecked = true
        } else {
            rb_female.isChecked = true
        }


        iv_user_photo.setOnClickListener(this)
        btn_save.setOnClickListener(this)

    }

    override fun onClick(v: View?) {

        if (v != null) {
            when (v.id) {
                R.id.iv_user_photo -> {

                    if (ContextCompat.checkSelfPermission(
                            requireActivity(),
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        )
                        == PackageManager.PERMISSION_GRANTED
                    ) {

                        Constants.showImageChooser(this)
                    } else {

                        /*Requests permissions to be granted to this application. These permissions
                         must be requested in your manifest, they should not be granted to your app,
                         and they should have protection level*/

                        ActivityCompat.requestPermissions(
                            requireActivity(),
                            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                            Constants.READ_STORAGE_PERMISSION_CODE
                        )
                    }

                }

                R.id.btn_save -> {
                    if (validateUserProfileDetails()) {

                        if (mSelectedImageFileUri != null) {
                            FirestoreClass().uploadImageToCloudStorage(
                                requireActivity(),
                                this,
                                mSelectedImageFileUri
                            )
                        } else {
                            updateUserProfileDetails()
                        }
                    }
                }
            }
        }
    }

    private fun updateUserProfileDetails() {
        val userHashMap = HashMap<String, Any>()
        val phoneNumber = et_phone_number.text.toString().trim { it <= ' ' }.toLong()
        val firstName = et_first_name.text.toString().trim { it <= ' ' }
        val lastName = et_last_name.text.toString().trim { it <= ' ' }
        val dateOfBirth = et_date_of_birth.text.toString().trim { it <= ' ' }
        val pin = et_user_pin.text.toString().trim { it <= ' ' }.toInt()

        val gender = if (rb_male.isChecked) {
            Constants.MALE
        } else {
            Constants.FEMALE
        }

        if (gender != mUserDetails.gender) {
            userHashMap[Constants.GENDER] = gender
        }
        if (phoneNumber != mUserDetails.mobile) {
            userHashMap[Constants.MOBILE] = phoneNumber
        }
        if (firstName != mUserDetails.firstName) {
            userHashMap[Constants.FIRST_NAME] = firstName
        }
        if (lastName != mUserDetails.lastName) {
            userHashMap[Constants.LAST_NAME] = lastName
        }
        if (dateOfBirth != mUserDetails.dateOfBirth) {
            userHashMap[Constants.DATE_OF_BIRTH] = dateOfBirth
        }
        if (pin != mUserDetails.pin) {
            userHashMap[Constants.PIN] = pin
        }
        if (mUserDetails.profileCompleted == 0) {
            userHashMap[Constants.PROFILE_COMPLETED] = 1
        }

        if (mUserProfileImageURL.isNotEmpty()) {
            userHashMap[Constants.IMAGE] = mUserProfileImageURL
            showLog(mUserProfileImageURL)
        }

        showProgressDialog("Please Wait..")

        FirestoreClass().updateUserProfileData(
            this,
            userHashMap
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.READ_STORAGE_PERMISSION_CODE) {
            //If permission is granted
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Constants.showImageChooser(this)
            } else {
                //Displaying another toast if permission is not granted
                showToast("You just denied the permission for storage. You can also allow it from settings")
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        showToast("onActivityResult")
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Constants.PICK_IMAGE_REQUEST_CODE) {
                if (data != null) {
                    try {
                        // The uri of selected image from phone storage.
                        mSelectedImageFileUri = data.data!!

                        GlideLoader(requireActivity()).loadUserPicture(
                            mSelectedImageFileUri!!,
                            iv_user_photo
                        )
                    } catch (e: IOException) {
                        e.printStackTrace()
                        showToast("Image selection Failed!")
                    }
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            // A log is printed when user close or cancel the image selection.
            Log.e("Request Cancelled", "Image selection cancelled")
        }
    }

    private fun validateUserProfileDetails(): Boolean {
        return when {

            // We have kept the user profile picture is optional.
            // The FirstName, LastName, and Email Id are not editable when they come from the login screen.
            // The Radio button for Gender always has the default selected value.

            // Check if the mobile number is not empty as it is mandatory to enter.
            TextUtils.isEmpty(et_first_name.text.toString().trim { it <= ' ' }) -> {
                showSnackBar("Please enter your first name")
                false
            }
            TextUtils.isEmpty(et_last_name.text.toString().trim { it <= ' ' }) -> {
                showSnackBar("Please enter your last name")
                false
            }
            TextUtils.isEmpty(et_date_of_birth.text.toString().trim { it <= ' ' }) -> {
                showSnackBar("Please enter your date of birth")
                false
            }
            TextUtils.isEmpty(et_phone_number.text.toString().trim { it <= ' ' }) -> {
                showSnackBar("Please enter your phone number")
                false
            }
            TextUtils.isEmpty(et_user_pin.text.toString().trim { it <= ' ' }) -> {
                showSnackBar("Please enter PIN")
                false
            }
            else -> {
                true
            }
        }
    }

    fun saveEditProfile() {
        hideProgressDialog()
        showToast("Your profile is updated")
        findNavController().navigate(R.id.action_editProfileFragment_to_medicineFragment)
    }

    fun imageUploadSuccess(imageURL: String) {
        mUserProfileImageURL = imageURL
        if (validateUserProfileDetails()) {
            updateUserProfileDetails()
        }
    }

}