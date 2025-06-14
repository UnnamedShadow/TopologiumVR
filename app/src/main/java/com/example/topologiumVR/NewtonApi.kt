package com.example.topologiumVR

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


interface NewtonApi {
    data class Result(val operation: String, val expression: String, val result: String)
    enum class Operation{
        Simplify,
        Factor,
        Derive,
        Integrate,
        Zeroes,
        Tangent,
        Area,
        Cos,
        Sin,
        Tan,
        ArcCos,
        ArcSin,
        ArcTan,
        Abs,
        Log,
    }
    @GET("/{op}/{exp}")
    suspend fun calculate(@Path("op") operation: Operation, @Path("exp") expression: String): Response<Result>
}

fun create(): NewtonApi{
    return Retrofit.Builder().baseUrl("https://newton.vercel.app/api/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(NewtonApi::class.java)
}
