package com.example.delishrecipes.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.delishrecipes.models.MealsList
import com.example.delishrecipes.repository.SpecificCategoryRepo
import com.example.delishrecipes.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpecificCategoryViewModel @Inject constructor(private val specificCategoryRepo: SpecificCategoryRepo) :
    ViewModel() {

    val specificCategoryLiveData: LiveData<NetworkResult<MealsList>>
        get() = specificCategoryRepo.specificCategoriesLiveData

    fun getSpecificCategoryList(category: String) {
        viewModelScope.launch {
            specificCategoryRepo.getSpecificCategory(category)
        }
    }

}