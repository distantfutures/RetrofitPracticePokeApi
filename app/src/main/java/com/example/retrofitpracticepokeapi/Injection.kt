package com.example.retrofitpracticepokeapi

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitpracticepokeapi.answerslist.PokedexViewModelFactory
import com.example.retrofitpracticepokeapi.database.AppDatabase
import com.example.retrofitpracticepokeapi.network.PokemonApi
import com.example.retrofitpracticepokeapi.repository.PokemonRepository

object Injection {

    /**
     * Creates an instance of [GithubRepository] based on the [GithubService] and a
     * [GithubLocalCache]
     */
    private fun providePokemonRepository(context: Context): PokemonRepository {
        return PokemonRepository(PokemonApi.retrofitService, AppDatabase.getInstance(context))
    }

    /**
     * Provides the [ViewModelProvider.Factory] that is then used to get a reference to
     * [ViewModel] objects.
     */
    fun provideViewModelFactory(context: Context): ViewModelProvider.Factory {
        return PokedexViewModelFactory(providePokemonRepository(context))
    }
}