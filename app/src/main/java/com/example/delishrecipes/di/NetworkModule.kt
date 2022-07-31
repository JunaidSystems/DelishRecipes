package com.example.delishrecipes.di

import com.example.delishrecipes.BuildConfig
import com.example.delishrecipes.api.CategoriesListAPI
import com.example.delishrecipes.api.SpecificCategoryAPI
import com.example.delishrecipes.api.SpecificMealAPI
import com.example.delishrecipes.utils.Constants
import com.example.delishrecipes.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {
        val logInterceptor = HttpLoggingInterceptor()
        logInterceptor.level = when (BuildConfig.DEBUG) {
            true -> HttpLoggingInterceptor.Level.BODY
            else -> HttpLoggingInterceptor.Level.NONE
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(logInterceptor)
            .readTimeout(Constants.APIConfig.TIMEOUT_DEFAULT, TimeUnit.SECONDS)
            .connectTimeout(Constants.APIConfig.TIMEOUT_DEFAULT, TimeUnit.SECONDS)
            .writeTimeout(Constants.APIConfig.TIMEOUT_DEFAULT, TimeUnit.SECONDS)
            .build()
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    fun provideCategoriesListApi(retrofit: Retrofit): CategoriesListAPI {
        return retrofit.create(CategoriesListAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideSpecificCategoryApi(retrofit: Retrofit): SpecificCategoryAPI {
        return retrofit.create(SpecificCategoryAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideSpecificMealApi(retrofit: Retrofit): SpecificMealAPI {
        return retrofit.create(SpecificMealAPI::class.java)
    }
}