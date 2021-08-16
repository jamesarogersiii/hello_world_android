package com.example.helloworldandroid.greetings.network

import com.example.helloworldandroid.greetings.Greeting
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.runBlocking
import org.mockito.Mockito
import org.mockito.Mockito.doReturn
import retrofit2.Call
import retrofit2.Response
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [GreetingApiModule::class]
)
class GreetingApiModuleMock {
    @Singleton
    @Provides
    fun greetingApi(): GreetingApi = Mockito.mock(GreetingApi::class.java).apply {
        val callMock = Mockito.mock(Call::class.java)
        doReturn(Response.success(listOf<Greeting>())).`when`(callMock).execute()
        doReturn(callMock).`when`(this).greetings()
    }
}