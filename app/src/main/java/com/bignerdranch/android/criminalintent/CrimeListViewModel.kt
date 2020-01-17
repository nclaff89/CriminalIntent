package com.bignerdranch.android.criminalintent

import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat

class CrimeListViewModel: ViewModel() {

    private val crimeRepository = CrimeRepository.get()

    val crimeListLiveData = crimeRepository.getCrimes()


}