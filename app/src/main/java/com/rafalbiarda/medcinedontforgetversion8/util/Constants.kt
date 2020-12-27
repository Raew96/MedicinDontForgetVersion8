package com.rafalbiarda.medcinedontforgetversion8.util

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.webkit.MimeTypeMap
import androidx.fragment.app.Fragment

object Constants {

    const val USERS: String = "users"

    const val USER_PREFERENCES: String = "USER_PREFERENCES"
    const val LOGGED_IN_USERNAME: String = "LOGGED_IN_USERNAME"

    const val EXTRA_USER_DETAILS: String = "EXTRA_USER_DETAILS"

    //A unique code for asking the Read Storage Permission using this we will be check and identify in the method onRequestPermissionsResult in the Base Activity.
    const val READ_STORAGE_PERMISSION_CODE = 2

    // A unique code of image selection from Phone Storage.
    const val PICK_IMAGE_REQUEST_CODE = 2

    // Constant variables for Gender
    const val MALE: String = "MALE"
    const val FEMALE: String = "FEMALE"

    // Firebase database field names
    const val MOBILE: String = "mobile"
    const val GENDER: String = "gender"
    const val FIRST_NAME = "firstName"
    const val LAST_NAME = "lastName"
    const val PROFILE_COMPLETED = "profileCompleted"
    const val PIN = "pin"
    const val DATE_OF_BIRTH = "dateOfBirth"
    const val IMAGE = "image"
    const val USER_PROFILE_IMAGE:String = "User_Profile_Image"


    fun showImageChooser(fragment: Fragment) {
        // An intent for launching the image selection of phone storage.
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        // Launches the image selection of phone storage using the constant code.
        fragment.startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST_CODE)
    }

    fun getFileExtension(activity: Activity, uri: Uri?): String? {
        return MimeTypeMap.getSingleton()
            .getExtensionFromMimeType(activity.contentResolver.getType(uri!!))
    }
}