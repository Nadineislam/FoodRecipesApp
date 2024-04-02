package com.example.foodrecipesapp.food_recipes_feature.domain.use_case

import com.example.foodrecipesapp.food_recipes_feature.data.models.MealsByCategoryList
import com.example.foodrecipesapp.food_recipes_feature.domain.repository.HomeRepository
import retrofit2.Response
import javax.inject.Inject

class PopularMealsUseCase @Inject constructor(private val repository: HomeRepository) {
    suspend operator fun invoke(categoryName: String): Response<MealsByCategoryList> =
        repository.getPopularMeals(categoryName)
}