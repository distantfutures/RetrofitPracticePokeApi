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

const val TAG = "CheckVM"
class PokedexViewModel : ViewModel() {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private var _pokemonList = MutableLiveData<List<Pokemon>>()
    val pokemonList: LiveData<List<Pokemon>>
        get() = _pokemonList

    init {
        getPokemonList()
        Log.i(TAG, "getPokemonList INITIALIZED")
    }

    private fun getPokemonList() {
        coroutineScope.launch {
            val response = PokemonApi.retrofitService.getPokedexList()

            if (response.isSuccessful) {
                val pokemonObject = response.body()
                val pokemonObjectSize = pokemonObject?.results?.size
                _pokemonList.value = pokemonObject?.results

                getPokemonSprites(pokemonObjectSize)

                Log.i(TAG, "\nPokemon Object: ${pokemonObject?.results} \n" +
                        "PokemonObject Size: ${pokemonObjectSize} \n" +
                        "Exit list check: ${_pokemonList.value}\n")
                Log.i(TAG, "Response Success!")
            } else {
                Log.i(TAG, "Response Failed")
            }
        }
    }
    private fun getPokemonSprites(pokeListSize: Int?) {
        coroutineScope.launch(Dispatchers.IO) {
            for (i in 0 until pokeListSize!!) {
                val responseSprites = PokemonApi.retrofitService.getSpritesList(i+1)
                val spritesUrl = responseSprites.body()?.sprites
                _pokemonList.value?.get(i)?.url = spritesUrl?.newUrl!!

                Log.i(TAG, "Sprites Url: ${spritesUrl} \n" +
                        "Name: ${_pokemonList.value?.get(i)?.name} \n" +
                        "Url: ${_pokemonList.value?.get(i)?.url} \n")
            }
        }
    }
}
