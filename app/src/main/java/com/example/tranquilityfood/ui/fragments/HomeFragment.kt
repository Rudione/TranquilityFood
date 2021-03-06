package com.example.tranquilityfood.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.tranquilityfood.R
import com.example.tranquilityfood.databinding.FragmentHomeBinding
import com.example.tranquilityfood.domain.MealsByCategory
import com.example.tranquilityfood.domain.Meal
import com.example.tranquilityfood.ui.activities.CategoryMealsActivity
import com.example.tranquilityfood.ui.activities.MainActivity
import com.example.tranquilityfood.ui.activities.MealActivity
import com.example.tranquilityfood.ui.adapters.CategoriesAdapter
import com.example.tranquilityfood.ui.adapters.PopularMealAdapter
import com.example.tranquilityfood.ui.fragments.bottomsheet.MealBottomSheetFragment
import com.example.tranquilityfood.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    lateinit var randomMeal: Meal
    private lateinit var viewModel : HomeViewModel
    private lateinit var popularMealAdapter: PopularMealAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter

    companion object {
        const val MEAL_ID = "com.example.tranquilityfood.ui.fragments.idMeal"
        const val MEAL_NAME = "com.example.tranquilityfood.ui.fragments.nameMeal"
        const val MEAL_THUMB = "com.example.tranquilityfood.ui.fragments.thumbMeal"
        const val CATEGORY_NAME = "com.example.tranquilityfood.ui.fragments.categoryName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
        popularMealAdapter = PopularMealAdapter()
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

        viewModel.getRandomMeal()
        observerRandomMeal()
        onClickRandomMeal()

        viewModel.getPopularMeal()
        observerPopularMealLiveData()
        onPopularMealClick()

        prepareCategoriesRecyclerView()
        viewModel.getCategoriesMeal()
        observerCategoriesLiveData()
        onCategoryClicks()

        onLongItemClick()

        onSearchIconClick()
    }

    private fun onSearchIconClick() {
        binding.llHomeSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment2)
        }
    }

    private fun onLongItemClick() {
        popularMealAdapter.onLongItemClick = {
            val mealBottomSheetFragment = MealBottomSheetFragment.newInstance(it.idMeal)
            mealBottomSheetFragment.show(childFragmentManager, "Meal Info")
        }
    }

    private fun onCategoryClicks() {
        categoriesAdapter.onItemClick = {
            val intent = Intent(activity, CategoryMealsActivity::class.java)
            intent.putExtra(CATEGORY_NAME, it.strCategory)
            startActivity(intent)
        }
    }

    private fun prepareCategoriesRecyclerView() {
        categoriesAdapter = CategoriesAdapter()

        binding.rcHomeCategories.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = categoriesAdapter
        }
    }

    private fun observerCategoriesLiveData() {
        viewModel.observerCategoriesMealLiveData().observe(viewLifecycleOwner, Observer {
                categoriesAdapter.setCategoryList(it)
        })
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
        viewModel.observePopularMealLiveData().observe(viewLifecycleOwner
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
        viewModel.observeRandomMealLiveData().observe(viewLifecycleOwner
        ) { meal ->
            Glide.with(this@HomeFragment)
                .load(meal!!.strMealThumb)
                .into(binding.cvHomeImageRandom)

            this.randomMeal = meal
        }
    }
}