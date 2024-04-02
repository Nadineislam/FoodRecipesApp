package com.example.foodrecipesapp.food_recipes_feature.data.repository

import com.example.foodrecipesapp.food_recipes_feature.data.api.ApiService
import com.example.foodrecipesapp.food_recipes_feature.data.db.MealDao
import com.example.foodrecipesapp.food_recipes_feature.data.models.Meal
import com.example.foodrecipesapp.food_recipes_feature.domain.repository.HomeRepository
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val mealDao: MealDao,
    private val apiService: ApiService
) : HomeRepository {
    override suspend fun getRandomMeal() = apiService.getRandomMeal()
    override suspend fun getMealsBySearch(mealName: String) = apiService.getMealsBySearch(mealName)
    override suspend fun getPopularMeals(categoryName: String) =
        apiService.getPopularMeals(categoryName)

    override suspend fun getCategory() = apiService.getCategory()
    override suspend fun deleteMeal(meal: Meal) = mealDao.deleteMeal(meal)
    override fun getMeals() = mealDao.getMeals()


}