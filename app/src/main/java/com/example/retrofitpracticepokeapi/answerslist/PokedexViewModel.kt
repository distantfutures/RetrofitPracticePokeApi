package com.example.retrofitpracticepokeapi.answerslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.retrofitpracticepokeapi.network.PokemonList

class PokedexViewModel : ViewModel() {

    val _pokemonList = MutableLiveData<List<PokemonList>>()
    val pokemonList: LiveData<List<PokemonList>>
        get() = _pokemonList

}
