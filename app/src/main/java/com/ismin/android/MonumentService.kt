package com.ismin.android

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MonumentService {
    @GET("/books")
    fun getAllMonuments(): Call<List<Monument>>

    @POST("/books")
    fun createMonument(@Body book: Monument): Call<Monument>
}