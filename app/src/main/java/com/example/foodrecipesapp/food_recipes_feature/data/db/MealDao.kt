package com.example.foodrecipesapp.food_recipes_feature.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.foodrecipesapp.food_recipes_feature.data.models.Meal

@Dao
interface MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertMeal(meal: Meal)

    @Delete
    suspend fun deleteMeal(meal: Meal)

    @Query("SELECT * FROM MealTable")
    fun getMeals(): LiveData<List<Meal>>

}