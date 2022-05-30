package com.example.tranquilityfood.retrofit

import com.example.tranquilityfood.pojo.CategoryList
import com.example.tranquilityfood.pojo.MealsByCategoryList
import com.example.tranquilityfood.pojo.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealAPI {

    @GET("random.php")
    fun getRandomMeal(): Call<MealList>

    @GET("lookup.php?")
    fun getDetailMeal(@Query("i") mealId: String): Call<MealList>

    @GET("filter.php?")
    fun getPopularMeal(@Query("c") meal: String): Call<MealsByCategoryList>

    @GET("categories.php")
    fun getCategoriesMeal(): Call<CategoryList>

    @GET("filter.php")
    fun getMealByCategory(@Query("c") meal: String): Call<MealsByCategoryList>

    @GET("search.php")
    fun searchMeals(@Query("s") meal: String): Call<MealList>
}