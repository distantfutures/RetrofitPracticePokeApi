package com.example.retrofitpracticepokeapi.answerslist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.retrofitpracticepokeapi.network.Pokemon
import com.example.retrofitpracticepokeapi.network.PokemonApi
import com.example.retrofitpracticepokeapi.network.PokemonList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PokedexViewModel : ViewModel() {

    val _pokemonList = MutableLiveData<String>()
    val pokemonList: LiveData<String>
        get() = _pokemonList

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getPokemonList()
        Log.i("Response", "getPokemonList INITIALIZED")
    }

    private fun getPokemonList() {
        coroutineScope.launch {
            val response = PokemonApi.retrofitService.getPokedexList()

            if (response.isSuccessful) {
                Log.i("Response", "Success!")
                val listOfPokemon = response.body()
                val arraySize = listOfPokemon?.results?.size
                Log.i("ResponseArraySize", "Array Size: $arraySize")
                Log.i("ResponseListResults", "Pokemon List: ${listOfPokemon?.results}")

                _pokemonList.value = listOfPokemon?.results?.get(1).toString()

                for (pokemon in listOfPokemon?.results!!) {
                    Log.i("Response", "Pokemon Object: $pokemon")
                    Log.i("Response", "Pokemon Name: ${pokemon.name}")
                    Log.i("Response", "Pokemon Url: ${pokemon.url}")
                }
            } else {
                Log.i("Response", "Failed")
            }
        }
    }
}
