package com.example.foodrecipesapp.core

import com.example.foodrecipesapp.utils.Resource
import retrofit2.Response

inline fun <reified T> handleResponse(response: Response<T>): Resource<T> {
    return if (response.isSuccessful) {
        response.body()?.let { resultResponse ->
            Resource.Success(resultResponse)
        } ?: Resource.Error("Response body is null")
    } else {
        Resource.Error(response.message())
    }
}