package com.bignerdranch.android.criminalintent

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.bignerdranch.android.criminalintent.database.CrimeDatabase
import com.bignerdranch.android.criminalintent.database.migration_2_3
import java.lang.IllegalStateException
import java.util.*
import java.util.concurrent.Executors

private const val DATABASE_NAME = "crime-database"
class CrimeRepository private constructor(context: Context) {

    /**
     * Chapter 15 challenge, migrate the db
     */
    private val database: CrimeDatabase = Room.databaseBuilder(
        context.applicationContext,
        CrimeDatabase::class.java,
        DATABASE_NAME
    ).addMigrations(migration_2_3)
        .build()

    private val crimeDao = database.crimeDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun upDateCrime(crime: Crime){
        executor.execute {
            crimeDao.updateCrime(crime)
        }
    }

    fun addCrime(crime:Crime){
        executor.execute {
            crimeDao.addCrime(crime)
        }
    }

    fun getCrimes(): LiveData<List<Crime>> = crimeDao.getCrimes()

    fun getCrime(id: UUID): LiveData<Crime?> = crimeDao.getCrime(id)

    companion object{
        private var INSTANCE: CrimeRepository? = null

        fun initialize(context: Context){
            if(INSTANCE == null){
                INSTANCE = CrimeRepository(context)
            }
        }

        fun get(): CrimeRepository{
            return INSTANCE?:
                    throw IllegalStateException("CrimeRepository must be initialized")
        }
    }
}