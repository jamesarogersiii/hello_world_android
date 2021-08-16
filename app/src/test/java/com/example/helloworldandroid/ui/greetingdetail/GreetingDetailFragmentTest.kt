package com.example.helloworldandroid.ui.greetingdetail

import com.example.helloworldandroid.R
import com.example.helloworldandroid.launchFragmentInHiltContainer
import android.app.Application
import android.os.Bundle
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.helloworldandroid.greetings.Greeting
import com.example.helloworldandroid.greetings.network.*
import dagger.hilt.android.testing.*
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import androidx.test.espresso.assertion.ViewAssertions.matches



@UninstallModules(GreetingApiModule::class)
@HiltAndroidTest
@Config(application = HiltTestApplication::class)
@RunWith(RobolectricTestRunner::class)
class GreetingsFragmentTest {

    val app = ApplicationProvider.getApplicationContext<Application>()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)


    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun greetingDetailsShownOnLaunch() = runBlocking{
        val greeting = Greeting("Hi", "English", "en")
        val bundle = Bundle().apply { putSerializable("greeting", greeting) }

        launchFragmentInHiltContainer<GreetingDetailFragment>(bundle) {
            onView(withId(R.id.greetingTitleTextView))
                .check(matches(withText(app.getString(R.string.greeting))))
            onView(withId(R.id.greetingTextView))
                .check(matches(withText(greeting.text)))

            onView(withId(R.id.languageTitleTextView))
                .check(matches(withText(app.getString(R.string.language))))
            onView(withId(R.id.languageTextView))
                .check(matches(withText(greeting.language)))

            onView(withId(R.id.symbolTitleTextView))
                .check(matches(withText(app.getString(R.string.symbol))))
            onView(withId(R.id.symbolTextView))
                .check(matches(withText(greeting.symbol)))
        }
    }

}