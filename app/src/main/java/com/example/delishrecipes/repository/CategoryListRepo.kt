package com.example.delishrecipes.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.delishrecipes.api.CategoriesListAPI
import com.example.delishrecipes.models.CategoriesList
import com.example.delishrecipes.utils.NetworkResult
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class CategoryListRepo @Inject constructor(private val categoriesListAPI: CategoriesListAPI) {

    private val _categoriesListLiveData = MutableLiveData<NetworkResult<CategoriesList>>()
    val categoriesListLiveData: LiveData<NetworkResult<CategoriesList>> get() = _categoriesListLiveData

    suspend fun getCategoryList() {
        _categoriesListLiveData.postValue(NetworkResult.Loading())
        val response = categoriesListAPI.categoriesList()
        handleResponse(response)
    }

    private fun handleResponse(response: Response<CategoriesList>) {
        if (response.isSuccessful && response.body() != null) {
            _categoriesListLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _categoriesListLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _categoriesListLiveData.postValue(NetworkResult.Error("Something Went Wrong"))
        }
    }
}