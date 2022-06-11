package com.example.tranquilityfood.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tranquilityfood.data.db.MealDatabase
import com.example.tranquilityfood.domain.*
import com.example.tranquilityfood.data.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(
    private val mealDatabase: MealDatabase
): ViewModel() {
    private val mealLiveData = MutableLiveData<Meal>()
    private val popularListLiveData = MutableLiveData<List<MealsByCategory>>()
    private val categoriesLiveData = MutableLiveData<List<Category>>()
    private val favoritesMealsLiveData = mealDatabase.mealDao().getAllMeals()
    private val bottomSheetMealLiveData = MutableLiveData<Meal>()
    private val searchedMealsLiveData = MutableLiveData<List<Meal>>()

    private var saveSateRandomMeal: Meal? = null
    fun getRandomMeal() {
        saveSateRandomMeal?.let {
            mealLiveData.postValue(it)
            return
        }

        RetrofitInstance.retrofitApi.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null) {
                    val randomMeal: Meal = response.body()!!.meals[0]
                    mealLiveData.value = randomMeal
                    saveSateRandomMeal = randomMeal

                    Log.d("TEST", "1.We are get ${randomMeal.idMeal} 2. + ${randomMeal.strMeal}")
                } else {
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("Random TEST", t.message.toString())
            }
        })
    }

    fun getPopularMeal() {
        RetrofitInstance.retrofitApi.getPopularMeal("Seafood").enqueue(object : Callback<MealsByCategoryList> {
            override fun onResponse(call: Call<MealsByCategoryList>, response: Response<MealsByCategoryList>) {
                if(response.body() != null) {
                    Log.d("testt1", "1. We are get ${popularListLiveData.value} 2. + ${response.body()!!.meals}")
                    popularListLiveData.value = response.body()!!.meals
                    Log.d("testt", "1. We are get ${popularListLiveData.value} 2. + ${response.body()!!.meals}")
                } else {
                    return
                }
            }

            override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                Log.d("testt", t.message.toString())
            }

        })
    }

    fun getCategoriesMeal() {
        RetrofitInstance.retrofitApi.getCategoriesMeal().enqueue(object : Callback<CategoryList> {
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                response.body()?.let {
                    categoriesLiveData.postValue(it.categories)
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.e("HomeVM", "dyakuyu bat'ko.... ${t.message.toString()}")
            }

        })
    }

    fun searchMeals(searchQuery: String) = RetrofitInstance.retrofitApi.searchMeals(searchQuery).enqueue(
        object : Callback<MealList> {
        override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
            val mealList = response.body()?.meals
            mealList?.let {
                searchedMealsLiveData.postValue(it)
            }
        }

        override fun onFailure(call: Call<MealList>, t: Throwable) {
            Log.d("search", t.message.toString())
        }
    })


    fun getDetailsMealById(id: String) {
        RetrofitInstance.retrofitApi.getDetailMeal(id).enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                val meal = response.body()?.meals?.first()
                meal?.let {
                    bottomSheetMealLiveData.postValue(it)
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("detailsMealId", t.message.toString())
            }

        })
    }

    fun insertMeal(meal: Meal) {
        viewModelScope.launch {
            mealDatabase.mealDao().upsert(meal)
        }
    }

    fun deleteMeal(meal: Meal) {
        viewModelScope.launch {
            mealDatabase.mealDao().upsert(meal)
        }
    }

    fun observeRandomMealLiveData(): LiveData<Meal> {
        return mealLiveData
    }

    fun observePopularMealLiveData(): LiveData<List<MealsByCategory>> {
        return popularListLiveData
    }

    fun observerCategoriesMealLiveData(): LiveData<List<Category>> {
        return categoriesLiveData
    }

    fun observeFavoritesMealLiveData(): LiveData<List<Meal>> {
        return favoritesMealsLiveData
    }

    fun observeDetailsMealByIdLiveData(): LiveData<Meal> {
        return bottomSheetMealLiveData
    }

    fun observeSearchedMealsLiveData(): LiveData<List<Meal>> {
        return searchedMealsLiveData
    }
}
