package com.example.retrofitpracticepokeapi

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.retrofitpracticepokeapi.network.PokemonApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

private const val BASE_URL = "https://raw.githubusercontent.com/"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val coroutineScope = CoroutineScope(Dispatchers.IO)

        val textView = findViewById<TextView>(R.id.text_view)
        val answer: MutableList<String> = ArrayList()
        val detail: MutableList<String> = ArrayList()

        coroutineScope.launch {
            val response = PokemonApi.retrofitService.getPokedexList()
            if (response.isSuccessful) {
                Log.i("Response", "Success!")
                val listOfPokemon = response.body()
                Log.i("Response", "$listOfPokemon")
                val arraySize = listOfPokemon?.results?.size
                Log.i("Response", "Array Size: $arraySize")
                Log.i("Response", "Pokemon List: ${listOfPokemon?.results}")
//                for (pokemon in listOfPokemon?.results!!) {
//                    Log.i("Response", "Results: ${listOfPokemon.results}")
//                    Log.i("Response", "Name: ${listOfPokemon.results.get(1).name}")
//                }
            } else {
                Log.i("Response", "Failed")
            }
        }
    }
}

//suspend fun responseCall(): ArrayList<Answer>? {
//    val coroutineScope = CoroutineScope(Dispatchers.IO)
//
//    val api = Retrofit.Builder()
//        .addConverterFactory(GsonConverterFactory.create())
//        .addCallAdapterFactory(CoroutineCallAdapterFactory())
//        .baseUrl(BASE_URL)
//        .build()
//        .create(AnswersApi::class.java)
//
//    var allAnswersAndDetails: ArrayList<Answer>? = null
//
//    coroutineScope.launch(Dispatchers.IO) {
//        val response = api.getAnswersCall()
//        if (response.isSuccessful) {
//            var response = response.body()
//            var listOfAnswers = response?.answers
//            var arraySize = listOfAnswers?.size
//            Log.i("CheckSize", "Array Size: $arraySize")
//
//            allAnswersAndDetails = listOfAnswers!!
////            for (answers in listOfAnswers?.answers!!) {
////                Log.i("CheckFor", "Answer: ${answers.answer}")
////                Log.i("CheckFor", "Detail: ${answers.details}")
////                // figure out how to update answer w. fetched data
////                allAnswers.add(answers.answer)
////                allDetails.add(answers.details)
////            }
//        }
//    }
//    return allAnswersAndDetails
//}
