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
import com.example.delishrecipes.databinding.FragmentCategoryListBinding
import com.example.delishrecipes.models.Category
import com.example.delishrecipes.ui.adapter.CategoryAdapter
import com.example.delishrecipes.ui.viewmodel.CategoryListViewModel
import com.example.delishrecipes.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryListFragment : Fragment() {

    private var _binding: FragmentCategoryListBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: CategoryAdapter

    private val viewModel by activityViewModels<CategoryListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCategoryListBinding.inflate(inflater, container, false)
        viewModel.getCategoriesList()
        adapter = CategoryAdapter(::onCategoryClicked)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvCategories.layoutManager = GridLayoutManager(context, 2)
        binding.rvCategories.adapter = adapter
        observeLivedata()
    }

    private fun observeLivedata() {
        viewModel.categoriesListLiveData.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = false
            binding.rvCategories.isVisible = false
            binding.tvErrorMsg.isVisible = false

            it?.let {
                when (it) {
                    is NetworkResult.Success -> {
                        binding.rvCategories.isVisible = true
                        adapter.submitList(it.data?.categories)
                    }
                    is NetworkResult.Loading -> {
                        binding.progressBar.isVisible = true
                    }
                    is NetworkResult.Error -> {
                        binding.tvErrorMsg.isVisible = true
                        binding.tvErrorMsg.text = it.message
                    }
                }
            }
        }
    }

    private fun onCategoryClicked(categorySelected: Category) {
        val bundle = Bundle()
        bundle.putString("categoryName", categorySelected.strCategory)
        findNavController().navigate(
            R.id.action_categoryListFragment_to_specificCategoryFragment,
            bundle
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}