package com.example.helloworldandroid.greetings.network

import com.example.helloworldandroid.greetings.Greeting
import retrofit2.Call
import retrofit2.http.GET

interface GreetingApi {

    @GET("/greetings")
    fun greetings(): Call<List<Greeting>>

}