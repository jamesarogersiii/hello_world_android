package com.example.helloworldandroid.ui.greetings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.helloworldandroid.greetings.GreetingsRepository

class GreetingsFragmentViewModelFactory(private val greetingsRepository: GreetingsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return GreetingsFragmentViewModel(greetingsRepository) as T
    }
}