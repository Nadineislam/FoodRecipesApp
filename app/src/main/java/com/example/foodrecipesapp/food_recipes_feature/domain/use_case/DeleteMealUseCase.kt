package com.example.foodrecipesapp.food_recipes_feature.domain.use_case

import com.example.foodrecipesapp.food_recipes_feature.data.models.Meal
import com.example.foodrecipesapp.food_recipes_feature.domain.repository.MealsRepository
import javax.inject.Inject

class DeleteMealUseCase @Inject constructor(private val repository: MealsRepository) {
    suspend operator fun invoke(meal: Meal) = repository.deleteMeal(meal)
}