package com.example.tranquilityfood.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tranquilityfood.R
import com.example.tranquilityfood.databinding.FragmentCategoriesBinding
import com.example.tranquilityfood.ui.activities.MainActivity
import com.example.tranquilityfood.ui.adapters.CategoriesAdapter
import com.example.tranquilityfood.viewmodel.HomeViewModel

class CategoriesFragment : Fragment() {

    private lateinit var binding: FragmentCategoriesBinding
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoriesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()
        observeCategories()
    }

    private fun observeCategories() {
        viewModel.observerCategoriesMealLiveData().observe(viewLifecycleOwner, Observer {
            categoriesAdapter.setCategoryList(it)
        })
    }

    private fun prepareRecyclerView() {
        categoriesAdapter = CategoriesAdapter()

        binding.categoriesRv.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = categoriesAdapter
        }
    }
}