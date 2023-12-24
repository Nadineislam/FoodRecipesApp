package com.example.foodrecipesapp.food_recipes_feature.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodrecipesapp.utils.Resource
import com.example.foodrecipesapp.food_recipes_feature.data.models.CategoryList
import com.example.foodrecipesapp.food_recipes_feature.data.models.Meal
import com.example.foodrecipesapp.food_recipes_feature.data.models.MealList
import com.example.foodrecipesapp.food_recipes_feature.data.models.MealsByCategoryList
import com.example.foodrecipesapp.food_recipes_feature.domain.use_case.CategoriesUseCase
import com.example.foodrecipesapp.food_recipes_feature.domain.use_case.DeleteMealUseCase
import com.example.foodrecipesapp.food_recipes_feature.domain.use_case.MealsUseCase
import com.example.foodrecipesapp.food_recipes_feature.domain.use_case.PopularMealsUseCase
import com.example.foodrecipesapp.food_recipes_feature.domain.use_case.RandomMealUseCase
import com.example.foodrecipesapp.food_recipes_feature.domain.use_case.SearchMealUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    mealsUseCase: MealsUseCase,
    private val randomMealUseCase: RandomMealUseCase,
    private val popularMealsUseCase: PopularMealsUseCase,
    private val categoriesUseCase: CategoriesUseCase,
    private val searchMealUseCase: SearchMealUseCase,
    private val deleteMealUseCase: DeleteMealUseCase
) : ViewModel() {
    private val _randomMeal: MutableStateFlow<Resource<Meal>> = MutableStateFlow(Resource.Loading())
    val randomMeal: StateFlow<Resource<Meal>> = _randomMeal

    private val _popularMeals: MutableStateFlow<Resource<MealsByCategoryList>> =
        MutableStateFlow(Resource.Loading())
    val popularMeals: StateFlow<Resource<MealsByCategoryList>> = _popularMeals

    private val _categories: MutableStateFlow<Resource<CategoryList>> =
        MutableStateFlow(Resource.Loading())
    val categories: StateFlow<Resource<CategoryList>> = _categories

    private var favoritesMealsLiveData = mealsUseCase()

    private val _searchMeal = MutableStateFlow<Resource<MealList>>(Resource.Loading())
    val searchMeal: StateFlow<Resource<MealList>> = _searchMeal

    init {
        getRandomMeal()
        getPopularMeals()
        getCategories()
    }

    fun getRandomMeal() = viewModelScope.launch {
        val response = randomMealUseCase()
        if (response.isSuccessful) {
            val mealList = response.body()
            val randomMeal = mealList?.meals?.firstOrNull()
            _randomMeal.value = Resource.Success(randomMeal as Meal)
        } else {
            val errorMessage = response.message()
            _randomMeal.value = Resource.Error(errorMessage)
        }

    }

     fun getCategories() = viewModelScope.launch {
        val response = categoriesUseCase()
        _categories.value = handleCategoriesResponse(response)
    }

    private fun handleCategoriesResponse(response: Response<CategoryList>): Resource<CategoryList> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

     fun getPopularMeals() = viewModelScope.launch {
        val response = popularMealsUseCase("Seafood")
        _popularMeals.value = handlePopularMealsResponse(response)
    }

    private fun handlePopularMealsResponse(response: Response<MealsByCategoryList>): Resource<MealsByCategoryList> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun searchMeal(searchQuery: String) = viewModelScope.launch {
        val response = searchMealUseCase(searchQuery)
        _searchMeal.value = handleSearchMealResponse(response)
    }

    private fun handleSearchMealResponse(response: Response<MealList>): Resource<MealList> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun deleteMeal(meal: Meal) {
        viewModelScope.launch {
            deleteMealUseCase(meal)
        }
    }

    fun observeFavoritesMealsStateFlow(): LiveData<List<Meal>> {
        return favoritesMealsLiveData
    }
}