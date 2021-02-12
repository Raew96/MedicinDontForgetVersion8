package com.rafalbiarda.medcinedontforgetversion8.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafalbiarda.medcinedontforgetversion8.firestore.FirebaseRepository
import com.rafalbiarda.medcinedontforgetversion8.models.Card
import com.rafalbiarda.medcinedontforgetversion8.models.Doctor
import com.rafalbiarda.medcinedontforgetversion8.models.Medicine
import com.rafalbiarda.medcinedontforgetversion8.models.MedicineReminder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
class MainViewModel: ViewModel() {

    private val repository = FirebaseRepository()
    lateinit var  userCards : MutableLiveData<List<Card>>

    var userMedicineList : MutableLiveData<List<Medicine>> = MutableLiveData()

    var redmindersMeds : MutableLiveData<List<MedicineReminder>> = MutableLiveData()

    var userDoctorList: MutableLiveData<List<Doctor>> = MutableLiveData()

    fun setReminderMedsList(merRemList : List<MedicineReminder>) =
    viewModelScope.launch {
        redmindersMeds.postValue( merRemList)
    }

    fun getUserCards() : LiveData<List<Card>>
    {
        return userCards
    }

    fun setUserCards()
    {
        userCards = repository.getUserCards()
    }

    fun setUserMedicineList()
    {
        userMedicineList = repository.getUserMedicineList()
    }

    fun setUserDoctorList()
    {
        userDoctorList = repository.getUserDoctorList()
    }







}

