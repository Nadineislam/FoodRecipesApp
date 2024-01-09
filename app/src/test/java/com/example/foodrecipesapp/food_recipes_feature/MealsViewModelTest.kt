package com.example.foodrecipesapp.food_recipes_feature

import com.example.foodrecipesapp.base.MainCoroutineExt
import com.example.foodrecipesapp.food_recipes_feature.data.models.Meal
import com.example.foodrecipesapp.food_recipes_feature.data.models.MealList
import com.example.foodrecipesapp.food_recipes_feature.domain.use_case.MealDetailsUseCase
import com.example.foodrecipesapp.food_recipes_feature.domain.use_case.UpsertMealUseCase
import com.example.foodrecipesapp.food_recipes_feature.presentation.viewmodels.MealsViewModel
import com.example.foodrecipesapp.utils.Resource
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import retrofit2.Response

@ExperimentalCoroutinesApi
class MealsViewModelTest {
    private lateinit var mealDetailsUseCase: MealDetailsUseCase
    private lateinit var upsertMealUseCase: UpsertMealUseCase
    private lateinit var viewModel: MealsViewModel

    @Before
    fun setup() {
        mealDetailsUseCase = mock(MealDetailsUseCase::class.java)
        upsertMealUseCase = mock(UpsertMealUseCase::class.java)
        viewModel = MealsViewModel(mealDetailsUseCase, upsertMealUseCase)
    }

    @get:Rule
    val mainCoroutineExt = MainCoroutineExt()

    @Test
    fun `when getMealDetails is called with success state then the list of meals should be retrieved`() =
        runBlocking {
            val id = "mealId"
            val mockedResponse = Response.success(MealList(listOf()))
            `when`(mealDetailsUseCase(id)).thenReturn(mockedResponse)

            viewModel.getMealDetails(id)

            assertTrue(
                (viewModel.mealDetails.value as Resource.Success)
                    .data == mockedResponse.body()
            )
        }

    @Test
    fun `when getMealDetails is called with failure state then error should be retrieved`() =
        runBlocking {
            val id = "mealId"
            val errorMessage = "Response.error()"
            val mockedResponse = Response.error<MealList>(400, errorMessage.toResponseBody(null))
            `when`(mealDetailsUseCase(id)).thenReturn(mockedResponse)

            viewModel.getMealDetails(id)

            assertEquals(
                errorMessage,
                (viewModel.mealDetails.value as Resource.Error).message
            )

        }

    @Test
    fun `when upsertMeal is called then the meal should be asserted`() = runBlocking {
        val meal = Meal(
            idMeal = "yourMealId",
            strArea = "yourArea",
            strCategory = "yourCategory",
            strInstructions = "yourInstructions",
            strMeal = "yourMeal",
            strMealThumb = "yourMealThumb",
            strYoutube = "yourYoutube"
        )

        viewModel.upsertMeal(meal)

        verify(upsertMealUseCase).invoke(meal)
    }
}
