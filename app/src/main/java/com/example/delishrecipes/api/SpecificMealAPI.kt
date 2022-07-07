package com.example.delishrecipes.api

import com.example.delishrecipes.models.SpecificDish
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SpecificMealAPI {

    @GET("lookup.php")
    suspend fun specificDish(
        @Query("i") id: String
    ): Response<SpecificDish>
}