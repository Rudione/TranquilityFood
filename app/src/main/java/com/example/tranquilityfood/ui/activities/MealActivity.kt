package com.example.tranquilityfood.ui.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.tranquilityfood.R
import com.example.tranquilityfood.databinding.ActivityMealBinding
import com.example.tranquilityfood.data.db.MealDatabase
import com.example.tranquilityfood.domain.Meal
import com.example.tranquilityfood.ui.fragments.HomeFragment
import com.example.tranquilityfood.viewmodel.MealViewModel
import com.example.tranquilityfood.viewmodel.MealViewModelFactory

class MealActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMealBinding
    private lateinit var idMeal: String
    private lateinit var nameMeal: String
    private lateinit var thumbMeal: String
    private lateinit var buttonYoutube: String
    private lateinit var mealViewModel: MealViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mealDatabase = MealDatabase.getInstance(this)
        val viewModelFactory = MealViewModelFactory(mealDatabase)
        mealViewModel = ViewModelProvider(this, viewModelFactory)[MealViewModel::class.java]

        getRandomMealInformation()
        setRandomMealInformation()

        mealViewModel.getDetailsLiveData(idMeal)
        observerGetMealInformation()

        onClickYoutubeButton()
        onFavoriteClick()
    }

    private fun onFavoriteClick() {
        binding.activityMealFlButton.setOnClickListener {
            mealToSave?.let {
                mealViewModel.insertMeal(it)
                Toast.makeText(this, "You add meal food", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onClickYoutubeButton() {
        binding.activityMealYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(buttonYoutube))
            startActivity(intent)
        }
    }

    private var mealToSave: Meal? = null
    private fun observerGetMealInformation() {
        mealViewModel.observeMealDetailsLiveData().observe(this
        ) { meal ->
            buttonYoutube = meal.strYoutube
            mealToSave = meal
            binding.activityMealTextCategories.text = "Category: ${meal!!.strCategory}"
            binding.activityMealTextCountry.text = "Country: ${meal.strArea}"
            binding.activityMealTextIntroduction.text = meal.strInstructions
            Log.d("MealCategory", "its: ${meal.strCategory}")
            Log.d("MealCategory", "its: ${meal.strArea}")
            Log.d("MealCategory", "its: ${meal.strInstructions}")
        }
    }

    private fun setRandomMealInformation() {
        Glide.with(applicationContext)
            .load(thumbMeal)
            .into(binding.activityMealImageview)

        binding.activityMealCollapsing.title = nameMeal
        binding.activityMealCollapsing.setCollapsedTitleTextColor(resources.getColor(R.color.red))
        binding.activityMealCollapsing.setExpandedTitleColor(resources.getColor(R.color.primary_luxury))

    }

    private fun getRandomMealInformation() {
        val intent = intent
        idMeal = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        nameMeal = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        thumbMeal = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }
}