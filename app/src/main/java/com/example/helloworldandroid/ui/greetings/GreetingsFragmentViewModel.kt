package com.example.helloworldandroid.ui.greetings


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.helloworldandroid.greetings.Greeting
import com.example.helloworldandroid.greetings.GreetingsRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class GreetingsFragmentViewModel(private val greetingsRepository: GreetingsRepository) : ViewModel(){
    val greetings: MutableLiveData<List<Greeting>> = MutableLiveData()
    val loadingGreetings: MutableLiveData<Boolean> = MutableLiveData()
    val failureCode: MutableLiveData<String> = MutableLiveData()

    init {
        fetchGreetings()
    }

    private fun fetchGreetings(){
        loadingGreetings.value = true
        viewModelScope.launch {
            greetingsRepository.greetings().collect {
                greetings.value = it
                loadingGreetings.value = false
            }
        }
    }

    fun refreshGreetings() {
        loadingGreetings.value = true
        viewModelScope.launch {
            val result = greetingsRepository.fetchGreetings()
            loadingGreetings.value = false
            if(!result.isSuccess()) failureCode.value = result.errorCode
        }
    }
}