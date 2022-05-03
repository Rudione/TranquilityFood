package com.example.tranquilityfood.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.example.tranquilityfood.pojo.Meal
import com.example.tranquilityfood.pojo.MealList
import com.example.tranquilityfood.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel() : ViewModel() {
    val mealLiveData = MutableLiveData<Meal>()

    fun getRandomMeal() {
        RetrofitInstance.retrofitApi.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null) {
                    val randomMeal: Meal = response.body()!!.meals[0]
                    mealLiveData.value = randomMeal

                    Log.d("TEST", "1.We are get ${randomMeal.idMeal} 2. + ${randomMeal.strMeal}")
                } else {
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("TEST", t.message.toString())
            }
        })
    }

    fun observeRandomMeal(): LiveData<Meal> {
        return mealLiveData
    }
}