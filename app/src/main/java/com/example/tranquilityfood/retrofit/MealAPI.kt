package com.example.tranquilityfood.retrofit

import com.example.tranquilityfood.pojo.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealAPI {

    @GET("random.php")
    fun getRandomMeal(): Call<MealList>

    @GET("lookup.php?")
    fun getDetailMeal(@Query("i") mealId: String): Call<MealList>
}