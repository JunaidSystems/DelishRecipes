package com.example.delishrecipes.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.delishrecipes.models.CategoriesList
import com.example.delishrecipes.repository.CategoryListRepo
import com.example.delishrecipes.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryListViewModel @Inject constructor(private val categoryListRepo: CategoryListRepo) :
    ViewModel() {

    val categoriesListLiveData: LiveData<NetworkResult<CategoriesList>>
        get() = categoryListRepo.categoriesListLiveData

    fun getCategoriesList() {
        viewModelScope.launch {
            categoryListRepo.getCategoryList()
        }
    }

}