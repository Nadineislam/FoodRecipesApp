package com.example.foodrecipesapp.food_recipes_feature.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodrecipesapp.core.handleResponse
import com.example.foodrecipesapp.utils.Resource
import com.example.foodrecipesapp.food_recipes_feature.data.models.MealsByCategoryList
import com.example.foodrecipesapp.food_recipes_feature.domain.use_case.MealsByCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryMealsViewModel @Inject constructor(
    private val mealsByCategoryUseCase: MealsByCategoryUseCase
) :
    ViewModel() {
    private val _categoryMeals: MutableStateFlow<Resource<MealsByCategoryList>> =
        MutableStateFlow(Resource.Loading())
    val categoryMeals: StateFlow<Resource<MealsByCategoryList>> = _categoryMeals


    fun getMealsByCategory(categoryName: String) = viewModelScope.launch {
        val response = mealsByCategoryUseCase(categoryName)
        _categoryMeals.value = handleResponse(response)

    }

}