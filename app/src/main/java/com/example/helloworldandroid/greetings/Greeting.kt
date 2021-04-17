package com.example.helloworldandroid.greetings

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
data class Greeting (
    @PrimaryKey @SerializedName("text") val text: String,
    @SerializedName("language") val language: String,
    @SerializedName("symbol") val symbol: String
    ): Serializable