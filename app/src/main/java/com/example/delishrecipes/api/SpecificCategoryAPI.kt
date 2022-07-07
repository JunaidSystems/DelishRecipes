package com.example.delishrecipes.api

import com.example.delishrecipes.models.MealsList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SpecificCategoryAPI {

    @GET("filter.php")
    suspend fun mealsList(
        @Query("c") category: String
    ): Response<MealsList>

}