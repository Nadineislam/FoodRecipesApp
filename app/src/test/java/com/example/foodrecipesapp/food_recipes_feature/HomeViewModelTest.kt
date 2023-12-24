package com.example.foodrecipesapp.food_recipes_feature

import com.example.foodrecipesapp.base.MainCoroutineExt
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
import com.example.foodrecipesapp.food_recipes_feature.presentation.viewmodels.HomeViewModel
import com.example.foodrecipesapp.utils.Resource
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Response

@ExperimentalCoroutinesApi
class HomeViewModelTest {
    @get:Rule
    val mainCoroutineExt = MainCoroutineExt()

    @Mock
    private lateinit var mealsUseCase: MealsUseCase

    @Mock
    private lateinit var randomMealUseCase: RandomMealUseCase

    @Mock
    private lateinit var popularMealsUseCase: PopularMealsUseCase

    @Mock
    private lateinit var categoriesUseCase: CategoriesUseCase

    @Mock
    private lateinit var searchMealUseCase: SearchMealUseCase

    @Mock
    private lateinit var deleteMealUseCase: DeleteMealUseCase

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = HomeViewModel(
            mealsUseCase,
            randomMealUseCase,
            popularMealsUseCase,
            categoriesUseCase,
            searchMealUseCase,
            deleteMealUseCase
        )
    }

    @Test
    fun `when getRandomMeal is called with success state then the list of meals should be retrieved`() =
        runBlocking {
            val meal = Meal(
                idMeal = "mealId",
                strArea = "yourArea",
                strCategory = "yourCategory",
                strInstructions = "yourInstructions",
                strMeal = "yourMeal",
                strMealThumb = "yourMealThumb",
                strYoutube = "yourYoutube"
            )
            val response = Response.success(MealList(listOf(meal)))
            `when`(randomMealUseCase()).thenReturn(response)

            viewModel.getRandomMeal()

            assertEquals(meal, (viewModel.randomMeal.value as Resource.Success).data)
        }

    @Test
    fun `when getRandomMeal is called with failure state then error should be retrieved`() =
        runBlocking {
            val errorMessage = "Response.error()"
            val response = Response.error<MealList>(400, errorMessage.toResponseBody(null))
            `when`(randomMealUseCase()).thenReturn(response)

            viewModel.getRandomMeal()

            val errorState = viewModel.randomMeal.value as Resource.Error
            assertEquals(errorMessage, errorState.message)
        }

    @Test
    fun `when getPopularMeals is called with success state then the list of meals should be retrieved`() =
        runBlocking {
            val mockedResponse = Response.success(MealsByCategoryList(meals = listOf()))
            `when`(popularMealsUseCase("Seafood")).thenReturn(mockedResponse)

            viewModel.getPopularMeals()

            assertEquals(
                mockedResponse.body(),
                (viewModel.popularMeals.value as Resource.Success).data
            )
        }

    @Test
    fun `when getPopularMeals is called with failure state then error should be retrieved`() =
        runBlocking {
            val errorMessage = "Error message"
            val response =
                Response.error<MealsByCategoryList>(400, errorMessage.toResponseBody(null))
            `when`(popularMealsUseCase("Seafood")).thenReturn(response)

            viewModel.getPopularMeals()

            assertEquals(
                "Response.error()",
                (viewModel.popularMeals.value as Resource.Error).message
            )
        }

    @Test
    fun `when getCategories is called with success state then the list of categories should be retrieved`() =
        runBlocking {
            val mockedResponse = Response.success(CategoryList(categories = listOf()))
            `when`(categoriesUseCase()).thenReturn(mockedResponse)

            viewModel.getCategories()

            assertEquals(
                mockedResponse.body(),
                (viewModel.categories.value as Resource.Success).data
            )
        }

    @Test
    fun `when getCategories is called with failure state then error should be retrieved`() =
        runBlocking {
            val errorMessage = "Response.error()"
            val response = Response.error<CategoryList>(400, errorMessage.toResponseBody(null))
            `when`(categoriesUseCase()).thenReturn(response)

            viewModel.getCategories()

            assertEquals(errorMessage, (viewModel.categories.value as Resource.Error).message)
        }

    @Test
    fun `when deleteMeal is called then the meal should be removed`() = runBlocking {
        val meal = Meal(
            idMeal = "yourMealId",
            strArea = "yourArea",
            strCategory = "yourCategory",
            strInstructions = "yourInstructions",
            strMeal = "yourMeal",
            strMealThumb = "yourMealThumb",
            strYoutube = "yourYoutube"
        )

        viewModel.deleteMeal(meal)

        Mockito.verify(deleteMealUseCase).invoke(meal)
    }

    @Test
    fun `when searchMeal is called with success state then the list of meals should be retrieved`() =
        runBlocking {
            val searchQuery = "query"
            val mockedResponse = Response.success(MealList(meals = listOf()))
            `when`(searchMealUseCase(searchQuery)).thenReturn(mockedResponse)

            viewModel.searchMeal(searchQuery)

            assertEquals(
                mockedResponse.body(),
                (viewModel.searchMeal.value as Resource.Success).data
            )
        }

    @Test
    fun `when searchMeal is called with failure state then error should be retrieved`() =
        runBlocking {
            val errorMessage = "Response.error()"
            val response = Response.error<MealList>(400, errorMessage.toResponseBody(null))
            `when`(searchMealUseCase("query")).thenReturn(response)

            val searchQuery = "query"
            viewModel.searchMeal(searchQuery)

            assertEquals(errorMessage, (viewModel.searchMeal.value as Resource.Error).message)
        }

}