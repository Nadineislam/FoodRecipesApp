package com.example.foodrecipesapp.food_recipes_feature.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodrecipesapp.utils.Resource
import com.example.foodrecipesapp.food_recipes_feature.data.models.MealsByCategoryList
import com.example.foodrecipesapp.food_recipes_feature.data.repository.MealsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class CategoryMealsViewModel @Inject constructor(
    private val mealRepository: MealsRepository
) :
    ViewModel() {
    private val _categoryMeals: MutableStateFlow<Resource<MealsByCategoryList>> =
        MutableStateFlow(Resource.Loading())
    val categoryMeals: StateFlow<Resource<MealsByCategoryList>> = _categoryMeals


    fun getMealsByCategory(categoryName: String) = viewModelScope.launch {
        val response = mealRepository.getMealsByCategory(categoryName)
        _categoryMeals.value = handleCategoryMealsLiveData(response)

    }

    private fun handleCategoryMealsLiveData(response: Response<MealsByCategoryList>): Resource<MealsByCategoryList> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

}