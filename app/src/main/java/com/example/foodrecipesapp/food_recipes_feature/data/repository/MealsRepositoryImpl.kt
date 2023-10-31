package com.example.foodrecipesapp.food_recipes_feature.data.repository

import com.example.foodrecipesapp.food_recipes_feature.data.api.ApiService
import com.example.foodrecipesapp.food_recipes_feature.data.db.MealDao
import com.example.foodrecipesapp.food_recipes_feature.data.models.Meal
import com.example.foodrecipesapp.food_recipes_feature.domain.repository.MealsRepository
import javax.inject.Inject

class MealsRepositoryImpl @Inject constructor(
    private val mealDao: MealDao,
    private val apiService: ApiService
) : MealsRepository {
    override suspend fun getRandomMeal() = apiService.getRandomMeal()
    override suspend fun getMealDetails(id: String) = apiService.getMealDetails(id)
    override suspend fun getMealsBySearch(mealName: String) = apiService.getMealsBySearch(mealName)
    override suspend fun getPopularMeals(categoryName: String) =
        apiService.getPopularMeals(categoryName)

    override suspend fun getCategory() = apiService.getCategory()
    override suspend fun getMealsByCategory(categoryName: String) =
        apiService.getMealsByCategory(categoryName)

    override suspend fun upsertMeal(meal: Meal) = mealDao.upsertMeal(meal)
    override suspend fun deleteMeal(meal: Meal) = mealDao.deleteMeal(meal)
    override fun getMeals() = mealDao.getMeals()


}