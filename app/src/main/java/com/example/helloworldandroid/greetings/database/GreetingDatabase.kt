package com.example.helloworldandroid.greetings.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.helloworldandroid.greetings.Greeting

@Database(entities = [Greeting::class], version = 1)
abstract class GreetingDatabase : RoomDatabase() {
    abstract fun greetingDao(): GreetingDao
}
