package com.example.helloworldandroid.greetings

import com.example.helloworldandroid.greetings.database.GreetingDao
import com.example.helloworldandroid.greetings.database.GreetingDatabase
import com.example.helloworldandroid.greetings.network.GreetingCall
import com.example.helloworldandroid.greetings.network.GreetingsResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations


class GreetingsRepositoryTest{

    @Mock lateinit var greetingCallMock: GreetingCall
    @Mock lateinit var greetingDatabaseMock: GreetingDatabase
    @Mock lateinit var greetingDaoMock: GreetingDao

    lateinit var greetingsRepository: GreetingsRepository
    val testDispather = TestCoroutineDispatcher()

    @Before
    fun setUp(){
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispather)
        greetingsRepository = GreetingsRepository(greetingCallMock, greetingDatabaseMock)
        mockDatabaseGreetings(listOf())
    }

    @Test
    fun greetingsFetchesGreetingsFromDatabase() = runBlocking {
        val greetings = listOf(Greeting("Hi", "English", "en"))
        mockDatabaseGreetings(greetings)

        val result = greetingsRepository.greetings().first()

        Assert.assertEquals(greetings, result)
    }

    @Test
    fun greetingsUpdatesDatabaseWithApiGreetings() = runBlocking {
        val greetings = listOf(Greeting("Hola", "Spanish", "sp"))
        mockApiGreetings(greetings)

        greetingsRepository.greetings()
        delay(2600) //TODO: replace with testCoroutineDispatcher and use advanceTime to shorten time of test

        verify(greetingCallMock).greetings()
        verify(greetingDaoMock).insertAll(greetings)
    }


    @Test
    fun fetchGreetingsUpdatesDatabaseWithApiGreetings() = runBlocking {
        val greetings = listOf(Greeting("Hola", "Spanish", "sp"))
        mockApiGreetings(greetings)

        greetingsRepository.fetchGreetings()
        delay(2600) //TODO: replace with testCoroutineDispatcher and use advanceTime to shorten time of test

        verify(greetingDaoMock).insertAll(greetings)
    }




    private fun mockDatabaseGreetings(greetings: List<Greeting>) = runBlocking{
        Mockito.doReturn(flowOf(greetings)).`when`(greetingDaoMock).greetings()
        Mockito.doReturn(greetingDaoMock).`when`(greetingDatabaseMock).greetingDao()
    }


    private fun mockApiGreetings(greetings: List<Greeting>) = runBlocking{
        val greetingResult = GreetingsResult(greetings)
        Mockito.doReturn(greetingResult).`when`(greetingCallMock).greetings()
    }
}