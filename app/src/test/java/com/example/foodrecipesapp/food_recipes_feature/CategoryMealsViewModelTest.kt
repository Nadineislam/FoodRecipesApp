package com.example.foodrecipesapp.food_recipes_feature

import com.example.foodrecipesapp.base.MainCoroutineExt
import com.example.foodrecipesapp.food_recipes_feature.data.models.MealsByCategoryList
import com.example.foodrecipesapp.food_recipes_feature.domain.use_case.MealsByCategoryUseCase
import com.example.foodrecipesapp.food_recipes_feature.presentation.viewmodels.CategoryMealsViewModel
import com.example.foodrecipesapp.utils.Resource
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import retrofit2.Response

@ExperimentalCoroutinesApi
class CategoryMealsViewModelTest {
    private lateinit var mealsByCategoryUseCase: MealsByCategoryUseCase
    private lateinit var viewModel: CategoryMealsViewModel

    @Before
    fun setup() {
        mealsByCategoryUseCase = mock(MealsByCategoryUseCase::class.java)
        viewModel = CategoryMealsViewModel(mealsByCategoryUseCase)
    }

    @get:Rule
    val mainCoroutineExt = MainCoroutineExt()

    @Test
    fun `when getMealsByCategory is called with success state then the list of meals should be retrieved`() =
        runBlocking {
            val categoryName = "YourCategoryName"
            val mockedResponse = Response.success(MealsByCategoryList(listOf()))

            `when`(mealsByCategoryUseCase(categoryName)).thenReturn(mockedResponse)

            viewModel.getMealsByCategory(categoryName)

            assertEquals(
                mockedResponse.body(), (viewModel.categoryMeals.value as Resource.Success)
                    .data
            )
        }

    @Test
    fun `when getMealsByCategory is called with failure state then error should be retrieved`() =
        runBlocking {
            val categoryName = "YourCategoryName"
            val errorMessage = "Response.error()"
            val errorResponse =
                Response.error<MealsByCategoryList>(404, errorMessage.toResponseBody())

            `when`(mealsByCategoryUseCase(categoryName)).thenReturn(errorResponse)

            viewModel.getMealsByCategory(categoryName)

            assertEquals(errorMessage, (viewModel.categoryMeals.value as Resource.Error).message)
        }
}