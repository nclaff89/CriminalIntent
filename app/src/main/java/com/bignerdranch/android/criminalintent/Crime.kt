package com.bignerdranch.android.criminalintent

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
@Entity
/**
 * Chapter 15 challenge add a suspectNumber column to the Room DB
 */
data class Crime(@PrimaryKey val id: UUID = UUID.randomUUID(),
                 var title: String = "",
                 var date: Date = Date(),
                 var isSolved:Boolean = false,
                 var suspect: String ="",
                 var suspectNumber: String = "")