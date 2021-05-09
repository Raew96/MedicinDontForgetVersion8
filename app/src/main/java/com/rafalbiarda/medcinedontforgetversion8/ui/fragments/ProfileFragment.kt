package com.rafalbiarda.medcinedontforgetversion8.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.rafalbiarda.medcinedontforgetversion8.R
import com.rafalbiarda.medcinedontforgetversion8.firebase.FirestoreClass
import com.rafalbiarda.medcinedontforgetversion8.model.User
import com.rafalbiarda.medcinedontforgetversion8.util.GlideLoader
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : BaseFragment() {

    private lateinit var mUserDetails: User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        tv_edit_profile.setOnClickListener {
            val action =  ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment(mUserDetails)
            findNavController().navigate(action)


        }

        btn_change_pin.setOnClickListener {
           // FirestoreClass().testing()

        }
    }

    override fun onResume() {
        super.onResume()
        getUserDetails()
    }

    private fun getUserDetails()
    {
        showProgressDialog("Please Wait")

        FirestoreClass().getUserDetails(this)

    }

    fun userDetailsSuccess(user: User) {

        mUserDetails = user
        // Hide the progress dialog
        hideProgressDialog()

        // Load the image using the Glide Loader class.
        GlideLoader(requireActivity()).loadUserPicture(user.image, iv_user_photo)

        tv_first_name.text = user.firstName
        tv_last_name.text = user.lastName
        tv_gender.text = user.gender
        tv_date_of_birth.text = user.dateOfBirth
        tv_phone_number.text = "${user.mobile}"

    }
}