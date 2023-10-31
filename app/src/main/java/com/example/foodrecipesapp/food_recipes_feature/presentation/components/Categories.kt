package com.example.foodrecipesapp.food_recipes_feature.presentation.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.foodrecipesapp.food_recipes_feature.presentation.viewmodels.HomeViewModel

@Composable
fun CategoriesScreen(viewModel: HomeViewModel) {
    Text(text = "Category")
    GetCategoriesMeal(viewModel = viewModel)


}
