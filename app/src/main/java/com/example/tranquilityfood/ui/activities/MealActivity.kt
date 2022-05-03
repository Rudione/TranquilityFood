package com.example.tranquilityfood.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.tranquilityfood.R
import com.example.tranquilityfood.databinding.ActivityMealBinding
import com.example.tranquilityfood.ui.fragments.HomeFragment

class MealActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMealBinding
    private lateinit var idMeal: String
    private lateinit var nameMeal: String
    private lateinit var thumbMeal: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getRandomMealInformation()

        setRandomMealInformation()
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