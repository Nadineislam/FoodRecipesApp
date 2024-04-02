package com.example.foodrecipesapp.food_recipes_feature.domain.repository

import com.example.foodrecipesapp.food_recipes_feature.data.models.Meal
import com.example.foodrecipesapp.food_recipes_feature.data.models.MealList
import retrofit2.Response

interface MealsRepository {
    suspend fun getMealDetails(id: String): Response<MealList>
    suspend fun upsertMeal(meal: Meal)

}