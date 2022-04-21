package com.example.retrofitpracticepokeapi.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.retrofitpracticepokeapi.model.Pokemon
import com.example.retrofitpracticepokeapi.network.PokemonApiService
import retrofit2.HttpException
import java.io.IOException

const val LIST_OFFSET = 0
const val LIST_SIZE = 20
// PagingSource takes Int b/c PokeAPI uses 20-based numbers for page offset numbers
// Also takes Pokemon due to type of data being loaded
class PokemonPagingSource(
    private val service: PokemonApiService,
    private val pokeName: String
) : PagingSource<Int, Pokemon>() {
    // Needs key of pages to be loaded & load size
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pokemon> {
        val pageKey = params.key ?: LIST_OFFSET
        val apiQuery = pokeName
        return try {
            val pokedexResponse = service.getPokedexList(pageKey, LIST_SIZE)
            val pokemonFetchList = pokedexResponse.body()?.results
            val pokemonInfoList = mutableListOf<Pokemon>()
            for (name in pokemonFetchList!!) {
                val response = service.getPokemonInfo(pokeName)
                pokemonInfoList.add(response.body()!!)
            }
            val nextKey = if (pokemonInfoList.isEmpty()) {
                null
            } else {
                pageKey + (params.loadSize / LIST_SIZE)
            }
            LoadResult.Page(
                data = pokemonInfoList,
                prevKey = if (pageKey == LIST_OFFSET) null else pageKey - 20,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Pokemon>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(20)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(20)
        }
    }

//    companion object {
//        const val PAGE_LIMIT = 20
//    }
}