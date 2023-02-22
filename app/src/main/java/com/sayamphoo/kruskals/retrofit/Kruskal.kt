package com.sayamphoo.kruskals.retrofit

import com.sayamphoo.kruskals.model.KruskalQuiz
import retrofit2.Call
import retrofit2.http.GET

interface Kruskal {
    @GET("kruskal.json")
    fun getKruskalQuiz() : Call<List<KruskalQuiz>>
}