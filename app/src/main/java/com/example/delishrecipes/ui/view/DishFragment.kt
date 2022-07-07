package com.example.delishrecipes.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.delishrecipes.databinding.FragmentDishBinding
import com.example.delishrecipes.ui.viewmodel.SpecificMealViewModel

class DishFragment : Fragment() {

    private var _binding: FragmentDishBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<SpecificMealViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDishBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getSpecificDish(getMealId())
        observeLivedata()
    }

    private fun getMealId(): String {
        return arguments?.getString("mealId")!!
    }

    private fun observeLivedata() {
        viewModel.specificDishLiveData.observe(viewLifecycleOwner, {
            it.data?.meals?.get(0)?.let {
                binding.apply {
                    tvRecipeName.text = it.strMeal ?: ""
                    tvInstructions.text = it.strInstructions ?: ""
                    context?.let { it1 -> Glide.with(it1).load(it.strMealThumb).into(ivIcon) }
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}