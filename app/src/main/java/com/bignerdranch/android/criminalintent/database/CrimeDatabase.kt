package com.bignerdranch.android.criminalintent.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.bignerdranch.android.criminalintent.Crime

@Database(entities = [Crime::class], version = 3)
@TypeConverters(CrimeTypeConverters::class)
abstract class CrimeDatabase: RoomDatabase(){

    abstract fun crimeDao(): CrimeDao
}

/**
 * Chapter 15 challenge update the ROom DB
 */
//change this
val migration_2_3 = object : Migration(2,3){
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE Crime ADD COLUMN suspectNumber TEXT NOT NULL DEFAULT ''"
        )
    }
}