package com.example.tranquilityfood.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.tranquilityfood.databinding.FragmentHomeBinding
import com.example.tranquilityfood.pojo.Meal
import com.example.tranquilityfood.ui.activities.MealActivity
import com.example.tranquilityfood.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    lateinit var randomMeal: Meal
    private lateinit var homeViewModel : HomeViewModel

    companion object {
        const val MEAL_ID = "com.example.tranquilityfood.ui.fragments.idMeal"
        const val MEAL_NAME = "com.example.tranquilityfood.ui.fragments.nameMeal"
        const val MEAL_THUMB = "com.example.tranquilityfood.ui.fragments.thumbMeal"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.getRandomMeal()
        observerRandomMeal()
        onClickRandomMeal()
    }

    private fun onClickRandomMeal() {
        binding.cvHomeImageRandom.setOnClickListener {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, randomMeal.idMeal)
            intent.putExtra(MEAL_NAME, randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB, randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observerRandomMeal() {
        homeViewModel.observeRandomMeal().observe(viewLifecycleOwner
        ) { meal ->
            Glide.with(this@HomeFragment)
                .load(meal!!.strMealThumb)
                .into(binding.cvHomeImageRandom)

            this.randomMeal = meal
        }
    }
}