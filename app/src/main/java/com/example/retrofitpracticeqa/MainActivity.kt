package com.example.retrofitpracticeqa

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
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

        val textView = findViewById<TextView>(R.id.text_view)
        val answer: MutableList<String> = ArrayList()
        val detail: MutableList<String> = ArrayList()

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
                    answer.add(answers.answer)
                    detail.add(answers.details)
                }
            }
            Log.i("Add", "Answer: $answer")
            Log.i("Add", "Detail: $detail")
        }
        Log.i("End", "Answer: $answer")
        Log.i("End", "Detail: $detail")

        textView.setText("Answer: ${answer} \n Detail: ${detail} \n \n")
    }
}
