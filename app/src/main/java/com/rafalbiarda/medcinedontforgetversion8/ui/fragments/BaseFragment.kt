package com.rafalbiarda.medcinedontforgetversion8.ui.fragments

import android.app.Dialog
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.rafalbiarda.medcinedontforgetversion8.R
import kotlinx.android.synthetic.main.dialog_progress.*

open class BaseFragment : Fragment(){

    private lateinit var mProgressDialog: Dialog

    fun showSnackBar(message: String)
    {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG).show()
    }

    fun showToast(message: String)
    {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    fun showLog(message: String)
    {
        Log.e("ImageUpload", message)
    }



    fun showProgressDialog(text: String) {
        mProgressDialog = Dialog(requireContext())
        mProgressDialog.setContentView(R.layout.dialog_progress)
        mProgressDialog.tv_progress_text.text = text
        mProgressDialog.setCancelable(false)
        mProgressDialog.setCanceledOnTouchOutside(false)
        mProgressDialog.show()
    }

    fun hideProgressDialog() {
        mProgressDialog.dismiss()
    }
}