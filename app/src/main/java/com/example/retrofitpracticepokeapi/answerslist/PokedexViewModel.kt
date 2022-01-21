package com.example.retrofitpracticepokeapi.answerslist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.retrofitpracticepokeapi.network.Pokemon
import com.example.retrofitpracticepokeapi.network.PokemonApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PokedexViewModel : ViewModel() {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _pokemonList = MutableLiveData<List<Pokemon>>()
    val pokemonList: LiveData<List<Pokemon>>
        get() = _pokemonList

    init {
        getPokemonList()
        Log.i("Response", "getPokemonList INITIALIZED")
    }

    private fun getPokemonList() {
        coroutineScope.launch {
            val response = PokemonApi.retrofitService.getPokedexList()

            if (response.isSuccessful) {
                Log.i("Response", "Success!")
                val pokemonObject = response.body()
//                val arraySize = listOfPokemon?.size
//                Log.i("ResponseArraySize", "Array Size: $arraySize")
                Log.i("ResponseListResults", "${pokemonObject?.results}")

                _pokemonList.value = pokemonObject?.results
                Log.i("Response", "Exit list: ${_pokemonList.value}")

            } else {
                Log.i("Response", "Failed")
            }
        }
    }
}
