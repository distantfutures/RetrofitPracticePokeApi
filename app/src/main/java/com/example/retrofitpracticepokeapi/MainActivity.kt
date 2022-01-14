package com.example.retrofitpracticepokeapi

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.retrofitpracticepokeapi.network.PokemonApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val coroutineScope = CoroutineScope(Dispatchers.IO)

        coroutineScope.launch {
            val response = PokemonApi.retrofitService.getPokedexList()

            if (response.isSuccessful) {
                Log.i("Response", "Success!")
                val listOfPokemon = response.body()
                val arraySize = listOfPokemon?.results?.size
                Log.i("ResponseArraySize", "Array Size: $arraySize")
                Log.i("ResponseListResults", "Pokemon List: ${listOfPokemon?.results}")
                
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