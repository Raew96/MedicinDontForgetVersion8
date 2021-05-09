package com.rafalbiarda.medcinedontforgetversion8.ui.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import com.rafalbiarda.medcinedontforgetversion8.R
import com.rafalbiarda.medcinedontforgetversion8.firebase.FirestoreClass
import com.rafalbiarda.medcinedontforgetversion8.model.Doctor
import kotlinx.android.synthetic.main.fragment_add_doctor.*


class AddDoctorsFragment : BaseFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_doctor, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_doctor_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.done -> {
                addUserDoctor()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun addUserDoctor() {

        val firstName = et_first_name.text.toString().trim { it <= ' ' }
        val lastName = et_last_name.text.toString().trim { it <= ' ' }
        val email = et_email.text.toString().trim { it <= ' ' }
        val phoneNumber = et_phone_number.text.toString().trim { it <= ' ' }
        val specialization = et_specialization.text.toString().trim { it <= ' ' }

        val doctor = Doctor("", firstName, lastName, email, phoneNumber.toLong(), specialization)

        FirestoreClass().addDoctorToUserDoctorList(doctor)

        showSnackBar("Doctor Added")

    }


    private fun validateDoctorDetails(): Boolean {
        return when {

            TextUtils.isEmpty(et_first_name.text.toString().trim { it <= ' ' }) -> {
                showSnackBar("Please enter doctor first name")
                false
            }
            TextUtils.isEmpty(et_last_name.text.toString().trim { it <= ' ' }) -> {
                showSnackBar("Please enter doctor last name")
                false
            }
            TextUtils.isEmpty(et_email.text.toString().trim { it <= ' ' }) -> {
                showSnackBar("Please enter doctor email")
                false
            }
            TextUtils.isEmpty(et_phone_number.text.toString().trim { it <= ' ' }) -> {
                showSnackBar("Please enter doctor phone number")
                false
            }
            TextUtils.isEmpty(et_specialization.text.toString().trim { it <= ' ' }) -> {
                showSnackBar("Please enter doctor specialization")
                false
            }
            else -> {
                true
            }
        }


    }


}