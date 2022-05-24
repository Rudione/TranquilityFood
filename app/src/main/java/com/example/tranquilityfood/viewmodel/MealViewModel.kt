package com.example.tranquilityfood.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tranquilityfood.db.MealDatabase
import com.example.tranquilityfood.pojo.Meal
import com.example.tranquilityfood.pojo.MealList
import com.example.tranquilityfood.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel(
    val mealDatabase: MealDatabase
) : ViewModel() {
    private var mealDetailsLiveData = MutableLiveData<Meal> ()

    fun getDetailsLiveData(mealId: String) {
        RetrofitInstance.retrofitApi.getDetailMeal(mealId).enqueue(object : Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if(response.body() != null) {
                    mealDetailsLiveData.value = response.body()!!.meals[0]
                } else {
                    return
                }
            }
            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("TEST", "fail?? :( ${t.message.toString()}")
            }

        })
    }

    fun observeMealDetailsLiveData(): LiveData<Meal> {
        return mealDetailsLiveData
    }

    fun insertMeal(meal: Meal) {
        viewModelScope.launch {
            mealDatabase.mealDao().upsert(meal)
        }
    }
}