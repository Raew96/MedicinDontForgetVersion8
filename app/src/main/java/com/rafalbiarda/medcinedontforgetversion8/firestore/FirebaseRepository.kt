package com.rafalbiarda.medcinedontforgetversion8.firestore

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.rafalbiarda.medcinedontforgetversion8.models.Card
import com.rafalbiarda.medcinedontforgetversion8.models.Doctor
import com.rafalbiarda.medcinedontforgetversion8.models.Medicine
import com.rafalbiarda.medcinedontforgetversion8.models.User
import com.rafalbiarda.medcinedontforgetversion8.util.Constants

class FirebaseRepository {

    private val storage = FirebaseStorage.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val cloud = FirebaseFirestore.getInstance()

    fun LogIn()
    {
        auth.signInWithEmailAndPassword("rafael.gorzow@gmail.com", "123456")
            .addOnCompleteListener { task ->


                if (task.isSuccessful) {


                } else {
                }

            }
    }

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
            querySnapshot?.let {

                val documentListOfCards = querySnapshot.documents
                val cardList = mutableListOf<Card>()

                documentListOfCards.forEach {
                    val card = it.toObject(Card::class.java)!!
                    cardList.add(card)
                }

                cloudResult.postValue(cardList)
            }
        }

        /*cloud.collection(Constants.USERS).document(uid!!).collection("CalendarCards").get()
            .addOnSuccessListener { snapshot ->

                val doucmentListOfCards = snapshot.documents
                val cardlist = mutableListOf<Card>()


                doucmentListOfCards.forEach {
                    val card = it.toObject(Card::class.java)!!
                        cardlist.add(card)
                }

                cloudResult.postValue(cardlist)
            }
            .addOnFailureListener {  }*/

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