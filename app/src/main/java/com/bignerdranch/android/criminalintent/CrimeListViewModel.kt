package com.bignerdranch.android.criminalintent

import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat

class CrimeListViewModel: ViewModel() {

    val crimes = mutableListOf<Crime>()

    init {
        for(i in 0 until 100){
            val crime = Crime()
            crime.title = "Crime # $i"
            crime.isSolved = i % 2 == 0
            crimes += crime
        }
    }


}