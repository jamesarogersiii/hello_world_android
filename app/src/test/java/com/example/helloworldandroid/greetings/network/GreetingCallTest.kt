package com.example.helloworldandroid.greetings.network

import com.example.helloworldandroid.greetings.Greeting
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.doReturn
import org.mockito.MockitoAnnotations
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Headers


class GreetingCallTest{

    @Mock
    lateinit var greetingApiMock: GreetingApi
    lateinit var greetingCall: GreetingCall

    @Before
    fun setUp(){
        MockitoAnnotations.openMocks(this)
        greetingCall = GreetingCall(greetingApiMock)
    }

    @Test
    fun greetingsWithApiSuccessReturnGreetings() = runBlocking {
        val greetings = listOf(Greeting("Hi", "English", "en"))
        mockApiGreetings(greetings)

        val result = greetingCall.greetings()

        assertTrue(result.isSuccess())
        assertTrue(result.errorCode.isBlank())
        assertEquals(greetings, result.greetings)
    }

    @Test
    fun greetingsWithApiFailureReturnsError() = runBlocking {
        val status = 400
        mockApiFailure(status)

        val result = greetingCall.greetings()

        assertFalse(result.isSuccess())
        assertEquals("timeout", result.errorCode)
    }


    private fun mockApiFailure(status: Int, content: String? = ""){
        val response = Response.error<ResponseBody>(status, ResponseBody.create(MediaType.parse("application/json"), content))
        val callMock = Mockito.mock(Call::class.java)

        doReturn(callMock).`when`(greetingApiMock).greetings()
        doReturn(response).`when`(callMock).execute()
    }

    private fun mockApiGreetings(greetings: List<Greeting>){
        val response = Response.success(greetings)
        val callMock = Mockito.mock(Call::class.java)

        doReturn(callMock).`when`(greetingApiMock).greetings()
        doReturn(response).`when`(callMock).execute()
    }
}