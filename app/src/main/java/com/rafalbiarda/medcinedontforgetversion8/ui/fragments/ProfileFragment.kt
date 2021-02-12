package com.rafalbiarda.medcinedontforgetversion8.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.rafalbiarda.medcinedontforgetversion8.R
import com.rafalbiarda.medcinedontforgetversion8.firestore.FirestoreClass
import com.rafalbiarda.medcinedontforgetversion8.models.User
import com.rafalbiarda.medcinedontforgetversion8.util.Constants
import com.rafalbiarda.medcinedontforgetversion8.util.GlideLoader
import com.rafalbiarda.medcinedontforgetversion8.viewmodels.MainViewModel
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
            FirebaseAuth.getInstance().signOut()
            showToast("Sign out")
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