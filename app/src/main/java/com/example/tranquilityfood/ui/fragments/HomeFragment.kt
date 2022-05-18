package com.example.tranquilityfood.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.tranquilityfood.databinding.FragmentHomeBinding
import com.example.tranquilityfood.pojo.MealsByCategory
import com.example.tranquilityfood.pojo.Meal
import com.example.tranquilityfood.ui.activities.MealActivity
import com.example.tranquilityfood.ui.adapters.PopularMealAdapter
import com.example.tranquilityfood.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    lateinit var randomMeal: Meal
    private lateinit var homeViewModel : HomeViewModel
    private lateinit var popularMealAdapter: PopularMealAdapter

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

        preparePopularItemsRecyclerView()

        homeViewModel.getRandomMeal()
        observerRandomMeal()
        onClickRandomMeal()

        homeViewModel.getPopularMeal()
        observerPopularMealLiveData()

        onPopularMealClick()

    }

    private fun onPopularMealClick() {

        popularMealAdapter.onItemClick = { meal ->
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, meal.idMeal)
            intent.putExtra(MEAL_NAME, meal.strMeal)
            intent.putExtra(MEAL_THUMB, meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun preparePopularItemsRecyclerView() {
        popularMealAdapter = PopularMealAdapter()

        binding.rcHomePopular.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularMealAdapter
        }
    }

    private fun observerPopularMealLiveData() {
        homeViewModel.observePopularMealLiveData().observe(viewLifecycleOwner
        ) { mealsList ->
            (mealsList as? ArrayList<MealsByCategory>)?.let { popularMealAdapter.setMeals(mealList = it) }
        }
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
        homeViewModel.observeRandomMealLiveData().observe(viewLifecycleOwner
        ) { meal ->
            Glide.with(this@HomeFragment)
                .load(meal!!.strMealThumb)
                .into(binding.cvHomeImageRandom)

            this.randomMeal = meal
        }
    }
}