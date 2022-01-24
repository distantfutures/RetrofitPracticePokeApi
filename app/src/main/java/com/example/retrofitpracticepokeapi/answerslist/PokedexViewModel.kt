package com.example.retrofitpracticepokeapi.answerslist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.retrofitpracticepokeapi.network.Pokemon
import com.example.retrofitpracticepokeapi.network.PokemonApi
import com.example.retrofitpracticepokeapi.network.PokemonSprites
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.json.JSONObject

class PokedexViewModel : ViewModel() {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private var _pokemonList = MutableLiveData<List<Pokemon>>()
    val pokemonList: LiveData<List<Pokemon>>
        get() = _pokemonList

    private val _pokemonSprites = MutableLiveData<List<PokemonSprites>>()
    val pokemonSprites: LiveData<List<PokemonSprites>>
        get() = _pokemonSprites

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
                Log.i("ResponseListResults", "${pokemonObject?.results}")
                val pokemonObjectSize = pokemonObject?.results?.size
                Log.i("ResponseListSize", "${pokemonObjectSize}")

                _pokemonList.value = pokemonObject?.results
                Log.i("Response", "Exit list: ${_pokemonList.value}")

                getPokemonSprites(pokemonObjectSize)

            } else {
                Log.i("Response", "Failed")
            }
        }
    }
    private fun getPokemonSprites(pokeListSize: Int?) {
        coroutineScope.launch {

            for (i in 0 until pokeListSize!!) {
                val responseSprites = PokemonApi.retrofitService.getSpritesList(i+1)
                val spritesUrl = responseSprites.body()?.sprites
                Log.i("ResponseSprite", "${spritesUrl}")
                Log.i("ResponseName", "${_pokemonList.value?.get(i)?.name}")
                _pokemonList.value?.get(i)?.url = spritesUrl?.front_default!!
                Log.i("ResponseNewUrl", "${_pokemonList.value?.get(i)?.url}")
            }
        }
    }
}
