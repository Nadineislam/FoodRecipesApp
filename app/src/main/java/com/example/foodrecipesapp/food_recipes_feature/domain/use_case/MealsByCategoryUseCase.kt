package com.example.foodrecipesapp.food_recipes_feature.domain.use_case

import com.example.foodrecipesapp.food_recipes_feature.data.models.MealsByCategoryList
import com.example.foodrecipesapp.food_recipes_feature.domain.repository.MealsRepository
import retrofit2.Response
import javax.inject.Inject

class MealsByCategoryUseCase @Inject constructor(private val repository: MealsRepository) {
    suspend operator fun invoke(categoryName: String): Response<MealsByCategoryList> =
        repository.getMealsByCategory(categoryName)

}