package com.example.foodrecipesapp.food_recipes_feature.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodrecipesapp.core.handleResponse
import com.example.foodrecipesapp.utils.Resource
import com.example.foodrecipesapp.food_recipes_feature.data.models.Meal
import com.example.foodrecipesapp.food_recipes_feature.data.models.MealList
import com.example.foodrecipesapp.food_recipes_feature.domain.use_case.MealDetailsUseCase
import com.example.foodrecipesapp.food_recipes_feature.domain.use_case.UpsertMealUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealsViewModel @Inject constructor(
    private val mealDetailsUseCase: MealDetailsUseCase,
    private val upsertMealUseCase: UpsertMealUseCase
) : ViewModel() {
    private val _mealDetails: MutableStateFlow<Resource<MealList>> =
        MutableStateFlow(Resource.Loading())
    val mealDetails = _mealDetails.asStateFlow()

    fun getMealDetails(id: String) = viewModelScope.launch {
        val response = mealDetailsUseCase(id)
        _mealDetails.value = handleResponse(response)
    }

    fun upsertMeal(meal: Meal) {
        viewModelScope.launch {
            upsertMealUseCase(meal)
        }
    }

}