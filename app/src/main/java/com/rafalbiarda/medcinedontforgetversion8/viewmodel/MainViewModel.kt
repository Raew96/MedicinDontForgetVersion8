package com.rafalbiarda.medcinedontforgetversion8.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafalbiarda.medcinedontforgetversion8.firebase.FirebaseRepository
import com.rafalbiarda.medcinedontforgetversion8.model.*
import kotlinx.coroutines.launch
import java.util.*

class MainViewModel: ViewModel() {

    private val repository = FirebaseRepository()
    lateinit var  userCards : MutableLiveData<List<Card>>

    var userMedicineList : MutableLiveData<List<Medicine>> = MutableLiveData()

    var actualCard : MutableLiveData<Card> = MutableLiveData()

    var healthCards : MutableLiveData<Health> = MutableLiveData()

    var userDoctorList: MutableLiveData<List<Doctor>> = MutableLiveData()

    var dateSetByUser : MutableLiveData<Date> = MutableLiveData()


    fun setDateSetByUser(date: Date) =
        viewModelScope.launch {
            dateSetByUser.postValue(date)
        }

    fun setActualCard(card: Card ) =
    viewModelScope.launch {
        actualCard.postValue(card)
    }

    fun setHealthCards(healthCardsList : Health) =
        viewModelScope.launch {
            healthCards.postValue(healthCardsList)
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

