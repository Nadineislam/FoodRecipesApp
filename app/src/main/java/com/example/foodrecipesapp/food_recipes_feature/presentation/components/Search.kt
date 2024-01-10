package com.example.foodrecipesapp.food_recipes_feature.presentation.components

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.example.foodrecipesapp.core.GetResourceList
import com.example.foodrecipesapp.utils.Constants.Companion.MEAL_ID
import com.example.foodrecipesapp.utils.Constants.Companion.MEAL_NAME
import com.example.foodrecipesapp.utils.Constants.Companion.MEAL_THUMB
import com.example.foodrecipesapp.food_recipes_feature.data.models.Meal
import com.example.foodrecipesapp.food_recipes_feature.presentation.activities.MealDetails
import com.example.foodrecipesapp.food_recipes_feature.presentation.viewmodels.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(viewModel: HomeViewModel) {
    val searchQuery = remember { mutableStateOf("") }
    val searchMealState = viewModel.searchMeal.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TextField(
            value = searchQuery.value,
            onValueChange = { query ->
                searchQuery.value = query
                viewModel.searchMeal(query)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            shape = RoundedCornerShape(16.dp),
            placeholder = { Text(text = "Search Meals..") }
        )
        GetResourceList(
            resourceState = searchMealState.value,
            emptyListMessage = "Error fetching movies"
        ) { resource ->
            val movieList = resource?.meals
            movieList?.let { meals ->
                LazyVerticalGrid(columns = GridCells.Adaptive(150.dp)) {
                    items(meals) { meal ->
                        MealItem(meal = meal)
                    }
                }
            }
        }
    }
}


@Composable
fun MealItem(meal: Meal) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .padding(8.dp)
            .background(Color.White)
            .clickable {
                val intent = Intent(context, MealDetails::class.java)
                intent.putExtra(MEAL_ID, meal.idMeal)
                intent.putExtra(MEAL_NAME, meal.strMeal)
                intent.putExtra(MEAL_THUMB, meal.strMealThumb)
                context.startActivity(intent)
            },
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            val painter = rememberAsyncImagePainter(
                model = meal.strMealThumb
            )
            Image(
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp)
                    .clip(shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
            )

            Text(
                text = meal.strMeal ?: "",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                style = TextStyle(fontWeight = FontWeight.Bold),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
