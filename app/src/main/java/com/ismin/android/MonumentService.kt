package com.ismin.android

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MonumentService {
    @GET("/monuments")
    fun getAllMonuments(): Call<List<Monument>>

    @POST("/monuments")
    fun createMonument(@Body monument: Monument): Call<Monument>
}