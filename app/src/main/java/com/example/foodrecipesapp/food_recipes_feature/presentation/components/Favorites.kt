package com.example.foodrecipesapp.food_recipes_feature.presentation.components

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.asFlow
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.foodrecipesapp.R
import com.example.foodrecipesapp.utils.Constants.Companion.MEAL_ID
import com.example.foodrecipesapp.utils.Constants.Companion.MEAL_NAME
import com.example.foodrecipesapp.utils.Constants.Companion.MEAL_THUMB
import com.example.foodrecipesapp.food_recipes_feature.data.models.Meal
import com.example.foodrecipesapp.food_recipes_feature.presentation.activities.MealDetails
import com.example.foodrecipesapp.food_recipes_feature.presentation.viewmodels.HomeViewModel
import kotlinx.coroutines.launch


@Composable
fun FavoritesScreen(viewModel: HomeViewModel) {
    val meals by viewModel.observeFavoritesMealsStateFlow().asFlow()
        .collectAsState(initial = emptyList())

    Column(modifier = Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(150.dp)
        ) {
            items(meals) { meal ->
                MealItems(meal = meal, viewModel = viewModel)
            }
        }
    }

}

@Composable
fun MealItems(meal: Meal, viewModel: HomeViewModel) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current).data(data = meal.strMealThumb)
            .apply {
            }
            .build()
    )



    Card(
        modifier = Modifier
            .background(Color.White)
            .padding(13.dp)
            .clickable {
                val intent = Intent(context, MealDetails::class.java)
                intent.putExtra(MEAL_ID, meal.idMeal)
                intent.putExtra(MEAL_NAME, meal.strMeal)
                intent.putExtra(MEAL_THUMB, meal.strMealThumb)
                context.startActivity(intent)
            },
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        )
    ) {
        Column(modifier = Modifier.background(Color.White)) {
            Image(
                painter = painter,
                contentDescription = "Meal Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp)
            )

            Text(
                text = meal.strMeal ?: "",
                modifier = Modifier.padding(8.dp),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            )
            Image(
                painter = painterResource(id = R.drawable.delete),
                contentDescription = "delete", alignment = Alignment.BottomEnd,
                modifier = Modifier
                    .size(20.dp)
                    .padding(2.dp)
                    .clickable {
                        coroutineScope.launch {
                            Toast
                                .makeText(context, "Meal deleted", Toast.LENGTH_LONG)
                                .show()
                            viewModel.deleteMeal(meal)

                        }
                    }
            )
        }
    }
}