package com.example.delishrecipes.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.delishrecipes.R
import com.example.delishrecipes.databinding.FragmentSpecificCategoryBinding
import com.example.delishrecipes.models.Meal
import com.example.delishrecipes.ui.adapter.SpecificCategoryAdapter
import com.example.delishrecipes.ui.viewmodel.SpecificCategoryViewModel
import com.example.delishrecipes.utils.NetworkResult

class SpecificCategoryFragment : Fragment() {

    private var _binding: FragmentSpecificCategoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: SpecificCategoryAdapter

    private val viewModel by activityViewModels<SpecificCategoryViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSpecificCategoryBinding.inflate(inflater, container, false)
        adapter = SpecificCategoryAdapter(::onMealClicked)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getSpecificCategoryList(getCategoryName())
        binding.rvCategories.adapter = adapter
        observeLivedata()
    }

    private fun getCategoryName(): String {
        return arguments?.getString("categoryName")!!
    }

    private fun observeLivedata() {
        viewModel.specificCategoryLiveData.observe(viewLifecycleOwner) {
            it?.let {
                binding.progressBar.isVisible = false
                it?.let {
                    when (it) {
                        is NetworkResult.Success -> {
                            binding.rvCategories.isVisible = true
                            adapter.submitList(it.data?.meals)
                        }
                        is NetworkResult.Loading -> {
                            binding.progressBar.isVisible = true
                            binding.rvCategories.isVisible = false
                        }
                        is NetworkResult.Error -> {
                            binding.tvErrorMsg.text = it.message
                        }
                    }
                }
            }
        }
    }

    private fun onMealClicked(mealSelected: Meal) {
        val bundle = Bundle()
        bundle.putString("mealId", mealSelected.idMeal)
        findNavController().navigate(R.id.action_specificCategoryFragment_to_dishFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}