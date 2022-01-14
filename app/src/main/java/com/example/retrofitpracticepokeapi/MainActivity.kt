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
                Log.i("Response", "$listOfPokemon")
                val arraySize = listOfPokemon?.results?.size
                Log.i("Response", "Array Size: $arraySize")
                Log.i("Response", "Pokemon List: ${listOfPokemon?.results}")
                
                for (pokemon in listOfPokemon?.results!!) {
                    Log.i("Response", "Results: ${listOfPokemon.results}")
                    Log.i("Response", "Name: ${listOfPokemon.results.get(1).name}")
                }
            } else {
                Log.i("Response", "Failed")
            }
        }
    }
}