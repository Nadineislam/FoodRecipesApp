package com.example.foodrecipesapp.food_recipes_feature.domain.use_case

import com.example.foodrecipesapp.food_recipes_feature.data.models.MealList
import com.example.foodrecipesapp.food_recipes_feature.domain.repository.MealsRepository
import retrofit2.Response
import javax.inject.Inject

class SearchMealUseCase @Inject constructor(private val repository: MealsRepository) {
    suspend operator fun invoke(mealName: String): Response<MealList> =
        repository.getMealsBySearch(mealName)
}