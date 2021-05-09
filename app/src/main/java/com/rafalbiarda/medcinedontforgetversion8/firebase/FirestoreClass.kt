package com.rafalbiarda.medcinedontforgetversion8.firebase

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.rafalbiarda.medcinedontforgetversion8.model.*
import com.rafalbiarda.medcinedontforgetversion8.ui.fragments.*
import com.rafalbiarda.medcinedontforgetversion8.util.Constants
import java.util.*
import kotlin.collections.HashMap

class FirestoreClass {

    private val mFireStore = FirebaseFirestore.getInstance()

    fun registerUser(fragment: RegisterFragment, userInfo: User) {

        // The "users" is collection name. If the collection is already created then it will not create the same one again.
        mFireStore.collection(Constants.USERS)
            // Document ID for users fields. Here the document it is the User ID.
            .document(userInfo.id)
            // Here the userInfo are Field and the SetOption is set to merge. It is for if we wants to merge later on instead of replacing the fields.
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                // Here calnction of base activity for transferring the result to it.
                fragment.userRegistrationSuccess()
            }
            .addOnFailureListener { _ ->
                fragment.hideProgressDialog()
            }
    }

    fun getCurrentUserID(): String {
        // An Instance of currentUser using FirebaseAuth
        val currentUser = FirebaseAuth.getInstance().currentUser

        // A variable to assign the currentUserId if it is not null or else it will be blank.
        var currentUserID = ""
        if (currentUser != null) {
            currentUserID = currentUser.uid
        }

        return currentUserID
    }


    fun getUserDetails(fragment: Fragment) {

        // Here we pass the collection name from which we wants the data.
        mFireStore.collection(Constants.USERS)
            // The document id to get the Fields of user.
            .document(getCurrentUserID())
            .get()
            .addOnSuccessListener { document ->

                Log.i(fragment.javaClass.simpleName, document.toString())


                // Here we have received the document snapshot which is converted into the User Data model object.
                val user = document.toObject(User::class.java)!!


                val sharedPreferences =
                    fragment.requireActivity().getSharedPreferences(
                        Constants.USER_PREFERENCES,
                        Context.MODE_PRIVATE
                    )

                // Create an instance of the editor which is help us to edit the SharedPreference.
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putString(
                    Constants.LOGGED_IN_USERNAME,
                    "${user.firstName} ${user.lastName}"
                )
                editor.apply()

                // START
                when (fragment) {
                    is LoginFragment -> {
                        // Call a function of base activity for transferring the result to it.
                        fragment.userLoggedInSuccess(user)
                    }

                    is ProfileFragment -> {
                        fragment.userDetailsSuccess(user)
                    }
                }
                // END
            }
            .addOnFailureListener { e ->
                // Hide the progress dialog if there is any error. And print the error in log.
                when (fragment) {
                    is LoginFragment -> {
                        fragment.hideProgressDialog()
                    }
                    is ProfileFragment -> {
                        fragment.hideProgressDialog()
                    }
                }

                Log.e(
                    fragment.javaClass.simpleName,
                    "Error while getting user details.",
                    e
                )
            }
    }

    fun getUserPin() : Int
    {
        var userPIN = 1234
        var userer = getCurrentUserID()
        mFireStore.collection(Constants.USERS)
            // The document id to get the Fields of user.
            .document(getCurrentUserID())
            .get()
            .addOnSuccessListener { document ->

                // Here we have received the document snapshot which is converted into the User Data model object.
                val user = document.toObject(User::class.java)!!
                userPIN = user.pin
            }
            .addOnFailureListener { exception ->
                Log.d("Error", "get failed with ", exception)

            }

        return userPIN
    }

    fun updateUserProfileData(fragment: Fragment, userHashMap: HashMap<String, Any>) {
        // Collection Name
        mFireStore.collection(Constants.USERS)
            // Document ID against which the data to be updated. Here the document id is the current logged in user id.
            .document(getCurrentUserID())
            // A HashMap of fields which are to be updated.
            .update(userHashMap)
            .addOnSuccessListener {

                // Notify the success result.
                when (fragment) {
                    is EditProfileFragment -> {
                        // Call a function of base activity for transferring the result to it.
                        fragment.saveEditProfile()
                    }
                }
            }
            .addOnFailureListener { e ->

                when (fragment) {
                    is EditProfileFragment -> {
                        fragment.hideProgressDialog()
                    }
                }

                Log.e(
                    fragment.javaClass.simpleName,
                    "Error while updating the user details.",
                    e
                )
            }
    }

    fun addDoctorToUserDoctorList(doctor: Doctor) {
        val cardsRef = mFireStore.collection(Constants.USERS)
            .document(getCurrentUserID()).collection("DoctorList")

        cardsRef.document().set(doctor, SetOptions.merge())
    }

    fun AddMedicineReminder(
        date: Date,
        cardList: List<Card>,
        medicineReminderList: List<MedicineReminder>
    ) {
        if (cardList.any {
                isSameDay(it.date!!, date)
            }) {
            val cardsRef = mFireStore.collection(Constants.USERS)
                .document(getCurrentUserID()).collection("CalendarCards")

            var newCard = Card()
            var newMedicineReminderList = mutableListOf<MedicineReminder>()

            cardList.forEach {
                if (it.date == date) {
                    newCard = it
                    it.medicineReminderList.forEach {
                        newMedicineReminderList.add(it)
                    }
                }
            }
            medicineReminderList.forEach {
                newMedicineReminderList.add(it)
            }

            newCard.medicineReminderList = newMedicineReminderList

            cardsRef.document(newCard.id).set(newCard, SetOptions.merge())

        } else {

            val cardsRef = mFireStore.collection(Constants.USERS)
                .document(getCurrentUserID()).collection("CalendarCards")
            val card =
                Card(date = date, medicineReminderList = medicineReminderList)

            cardsRef.document().set(card)
        }
    }

    fun deleteMedicineReminder(
        card: Card, medicineReminder: MedicineReminder
    )
    {
        val cardsRef = mFireStore.collection(Constants.USERS)
            .document(getCurrentUserID()).collection("CalendarCards")

        var newCard = card
        card.medicineReminderList.forEachIndexed { index, element ->
            if(element == medicineReminder )
            {
                var tempMedReminderList = newCard.medicineReminderList.toMutableList()
                tempMedReminderList.removeAt(index)
                newCard.medicineReminderList = tempMedReminderList

                Log.d("XDDd", "XDD")

            }
        }

        cardsRef.document(card.id).set(newCard, SetOptions.merge())
    }

    fun takeMedicineReminder(
        card: Card, medicineReminder: MedicineReminder
    )
    {
        val cardsRef = mFireStore.collection(Constants.USERS)
            .document(getCurrentUserID()).collection("CalendarCards")

        var newCard = card
        card.medicineReminderList.forEachIndexed { index, element ->
            if(element == medicineReminder )
            {
                var tempMedReminderList = newCard.medicineReminderList.toMutableList()
                tempMedReminderList[index].isTaken = true
                newCard.medicineReminderList = tempMedReminderList

            }
        }
        cardsRef.document(card.id).set(newCard, SetOptions.merge())
    }

    fun addHealth(card: Card )
    {
        val cardsRef = mFireStore.collection(Constants.USERS)
            .document(getCurrentUserID()).collection("CalendarCards")
        if(card.id != "") {
            cardsRef.document(card.id).set(card, SetOptions.merge())
        }else
        {
            cardsRef.document().set(card, SetOptions.merge())
        }

    }



    fun isSameDay(date1: Date, date2: Date): Boolean
    {
        val cal1 = Calendar.getInstance()
        val cal2 = Calendar.getInstance()
        cal1.time = date1
        cal2.time = date2

        return cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)

    }


    fun addMedicineToUserList(medicine: Medicine)
    {
        val cardsRef = mFireStore.collection(Constants.USERS)
            .document(getCurrentUserID()).collection("MedicineList")

        cardsRef.document().set(medicine, SetOptions.merge())
    }

    /*
    fun testing()
    {
        //Adding subcollections in firestore
        val cardsRef = mFireStore.collection(Constants.USERS)
            .document(getCurrentUserID()).collection("CalendarCards")

        val med = Medicine(null, "Aspiryna", "Aspirytex", "some instructions", 60, "table")

        val medRem = MedicineReminder(medicine = med)
        val medlist = listOf(medRem,medRem,medRem)

        val time = Calendar.getInstance()
        val card1 = Card(date = time.time, medicineReminderList = medlist)

        time.add(Calendar.DAY_OF_YEAR, -1)
        val card2 = Card(date = time.time, medicineReminderList = medlist)

        time.add(Calendar.DAY_OF_YEAR, -2)
        val card3 = Card(date = time.time, medicineReminderList = medlist)


        cardsRef.document().set(card1, SetOptions.merge())
        cardsRef.document().set(card2, SetOptions.merge())
        cardsRef.document().set(card3, SetOptions.merge())
    }
    */

    fun testing2()
    {

       /* //Adding subcollections in firestore
        val cardsRef = mFireStore.collection(Constants.USERS)
            .document(getCurrentUserID()).collection("CalendarCards").get().addOnSuccessListener {

                for(i in it.documents)
                {
                    val cardsRef2 = mFireStore.collection(Constants.USERS)
                        .document(getCurrentUserID()).collection("CalendarCards").document(i.id).collection("MedicineReminders")

                    val med = Medicine(null, "Aspiryna", "Aspirytex", "some instructions", 60, 2, "table")
                    //val medRem = MedicineReminder(Calendar.getInstance().time, "med")
                    cardsRef2.document().set(medRem, SetOptions.merge())
                }
            }
*/
    }

    fun test3(fragment: Fragment) : List<MedicineReminder>
    {
        Log.e(fragment.javaClass.simpleName, "Elo" )
        val reminderList = mutableListOf<MedicineReminder>()
        val cardsRef = mFireStore.collection(Constants.USERS)
            .document(getCurrentUserID()).collection("CalendarCards").get().addOnCompleteListener(
                OnCompleteListener {

                    task ->

                    if(task.isSuccessful)
                    {
                        val myList = task.getResult()!!.documents

                        myList.forEach {

                            val reminder = it.toObject(MedicineReminder::class.java)!!

                            Log.d("Test", "${reminder.date.toString()}")

                            reminderList.add(reminder)
                        }

                    }

                })
        return reminderList
    }


    fun uploadImageToCloudStorage(activity: Activity, fragment: Fragment, imageFileURI: Uri?) {

        //getting the storage reference
        val sRef: StorageReference = FirebaseStorage.getInstance().reference.child(
            Constants.USER_PROFILE_IMAGE + System.currentTimeMillis() + "."
                    + Constants.getFileExtension(
                activity,
                imageFileURI
            )
        )

        //adding the file to reference
        sRef.putFile(imageFileURI!!)
            .addOnSuccessListener { taskSnapshot ->
                // The image upload is success
                Log.e(
                    "Firebase Image URL",
                    taskSnapshot.metadata!!.reference!!.downloadUrl.toString()
                )

                // Get the downloadable url from the task snapshot
                taskSnapshot.metadata!!.reference!!.downloadUrl
                    .addOnSuccessListener { uri ->
                        Log.e("Downloadable Image URL", uri.toString())

                        when (fragment) {
                            is EditProfileFragment -> {
                                Log.e("Downloadable Image URL", "uri.toString()")
                                fragment.imageUploadSuccess(uri.toString())
                            }
                        }
                        // END
                    }
            }
            .addOnFailureListener { exception ->

                // Hide the progress dialog if there is any error. And print the error in log.
                when (fragment) {
                    is EditProfileFragment -> {
                        fragment.hideProgressDialog()
                    }
                }

                Log.e(
                    activity.javaClass.simpleName,
                    exception.message,
                    exception
                )
            }
    }
}