package com.example.foodrecipesapp.food_recipes_feature.presentation.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.foodrecipesapp.food_recipes_feature.presentation.components.GetResourceList
import com.example.foodrecipesapp.utils.Constants.Companion.MEAL_ID
import com.example.foodrecipesapp.utils.Constants.Companion.MEAL_NAME
import com.example.foodrecipesapp.utils.Constants.Companion.MEAL_THUMB
import com.example.foodrecipesapp.food_recipes_feature.data.models.MealsByCategory
import com.example.foodrecipesapp.food_recipes_feature.presentation.activities.ui.theme.FoodRecipesAppTheme
import com.example.foodrecipesapp.food_recipes_feature.presentation.viewmodels.CategoryMealsViewModel
import com.example.foodrecipesapp.utils.Constants.Companion.CATEGORY_NAME
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryMeals : ComponentActivity() {
    private val categoryMealsViewModel: CategoryMealsViewModel by viewModels()
    private lateinit var categoryName: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = intent
        categoryName = intent.getStringExtra(CATEGORY_NAME) ?: ""
        categoryMealsViewModel.getMealsByCategory(categoryName)
        setContent {
            FoodRecipesAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GetCategoriesMealsItems(viewModel = categoryMealsViewModel)
                }
            }
        }
    }
}

@Composable
fun GetCategoriesMealsItems(viewModel: CategoryMealsViewModel) {
    val categoryMealsState by viewModel.categoryMeals.collectAsStateWithLifecycle()
    GetResourceList(
        resourceState = categoryMealsState,
        emptyListMessage = "Error fetching people"
    ) { mealsList ->
        CategoryMealsItems(mealsByCategory = mealsList?.meals ?: emptyList())
    }
}

@Composable
fun CategoryMealsItems(mealsByCategory: List<MealsByCategory>) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
    ) {
        LazyVerticalGrid(columns = GridCells.Adaptive(150.dp)) {
            items(mealsByCategory) { category ->
                Card(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(15.dp)
                        .clickable {
                            val intent = Intent(context, MealDetails::class.java)
                            intent.putExtra(MEAL_ID, category.idMeal)
                            intent.putExtra(MEAL_NAME, category.strMeal)
                            intent.putExtra(MEAL_THUMB, category.strMealThumb)
                            context.startActivity(intent)
                        },
                    shape = RoundedCornerShape(10.dp),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 1.dp
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .background(Color.White),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        AsyncImage(
                            model = category.strMealThumb,
                            contentDescription = "food",
                            contentScale = ContentScale.Crop
                        )
                        Text(
                            text = category.strMeal ?: "", color = Color.Black,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                }
            }
        }
    }
}