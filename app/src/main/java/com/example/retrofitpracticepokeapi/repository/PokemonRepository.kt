package com.example.retrofitpracticepokeapi.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.retrofitpracticepokeapi.data.PokemonPagingSource
import com.example.retrofitpracticepokeapi.model.Pokemon
import com.example.retrofitpracticepokeapi.network.PokemonApiService
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class PokemonRepository(private val service: PokemonApiService) {

    fun getPokedexListRep(): Flow<PagingData<Pokemon>> {
        return Pager(
            config = PagingConfig(
                pageSize = LIST_LIMIT,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PokemonPagingSource(service) }
        ).flow
    }

    suspend fun getPokemonInfoRep(pokemonName: String): Response<Pokemon> {
        return service.getPokemonInfo(pokemonName)
    }

    companion object {
        const val LIST_LIMIT = 20
    }
}