package com.example.retrofitpracticepokeapi.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.retrofitpracticepokeapi.model.Pokemon
import com.example.retrofitpracticepokeapi.network.PokemonApiService
import retrofit2.HttpException
import java.io.IOException

const val TAG = "CheckPagingSource"
//const val LIST_OFFSET = 0
//const val LIST_SIZE = 20
// PagingSource takes Int b/c PokeAPI uses 20-based numbers for page offset numbers
// Also takes Pokemon due to type of data being loaded
class PokemonPagingSource(
    private val service: PokemonApiService
) : PagingSource<Int, Pokemon>() {
    // Needs key of pages to be loaded & load size
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pokemon> {
        val pageKey = params.key ?: LIST_OFFSET
        Log.i(TAG, "paramsKey: $pageKey")
        return try {
            val pokedexResponse = service.getPokedexList(pageKey, LIST_SIZE)
            val pokemonFetchList = pokedexResponse.body()?.results
            val pokemonInfoList = mutableListOf<Pokemon>()
            for (pokemon in pokemonFetchList!!) {
                val response = service.getPokemonInfo(pokemon.name)
                pokemonInfoList.add(response.body()!!)
//                Log.i(TAG, "Each Pokemon Info: ${response.body()}")
            }
            val nextKey = if (pokemonInfoList.isEmpty()) {
                null
            } else {
                pageKey + LIST_SIZE
            }
            Log.i(TAG, "params loadSize: ${params.loadSize} \n" +
                    "PageKey $pageKey \n" +
                    "NextKey: ${nextKey} \n" +
                    "ListSize: $LIST_SIZE")
            Log.i(TAG, "Pokedex Response: $pokedexResponse \n" +
                    "Pokemon Fetch List: $pokemonFetchList \n" +
                    "Pokemon Info List: $pokemonInfoList")
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
}