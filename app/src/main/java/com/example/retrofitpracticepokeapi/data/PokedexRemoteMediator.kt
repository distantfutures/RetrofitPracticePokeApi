package com.example.retrofitpracticepokeapi.data

import androidx.paging.*
import androidx.room.withTransaction
import com.example.retrofitpracticepokeapi.database.AppDatabase
import com.example.retrofitpracticepokeapi.model.Pokemon
import com.example.retrofitpracticepokeapi.model.PokemonResponse
import com.example.retrofitpracticepokeapi.network.PokemonApiService
import retrofit2.HttpException
import java.io.IOException

const val LIST_OFFSET = 0
const val LIST_SIZE = 20
@OptIn(ExperimentalPagingApi::class)
class PokedexRemoteMediator(
    private val service: PokemonApiService,
    private val appDatabase: AppDatabase
) : RemoteMediator<Int, Pokemon>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Pokemon>
    ): MediatorResult {
        /**
         * Paging State gives us info about the pages loaded before,
         * the most recently accessed index in the list,
         * and the PagingConfig
         */

        /**
         * LoadType tells us whether we need to load data at the end LoadType.APPEND
         * or at the beginning of the data LoadType.PREPEND
         * or if its the first time loading data LoadType.REFRESH
         */
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.next?.minus(20) ?: LIST_OFFSET
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.previous
                if (prevKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.next
                if (nextKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
                nextKey
            }
        }
        try {
            val pokedexResponse = service.getPokedexList(page, state.config.pageSize)
            val pokemonFetchList = pokedexResponse.body()?.results
            val endOfPagingationReached = pokemonFetchList?.isEmpty()
            val pokemonInfoList = mutableListOf<Pokemon>()

            appDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    appDatabase.pokemonRemoteKeysDao().clearRemoteKeys()
                    appDatabase.pokeDao().clearRepos()
                }
                val prevKey = if (page == LIST_OFFSET) null else page - 20
                val nextKey = if (endOfPagingationReached == true) null else page + 20

                if (pokemonFetchList != null) {
                    for (pokemon in pokemonFetchList) {
                        val response = service.getPokemonInfo(pokemon.name)
                        pokemonInfoList.add(response.body()!!)
            //                Log.i(TAG, "Each Pokemon Info: ${response.body()}")
                    }
                }

                val keys = pokemonFetchList?.map {
                    PokemonResponse(repoId = it.page, 0, next = nextKey, previous = prevKey, pokemonInfoList)
                }
                appDatabase.pokeDao().insertList(pokemonInfoList)
                if (keys != null) {
                    appDatabase.pokemonRemoteKeysDao().insertList(keys)
                }
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPagingationReached!!)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }
    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Pokemon>
    ): PokemonResponse? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.page?.let { repoId ->
                appDatabase.pokemonRemoteKeysDao().remoteKeysRepoId(repoId)
            }
        }
    }
    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Pokemon>): PokemonResponse? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { repo ->
                // Get the remote keys of the first items retrieved
                appDatabase.pokemonRemoteKeysDao().remoteKeysRepoId(repo.page)
            }
    }
    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Pokemon>): PokemonResponse? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { repo ->
                // Get the remote keys of the last item retrieved
                appDatabase.pokemonRemoteKeysDao().remoteKeysRepoId(repo.page)
            }
    }
}