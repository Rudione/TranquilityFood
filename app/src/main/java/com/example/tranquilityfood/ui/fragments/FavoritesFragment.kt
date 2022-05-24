package com.example.tranquilityfood.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.tranquilityfood.R
import com.example.tranquilityfood.databinding.FragmentFavoritesBinding
import com.example.tranquilityfood.ui.activities.MainActivity
import com.example.tranquilityfood.ui.adapters.FavoritesMealsAdapter
import com.example.tranquilityfood.viewmodel.HomeViewModel
import com.google.android.material.snackbar.Snackbar


class FavoritesFragment : Fragment() {
    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var favoritesMealsAdapter: FavoritesMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()
        observeFavorites()

        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
             ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                viewModel.deleteMeal(favoritesMealsAdapter.differ.currentList[position])

                Snackbar.make(requireView(), "Meal deleted", Snackbar.LENGTH_LONG).setAction(
                    "Undo",
                    View.OnClickListener {
                        viewModel.insertMeal(favoritesMealsAdapter.differ.currentList[position])
                    }
                ).show()
            }
        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.favoritesRv)
    }

    private fun prepareRecyclerView() {
        favoritesMealsAdapter = FavoritesMealsAdapter()
        binding.favoritesRv.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = favoritesMealsAdapter
        }
    }

    private fun observeFavorites() {
        viewModel.observeFavoritesMealLiveData().observe(requireActivity(), Observer {
            favoritesMealsAdapter.differ.submitList(it)
        })
    }
}