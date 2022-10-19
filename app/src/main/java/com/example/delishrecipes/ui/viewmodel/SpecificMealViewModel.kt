package com.example.delishrecipes.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.delishrecipes.models.SpecificDish
import com.example.delishrecipes.repository.SpecificMealRepo
import com.example.delishrecipes.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpecificMealViewModel @Inject constructor(private val specificMealRepo: SpecificMealRepo) :
    ViewModel() {

    val specificDishLiveData: LiveData<NetworkResult<SpecificDish>>
        get() = specificMealRepo.specificMealLiveData

    private var strYoutubeId: String? = null

    fun getSpecificDish(id: String) {
        viewModelScope.launch {
            specificMealRepo.getSpecificMeal(id)
        }
    }

    fun setYoutubeId(completeUrl: String) {
        var id = ""
        if (!completeUrl.isNullOrEmpty()){
            id = completeUrl.substring(32)
        }
        strYoutubeId = id
    }

    fun getYoutubeId(): String? {
        return strYoutubeId
    }
}