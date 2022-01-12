package com.example.retrofitpracticeqa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://raw.githubusercontent.com/"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val api = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl(BASE_URL)
            .build()
            .create(AnswersApi::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            val response = api.getAnswersCall()
            if (response.isSuccessful) {
                var listOfAnswers = response.body()

                var arraySize = listOfAnswers?.answers?.size
                Log.i("Response", "Array Size: $arraySize")

                for (answers in listOfAnswers?.answers!!) {
                    Log.i("Response", "Answer: ${answers.answer}")
                    Log.i("Response", "Detail: ${answers.details}")
                }
            }
        }
    }
}