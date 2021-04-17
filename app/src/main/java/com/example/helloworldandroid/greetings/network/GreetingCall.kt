package com.example.helloworldandroid.greetings.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class GreetingCall @Inject constructor(private val greetingApi: GreetingApi) {

    suspend fun greetings():GreetingsResult = withContext(Dispatchers.IO){
        try {
            val response = greetingApi.greetings().execute()
            GreetingsResult(response.body()!!)
        }
        catch (e: Exception){ GreetingsResult(listOf(), "timeout") }
    }

}