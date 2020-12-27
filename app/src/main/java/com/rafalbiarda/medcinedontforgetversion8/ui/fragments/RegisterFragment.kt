package com.rafalbiarda.medcinedontforgetversion8.ui.fragments

import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.rafalbiarda.medcinedontforgetversion8.R
import com.rafalbiarda.medcinedontforgetversion8.firestore.FirestoreClass
import com.rafalbiarda.medcinedontforgetversion8.models.User
import kotlinx.android.synthetic.main.fragment_register.*


class RegisterFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_register.setOnClickListener {
            registerUser()
        }

        tv_login.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    private fun validateRegister() : Boolean
    {
        return when{
            TextUtils.isEmpty(et_first_name.text.toString().trim { it <= ' ' }) -> {
                val message = resources.getString(R.string.err_msg_enter_first_name)
                showSnackBar(message)
                false
            }

            TextUtils.isEmpty(et_last_name.text.toString().trim { it <= ' ' }) -> {
                val message = resources.getString(R.string.err_msg_enter_last_name)
                showSnackBar(message)
                false
            }

            TextUtils.isEmpty(et_email.text.toString().trim { it <= ' ' }) -> {
                val message = resources.getString(R.string.err_msg_enter_email)
                showSnackBar(message)
                false
            }

            TextUtils.isEmpty(et_password.text.toString().trim { it <= ' ' }) -> {
                val message = resources.getString(R.string.err_msg_enter_password)
                showSnackBar(message)
                false
            }

            TextUtils.isEmpty(et_confirm_password.text.toString().trim { it <= ' ' }) -> {
                val message = resources.getString(R.string.err_msg_enter_confirm_password)
                showSnackBar(message)
                false
            }

            et_password.text.toString().trim { it <= ' ' } != et_confirm_password.text.toString()
                .trim { it <= ' ' } -> {
                val message = resources.getString(R.string.err_msg_password_and_confirm_password_mismatch)
                showSnackBar(message)
                false
            }
            else -> {
                true
            }
        }
    }

    private fun registerUser() {

        // Check with validate function if the entries are valid or not.
        if (validateRegister()) {

            showProgressDialog("Please Wait..")
            // Show the progress dialog.

            val email: String = et_email.text.toString().trim { it <= ' ' }
            val password: String = et_password.text.toString().trim { it <= ' ' }

            // Create an instance and create a register a user with email and password.
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->

                        // Hide the progress dialog
                        hideProgressDialog()

                        // If the registration is successfully done
                        if (task.isSuccessful) {

                            // Firebase registered user
                            val firebaseUser: FirebaseUser = task.result!!.user!!

                            val user = User(
                                firebaseUser.uid,
                                et_first_name.text.toString().trim { it <= ' ' },
                                et_last_name.text.toString().trim { it <= ' ' },
                                et_email.text.toString().trim { it <= ' ' }
                            )

                            FirestoreClass().registerUser(this, user)
                            // END
                        } else {
                            // If the registering is not successful then show error message.
                            showSnackBar(task.exception!!.message.toString())
                        }
                    }
        }
    }

    fun userRegistrationSuccess() {

        hideProgressDialog()

        showToast("Register success!")

        FirebaseAuth.getInstance().signOut()

        requireActivity().onBackPressed()
    }

}