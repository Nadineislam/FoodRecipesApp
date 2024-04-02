package com.example.foodrecipesapp.food_recipes_feature.domain.repository

import com.example.foodrecipesapp.food_recipes_feature.data.models.MealsByCategoryList
import retrofit2.Response

interface CategoryMealsRepository {
    suspend fun getMealsByCategory(categoryName: String): Response<MealsByCategoryList>
}