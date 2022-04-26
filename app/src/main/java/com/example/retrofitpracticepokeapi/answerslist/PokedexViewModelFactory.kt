package com.example.retrofitpracticepokeapi.answerslist

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import com.example.retrofitpracticepokeapi.repository.PokemonRepository

class PokedexViewModelFactory(
    private val repository: PokemonRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PokedexViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PokedexViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}