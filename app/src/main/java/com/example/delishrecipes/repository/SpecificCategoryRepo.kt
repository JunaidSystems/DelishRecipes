package com.example.delishrecipes.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.delishrecipes.api.SpecificCategoryAPI
import com.example.delishrecipes.models.MealsList
import com.example.delishrecipes.utils.NetworkResult
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class SpecificCategoryRepo @Inject constructor(private val specificCategoryAPI: SpecificCategoryAPI) {

    private val _specificCategoryLiveData = MutableLiveData<NetworkResult<MealsList>>()
    val specificCategoriesLiveData: LiveData<NetworkResult<MealsList>> get() = _specificCategoryLiveData

    suspend fun getSpecificCategory(category: String) {
        _specificCategoryLiveData.postValue(NetworkResult.Loading())
        try {
            val response = specificCategoryAPI.mealsList(category = category)
            handleResponse(response)
        } catch (e: java.lang.Exception) {
            _specificCategoryLiveData.postValue(NetworkResult.Error("Something Went Wrong, Please check you internet connection!"))
        }
    }

    private fun handleResponse(response: Response<MealsList>) {
        if (response.isSuccessful && response.body() != null) {
            _specificCategoryLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _specificCategoryLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _specificCategoryLiveData.postValue(NetworkResult.Error("Something Went Wrong"))
        }
    }
}