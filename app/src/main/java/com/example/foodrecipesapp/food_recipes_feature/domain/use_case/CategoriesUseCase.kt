package com.example.foodrecipesapp.food_recipes_feature.domain.use_case

import com.example.foodrecipesapp.food_recipes_feature.data.models.CategoryList
import com.example.foodrecipesapp.food_recipes_feature.domain.repository.HomeRepository
import retrofit2.Response
import javax.inject.Inject

class CategoriesUseCase @Inject constructor(private val repository: HomeRepository) {
    suspend operator fun invoke(): Response<CategoryList> = repository.getCategory()
}