package com.rafalbiarda.medcinedontforgetversion8.firebase

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.rafalbiarda.medcinedontforgetversion8.model.Card
import com.rafalbiarda.medcinedontforgetversion8.model.Doctor
import com.rafalbiarda.medcinedontforgetversion8.model.Medicine
import com.rafalbiarda.medcinedontforgetversion8.model.User
import com.rafalbiarda.medcinedontforgetversion8.util.Constants

class FirebaseRepository {

    //Access instance of Firebase tools from Activity
    private val storage = FirebaseStorage.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val cloud = FirebaseFirestore.getInstance()


    fun getUserData(): LiveData<User> {
        val cloudResult = MutableLiveData<User>()
        val uid = auth.currentUser?.uid
        cloud.collection("users").document(uid!!).get()
            .addOnSuccessListener {
                val user = it.toObject(User::class.java)
                cloudResult.postValue(user)
            }
            .addOnFailureListener { }
        return cloudResult
    }


    
    fun getUserCards(): MutableLiveData<List<Card>>
    {
        val cloudResult = MutableLiveData<List<Card>>()
        val uid = auth.currentUser?.uid
        val collectionCalendarCards = cloud.collection(Constants.USERS).document(uid!!).collection("CalendarCards")

        collectionCalendarCards.addSnapshotListener { querySnapshot, firebaseFirestoreException ->

            firebaseFirestoreException?.let {
                Log.e("Test1", it.message.toString())
                return@addSnapshotListener
            }
            val cardList = mutableListOf<Card>()
            querySnapshot?.let {

                val documentListOfCards = querySnapshot.documents
                documentListOfCards.forEach {
                    var card = it.toObject(Card::class.java)!!
                    card.id = it.id
                    cardList.add(card)
                }

            }
            cloudResult.postValue(cardList)
        }

        return cloudResult
    }


    fun getUserMedicineList() : MutableLiveData<List<Medicine>>
    {
        val cloudResult = MutableLiveData<List<Medicine>>()
        val uid = auth.currentUser?.uid
        val collectionCalendarCards = cloud.collection(Constants.USERS).document(uid!!).collection("MedicineList")

        collectionCalendarCards.addSnapshotListener { querySnapshot, firebaseFirestoreException ->

            firebaseFirestoreException?.let {
                Log.e("Test1", it.message.toString())
                return@addSnapshotListener
            }
            querySnapshot?.let {

                val documentListOfCards = querySnapshot.documents
                val medicineList = mutableListOf<Medicine>()

                documentListOfCards.forEach {
                    val medicine = it.toObject(Medicine::class.java)!!
                    medicineList.add(medicine)
                }

                cloudResult.postValue(medicineList)
            }
        }

        return cloudResult
    }

    fun getUserDoctorList() : MutableLiveData<List<Doctor>>
    {
        val cloudResult = MutableLiveData<List<Doctor>>()
        val uid = auth.currentUser?.uid
        val collectionCalendarCards = cloud.collection(Constants.USERS).document(uid!!).collection("DoctorList")

        collectionCalendarCards.addSnapshotListener { querySnapshot, firebaseFirestoreException ->

            firebaseFirestoreException?.let {
                Log.e("Test1", it.message.toString())
                return@addSnapshotListener
            }
            querySnapshot?.let {

                val documentListOfCards = querySnapshot.documents
                val doctorList = mutableListOf<Doctor>()

                documentListOfCards.forEach {
                    val doctor = it.toObject(Doctor::class.java)!!
                    doctorList.add(doctor)
                }

                cloudResult.postValue(doctorList)
            }
        }
        return cloudResult
    }



}