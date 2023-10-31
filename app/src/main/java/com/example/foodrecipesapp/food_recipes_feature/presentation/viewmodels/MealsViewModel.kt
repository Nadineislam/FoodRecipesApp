package com.example.foodrecipesapp.food_recipes_feature.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodrecipesapp.utils.Resource
import com.example.foodrecipesapp.food_recipes_feature.data.models.Meal
import com.example.foodrecipesapp.food_recipes_feature.data.models.MealList
import com.example.foodrecipesapp.food_recipes_feature.data.repository.MealsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MealsViewModel @Inject constructor(
    private val mealRepository: MealsRepository
) : ViewModel() {
    private val _mealDetails: MutableStateFlow<Resource<MealList>> =
        MutableStateFlow(Resource.Loading())
    val mealDetails: StateFlow<Resource<MealList>> = _mealDetails

    fun getMealDetails(id: String) = viewModelScope.launch {
        val response = mealRepository.getMealDetails(id)
        _mealDetails.value = handleMealDetailsResponse(response)
    }

    private fun handleMealDetailsResponse(response: Response<MealList>): Resource<MealList> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun upsertMeal(meal: Meal) {
        viewModelScope.launch {
            mealRepository.upsertMeal(meal)
        }
    }

}