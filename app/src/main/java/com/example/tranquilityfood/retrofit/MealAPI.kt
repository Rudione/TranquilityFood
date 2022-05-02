package com.example.tranquilityfood.retrofit

import com.example.tranquilityfood.pojo.MealList
import retrofit2.Call
import retrofit2.http.GET

interface MealAPI {

    @GET("random.php")
    fun getRandomMeal(): Call<MealList>
}