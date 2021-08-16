package com.example.helloworldandroid.greetings

import com.example.helloworldandroid.greetings.database.GreetingDatabase
import com.example.helloworldandroid.greetings.network.GreetingCall
import com.example.helloworldandroid.greetings.network.GreetingsResult
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GreetingsRepository @Inject constructor(private val greetingCall: GreetingCall, private val greetingDatabase: GreetingDatabase) {

    suspend fun greetings(): Flow<List<Greeting>> = withContext(Dispatchers.IO) {
        val greetingFlow = greetingDatabase.greetingDao().greetings()
        MainScope().launch {  fetchGreetings() }
        return@withContext greetingFlow
    }

    suspend fun fetchGreetings(): GreetingsResult = withContext(Dispatchers.IO){
        delay(2500) //for show

        val greetingResult = greetingCall.greetings()
        if(greetingResult.isSuccess()) greetingDatabase.greetingDao().insertAll(greetingResult.greetings)
        return@withContext greetingResult
    }

}