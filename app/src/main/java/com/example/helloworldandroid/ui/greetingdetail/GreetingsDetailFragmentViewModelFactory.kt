package com.example.helloworldandroid.ui.greetingdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.helloworldandroid.greetings.Greeting

class GreetingsDetailFragmentViewModelFactory(private val greeting: Greeting) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return GreetingsDetailFragmentViewModel(greeting) as T
    }
}