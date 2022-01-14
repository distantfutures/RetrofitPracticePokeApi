package com.example.retrofitpracticepokeapi.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://pokeapi.co/api/v2/"

//Use Moshi Builder to create Moshi object w. KotlinJsonAdapterFactory
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

// Fetches JSON response. Create Retrofit Builder using scalars converter factory & give base url of web service.
private val retrofit = Retrofit.Builder()
    //Turns Json response into string
    //.addConverterFactory(ScalarsConverterFactory.create())
    //Turns Json response into Kotlin Objects
    //.addConverterFactory(MoshiConverterFactory.create(moshi))
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

// Implement API service interface that return JSON data as a string (through Scalars)
interface PokemonApiService {
    // Gets answers objects
    @GET("pokemon")
    suspend fun getPokedexList():
            Response<PokemonList>
}

// Creates API object using Retrofit to implement API Service
object PokemonApi {
    val retrofitService: PokemonApiService by lazy {
        retrofit.create(PokemonApiService::class.java)
    }
}
