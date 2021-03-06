package com.example.retrofitpracticepokeapi.network

import com.example.retrofitpracticepokeapi.model.Pokemon
import com.example.retrofitpracticepokeapi.model.PokemonResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://pokeapi.co/api/v2/"

// Fetches JSON response. Create Retrofit Builder using scalars converter factory & give base url of web service.
private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

// Implement API service interface that return JSON data as a string (through Scalars)
interface PokemonApiService {
    @GET("pokemon")
    suspend fun getPokedexList(
        @Query("offset") offset: Int = 0,
        @Query("limit") limit: Int = 20
    ): Response<PokemonResponse>

    @GET("pokemon/{name}")
    suspend fun getPokemonInfo(@Path("name") name: String):
            Response<Pokemon>
}

// Creates API object using Retrofit to implement API Service
object PokemonApi {
    val retrofitService: PokemonApiService by lazy {
        retrofit.create(PokemonApiService::class.java)
    }
}
