package com.example.delishrecipes.api

import com.example.delishrecipes.models.CategoriesList
import retrofit2.Response
import retrofit2.http.GET

interface CategoriesListAPI {

    @GET("categories.php")
    suspend fun categoriesList():Response<CategoriesList>
}