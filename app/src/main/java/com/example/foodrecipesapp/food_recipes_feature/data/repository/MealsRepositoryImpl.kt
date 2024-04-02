package com.example.foodrecipesapp.food_recipes_feature.data.repository

import com.example.foodrecipesapp.food_recipes_feature.data.api.ApiService
import com.example.foodrecipesapp.food_recipes_feature.data.db.MealDao
import com.example.foodrecipesapp.food_recipes_feature.data.models.Meal
import com.example.foodrecipesapp.food_recipes_feature.domain.repository.MealsRepository
import javax.inject.Inject

class MealsRepositoryImpl@Inject constructor(
    private val mealDao: MealDao,
    private val apiService: ApiService
):MealsRepository {
    override suspend fun getMealDetails(id: String) = apiService.getMealDetails(id)
    override suspend fun upsertMeal(meal: Meal) = mealDao.upsertMeal(meal)

}