package com.example.helloworldandroid.greetings.network

import com.example.helloworldandroid.greetings.Greeting

class GreetingsResult(val greetings: List<Greeting> = listOf(), val errorCode: String = "") {
    fun isSuccess() = greetings.isNotEmpty()
}