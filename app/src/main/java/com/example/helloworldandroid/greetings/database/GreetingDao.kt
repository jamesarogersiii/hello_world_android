package com.example.helloworldandroid.greetings.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.helloworldandroid.greetings.Greeting
import kotlinx.coroutines.flow.Flow

@Dao
interface GreetingDao {

    @Query("SELECT * from greeting")
    fun greetings(): Flow<List<Greeting>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(greetings: List<Greeting>)
}