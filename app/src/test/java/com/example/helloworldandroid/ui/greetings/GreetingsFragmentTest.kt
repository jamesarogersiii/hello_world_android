package com.example.helloworldandroid.ui.greetings

import com.example.helloworldandroid.R
import com.example.helloworldandroid.RecyclerViewItemCountAssertion
import com.example.helloworldandroid.launchFragmentInHiltContainer
import android.app.Application
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.helloworldandroid.greetings.Greeting
import com.example.helloworldandroid.greetings.database.GreetingDatabase
import com.example.helloworldandroid.greetings.network.*
import dagger.hilt.android.testing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.doReturn
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import kotlinx.coroutines.delay
import org.hamcrest.Matchers
import org.hamcrest.Matchers.not


@UninstallModules(GreetingApiModule::class)
@HiltAndroidTest
@Config(application = HiltTestApplication::class)
@RunWith(RobolectricTestRunner::class)
class GreetingsFragmentTest {

    val app = ApplicationProvider.getApplicationContext<Application>()
    @Inject lateinit var greetingApiMock: GreetingApi
    @Inject lateinit var greetingDatabase: GreetingDatabase

    @get:Rule
    var hiltRule = HiltAndroidRule(this)


    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        hiltRule.inject()
    }

    @After
    fun tearDown() {
    }

    @Test
    fun greetingsShownOnLaunch() = runBlocking{
        val greetings = listOf(
            Greeting("Hi", "English", "en"),
            Greeting("Hola", "Spanish", "es"),
            Greeting("Hei Verden", "Norwegian", "no")
        )
        mockDatabaseGreetings(greetings)

        launchFragmentInHiltContainer<GreetingsFragment> {
            onView(withId(R.id.greetingsRecylcerView))
                .check(RecyclerViewItemCountAssertion(3))
                .perform(RecyclerViewActions.scrollTo<GreetingAdapter.GreetingViewHolder>(hasDescendant(withText(greetings[0].text))))
                .perform(RecyclerViewActions.scrollTo<GreetingAdapter.GreetingViewHolder>(hasDescendant(withText(greetings[1].text))))
                .perform(RecyclerViewActions.scrollTo<GreetingAdapter.GreetingViewHolder>(hasDescendant(withText(greetings[2].text))))
        }
    }

    @Test
    fun refreshUpdatesGreetingsShown() = runBlocking{
        val greetings = listOf(
            Greeting("Hi", "English", "en")
        )
        mockDatabaseGreetings(listOf())

        launchFragmentInHiltContainer<GreetingsFragment> {
            runBlocking {
                onView(withId(R.id.greetingsRecylcerView)).check(RecyclerViewItemCountAssertion(0))
                delay(3000) //TODO: Switch to testDispatcher

                mockApiGreetings(greetings)
                onView(withId(R.id.refreshMenuItem)).perform(click())
                onView(withId(R.id.shimmerLayout)).check(matches(isDisplayed()))
                delay(3000)
                onView(withId(R.id.greetingsRecylcerView)).check(RecyclerViewItemCountAssertion(1))
                    .perform(RecyclerViewActions.scrollTo<GreetingAdapter.GreetingViewHolder>(hasDescendant(withText(greetings[0].text))))
                onView(withId(R.id.shimmerLayout)).check(matches(not(isDisplayed())))
            }
        }
    }



    @Test
    fun clickGreetingLaunchesGreetingDetails() = runBlocking{
        val greetings = listOf(
            Greeting("Hi", "English", "en"),
        )
        mockDatabaseGreetings(greetings)

        val navController = TestNavHostController(app)
        launchFragmentInHiltContainer<GreetingsFragment> {
            viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                if (viewLifecycleOwner != null) {
                    navController.setGraph(R.navigation.greetings_nav)
                    Navigation.setViewNavController(requireView(), navController)
                }
            }

            onView(withId(R.id.greetingsRecylcerView))
                .check(RecyclerViewItemCountAssertion(1))
                .perform(RecyclerViewActions.actionOnItemAtPosition<GreetingAdapter.GreetingViewHolder>(0, click()))

            assertThat(navController.currentDestination?.id, Matchers.`is`(R.id.greetingDetailFragment))
        }
    }





    private suspend fun mockDatabaseGreetings(greetings: List<Greeting>) = withContext(Dispatchers.IO){
        greetingDatabase.greetingDao().insertAll(greetings)
    }

    private suspend fun mockApiGreetings(greetings: List<Greeting>) = withContext(Dispatchers.IO){
        val callMock = Mockito.mock(Call::class.java)
        doReturn(Response.success(greetings)).`when`(callMock).execute()
        doReturn(callMock).`when`(greetingApiMock).greetings()
    }
}