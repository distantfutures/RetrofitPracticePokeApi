package com.example.retrofitpracticepokeapi.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.retrofitpracticepokeapi.answerslist.LIST_OFFSET
import com.example.retrofitpracticepokeapi.answerslist.TAG
import com.example.retrofitpracticepokeapi.data.PokemonPagingSource
import com.example.retrofitpracticepokeapi.database.PokemonDao
import com.example.retrofitpracticepokeapi.model.Pokemon
import com.example.retrofitpracticepokeapi.model.PokemonResponse
import com.example.retrofitpracticepokeapi.network.PokemonApi
import com.example.retrofitpracticepokeapi.network.PokemonApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class PokemonRepository(private val service: PokemonApiService) {

    fun getPokedexList(): Flow<PagingData<Pokemon>> {
        return Pager(
            config = PagingConfig(
                pageSize = LIST_LIMIT,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PokemonPagingSource(service)}
        ).flow
    }

//    suspend fun getPokedexList(offset: Int, limit: Int): Response<PokemonResponse> {
//        return service.getPokedexList(offset = offset, limit = limit)
//    }

    suspend fun getPokemonInfo(pokemonName: String): Response<Pokemon> {
        return service.getPokemonInfo(pokemonName)
    }

    companion object {
        const val LIST_LIMIT = 20
    }
}