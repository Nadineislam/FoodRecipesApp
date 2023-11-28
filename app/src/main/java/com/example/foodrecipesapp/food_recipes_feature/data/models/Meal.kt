package com.example.foodrecipesapp.food_recipes_feature.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MealTable")
data class Meal(
    @PrimaryKey
    val idMeal: String,
    val strArea: String?,
    val strCategory: String?,
    val strInstructions: String?,
    val strMeal: String?,
    val strMealThumb: String?,
    val strYoutube: String?
)