package com.example.delishrecipes.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.delishrecipes.api.SpecificMealAPI
import com.example.delishrecipes.models.SpecificDish
import com.example.delishrecipes.utils.NetworkResult
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class SpecificMealRepo @Inject constructor(private val specificMealAPI: SpecificMealAPI) {

    private val _specificMealLiveData = MutableLiveData<NetworkResult<SpecificDish>>()
    val specificMealLiveData: LiveData<NetworkResult<SpecificDish>> get() = _specificMealLiveData

    suspend fun getSpecificMeal(id: String) {
        _specificMealLiveData.postValue(NetworkResult.Loading())
        val response = specificMealAPI.specificDish(id = id)
        handleResponse(response)
    }

    private fun handleResponse(response: Response<SpecificDish>) {
        if (response.isSuccessful && response.body() != null) {
            _specificMealLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _specificMealLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _specificMealLiveData.postValue(NetworkResult.Error("Something Went Wrong"))
        }
    }
}