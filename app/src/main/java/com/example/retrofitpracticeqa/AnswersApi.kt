package com.example.retrofitpracticeqa

import retrofit2.Response
import retrofit2.http.GET

interface AnswersApi {
    // Gets answers objects
    @GET("Tc2r1/DevInterview_Questions/master/Languages/Android/answers.json")
    suspend fun getAnswersCall(): Response<Answers>
}
