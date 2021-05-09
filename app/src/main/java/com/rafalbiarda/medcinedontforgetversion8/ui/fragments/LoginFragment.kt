package com.rafalbiarda.medcinedontforgetversion8.ui.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.rafalbiarda.medcinedontforgetversion8.R
import com.rafalbiarda.medcinedontforgetversion8.firebase.FirestoreClass
import com.rafalbiarda.medcinedontforgetversion8.model.User
import com.rafalbiarda.medcinedontforgetversion8.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.et_email
import kotlinx.android.synthetic.main.fragment_login.et_password

class LoginFragment : BaseFragment(), View.OnClickListener {

    lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        tv_register.setOnClickListener(this)
        btn_login.setOnClickListener(this)
        tv_forgot_password.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {

                R.id.tv_forgot_password -> {
                    findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
                }

                R.id.btn_login -> {
                    logInRegisteredUser()
                }

                R.id.tv_register -> {
                    findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
                }
            }
        }
    }

    private fun validateLoginDetails(): Boolean {
        return when {
            TextUtils.isEmpty(et_email.text.toString().trim { it <= ' ' }) -> {
                showSnackBar(resources.getString(R.string.err_msg_enter_email))
                false
            }
            TextUtils.isEmpty(et_password.text.toString().trim { it <= ' ' }) -> {
                showSnackBar(resources.getString(R.string.err_msg_enter_password))
                false
            }
            else -> {
                true
            }
        }
    }

    private fun logInRegisteredUser() {

        if (validateLoginDetails()) {

            showProgressDialog("Please Wait..")

            val email: String = et_email.text.toString().trim { it <= ' ' }
            val password: String = et_password.text.toString().trim { it <= ' ' }

            // Create an instance and create a register a user with email and password.
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->

                    hideProgressDialog()

                    if (task.isSuccessful) {

                        FirestoreClass().getUserDetails(this)

                    } else {
                        hideProgressDialog()
                        showSnackBar(task.exception!!.message.toString())
                    }

                }
        }
    }

    fun userLoggedInSuccess(user: User)
    {
        hideProgressDialog()

        if(user.profileCompleted == 0)
        {
            val action = LoginFragmentDirections.actionLoginFragmentToEditProfileFragment(user)
            findNavController().navigate(action)

        }
        else
        {
            viewModel.setUserCards()
            viewModel.setUserMedicineList()
            viewModel.setUserDoctorList()
            findNavController().navigate(R.id.action_loginFragment_to_medicineFragment)
        }
    }

}