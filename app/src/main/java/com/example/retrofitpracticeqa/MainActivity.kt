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
                Log.i("Response", "Array Size$arraySize")
//                for (i in 0 until arraySize!!) {
//                    val tempAnswer = listOfAnswers?.answers?.get(i)?.answer
//                    Log.i("I/CheckAssign", "Answer: XX $tempAnswer")
//                    val tempDetail = listOfAnswers?.answers?.get(i)?.details
//                    Log.i("I/CheckAssign", "Detail: XX $tempDetail")
//                    val temp = Answer(tempAnswer!!, tempDetail!!)
//                    Log.i("I/CheckTemp", "Detail: XX $temp")
//                    answersList.add(temp)
//                    Log.i("I/CheckAdd", "ANSWERS LIST: $answersList")
//                }

//                for (answers in response.body()!!) {
//                    Log.i("Response", answers.answer)
//                }
            }
        }
    }
}