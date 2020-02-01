package com.bignerdranch.android.criminalintent

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
@Entity
/**
 * Updated data class for  chapter 13 challenge
 */
data class Crime(@PrimaryKey val id: UUID = UUID.randomUUID(),
                 var title: String = "",
                 var date: Date = Date(),
                 var time: String = "",
                 var isSolved:Boolean = false)