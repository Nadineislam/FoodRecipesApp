package com.example.foodrecipesapp.food_recipes_feature.domain.use_case

import androidx.lifecycle.LiveData
import com.example.foodrecipesapp.food_recipes_feature.data.models.Meal
import com.example.foodrecipesapp.food_recipes_feature.domain.repository.MealsRepository
import javax.inject.Inject

class MealsUseCase @Inject constructor(private val repository: MealsRepository) {
    operator fun invoke(): LiveData<List<Meal>> = repository.getMeals()
}