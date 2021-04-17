package com.example.helloworldandroid.greetings.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class GreetingDatabaseModule {

    @Provides
    fun greetingDao(greetingDatabase: GreetingDatabase): GreetingDao {
        return greetingDatabase.greetingDao()
    }

    @Provides
    @Singleton
    fun greetingDatabase(@ApplicationContext appContext: Context): GreetingDatabase {
        return Room.databaseBuilder(
            appContext,
            GreetingDatabase::class.java,
            "greeting-database"
        ).build()
    }
}