package com.example.helloworldandroid.greetings.network


import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class GreetingApiModule {

    @Provides
    @Singleton
    fun greetingApi(): GreetingApi {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://hello-world-node-service.vercel.app")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(GreetingApi::class.java)
    }
}