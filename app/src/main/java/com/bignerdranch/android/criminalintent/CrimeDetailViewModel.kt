package com.bignerdranch.android.criminalintent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.util.*

class CrimeDetailViewModel: ViewModel() {

    private val crimeRepository =  CrimeRepository.get()
    private val crimeIdLiveData = MutableLiveData<UUID>()

    var crimeLiveData: LiveData<Crime?> =
        Transformations.switchMap(crimeIdLiveData){crimeId->
            crimeRepository.getCrime(crimeId)
        }

    fun loadCrime(crimeId: UUID){
        crimeIdLiveData.value = crimeId
    }

    fun saveCrime(crime: Crime){
        crimeRepository.upDateCrime(crime)
    }

    /**
     * IMPORTANT! CHAPTER 13 CHALLENGE! WE CAN NOT USE THE PRELOADED
     * DATABASE THAT WE HAVE BEEN USING FOR THIS APP. WE HAVE TO GO INTO DEVICE
     * FILE EXPLORER AND DELETE THE DATABASES FOLDER, THEN, just to get the challenge done,
     * we will add 10 random crimes to our db so our recycler view has something to load.
     */
    fun addAFewCrimes(){
        for(i in 0..10){
            val crime = Crime()
            crimeRepository.addCrime(crime)
        }
    }
}