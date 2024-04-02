package com.example.foodrecipesapp.food_recipes_feature.data.repository

import com.example.foodrecipesapp.food_recipes_feature.data.api.ApiService
import com.example.foodrecipesapp.food_recipes_feature.data.models.MealsByCategoryList
import com.example.foodrecipesapp.food_recipes_feature.domain.repository.CategoryMealsRepository
import retrofit2.Response
import javax.inject.Inject

class CategoryMealsRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : CategoryMealsRepository {
    override suspend fun getMealsByCategory(categoryName: String): Response<MealsByCategoryList> =
        apiService.getMealsByCategory(categoryName)

}