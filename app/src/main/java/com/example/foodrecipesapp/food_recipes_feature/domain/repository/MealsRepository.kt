package com.example.foodrecipesapp.food_recipes_feature.domain.repository

import androidx.lifecycle.LiveData
import com.example.foodrecipesapp.food_recipes_feature.data.models.CategoryList
import com.example.foodrecipesapp.food_recipes_feature.data.models.Meal
import com.example.foodrecipesapp.food_recipes_feature.data.models.MealList
import com.example.foodrecipesapp.food_recipes_feature.data.models.MealsByCategoryList
import retrofit2.Response

interface MealsRepository {
    suspend fun getRandomMeal(): Response<MealList>
    suspend fun getMealDetails(id: String):Response<MealList>
    suspend fun getMealsBySearch(mealName: String):Response<MealList>
    suspend fun getPopularMeals(categoryName: String):Response<MealsByCategoryList>
    suspend fun getCategory():Response<CategoryList>
    suspend fun getMealsByCategory(categoryName: String):Response<MealsByCategoryList>
    suspend fun upsertMeal(meal: Meal)
    suspend fun deleteMeal(meal: Meal)
    fun getMeals(): LiveData<List<Meal>>
}