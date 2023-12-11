package com.example.foodrecipesapp.food_recipes_feature.presentation.components

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.foodrecipesapp.R
import com.example.foodrecipesapp.utils.Constants.Companion.CATEGORY_NAME
import com.example.foodrecipesapp.utils.Resource
import com.example.foodrecipesapp.food_recipes_feature.data.models.Category
import com.example.foodrecipesapp.food_recipes_feature.data.models.Meal
import com.example.foodrecipesapp.food_recipes_feature.data.models.MealsByCategory
import com.example.foodrecipesapp.food_recipes_feature.presentation.activities.CategoryMeals
import com.example.foodrecipesapp.food_recipes_feature.presentation.activities.MealDetails
import com.example.foodrecipesapp.food_recipes_feature.presentation.viewmodels.HomeViewModel
import com.example.foodrecipesapp.ui.theme.Accent

@Composable
fun HomeScreen(viewModel: HomeViewModel, navController: NavController) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            HomeLayout(navController)
            TitleText(text = "What would you like to eat?")
            GetRandomMeal(viewModel = viewModel)
            TitleText(text = "Popular items")
            GetPopularMeals(viewModel = viewModel)
            TitleText(text = "Categories")
            GetCategoriesMeal(viewModel = viewModel)
        }
    }
}


@Composable
fun GetCategoriesMeal(viewModel: HomeViewModel) {
    val categoryMeals by viewModel.categories.collectAsStateWithLifecycle()
    when (val resource = categoryMeals) {
        is Resource.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is Resource.Success -> {
            val categories = resource.data?.categories
            CategoriesMeal(categories = categories ?: emptyList())
        }

        is Resource.Error -> {
            val message = resource.message ?: "Error fetching meal"
            Toast.makeText(LocalContext.current, message, Toast.LENGTH_LONG)
                .show()
        }
    }
}

@Composable
fun GetPopularMeals(viewModel: HomeViewModel) {
    val popularMealsState by viewModel.popularMeals.collectAsStateWithLifecycle()
    when (val resource = popularMealsState) {
        is Resource.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is Resource.Success -> {
            val meals = resource.data?.meals
            PopularMealList(mealThumbs = meals ?: emptyList())
        }

        is Resource.Error -> {
            val message = resource.message ?: "Error fetching news"
            Toast.makeText(LocalContext.current, message, Toast.LENGTH_LONG)
                .show()
        }
    }
}

@Composable
fun HomeLayout(navController: NavController) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Home",
            color = Accent,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(6.dp),
            fontFamily = FontFamily.Serif
        )
        Spacer(modifier = Modifier.width(260.dp))
        Image(
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = "search", modifier = Modifier
                .padding(10.dp)
                .size(30.dp)
                .clickable { navController.navigate("Search") }
        )
    }
}

@Composable
fun TitleText(text: String) {
    Text(
        text = text,
        modifier = Modifier.padding(vertical = 8.dp, horizontal = 10.dp),
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun GetRandomMeal(viewModel: HomeViewModel) {
    val randomMeal = viewModel.randomMeal.collectAsStateWithLifecycle()
    when (val resource = randomMeal.value) {
        is Resource.Success -> {
            val meal = resource.data
            RandomMeal(meal = meal!!)
        }

        is Resource.Error -> {
            val message = resource.message ?: "Error fetching meal"
            Toast.makeText(LocalContext.current, message, Toast.LENGTH_LONG)
                .show()
        }

        is Resource.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }

        }
    }
}

@Composable
fun CategoriesMeal(categories: List<Category>) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
    ) {
        LazyVerticalGrid(columns = GridCells.Adaptive(100.dp)) {
            items(categories) { category ->
                Card(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(15.dp)
                        .clickable {
                            val intent = Intent(context, CategoryMeals::class.java)
                            intent.putExtra(CATEGORY_NAME, category.strCategory)
                            context.startActivity(intent)
                        },
                    shape = RoundedCornerShape(10.dp),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 2.dp
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .background(Color.White),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        val painter: Painter = rememberImagePainter(
                            data = category.strCategoryThumb,
                            builder = {
                                placeholder(R.drawable.food)
                                error(R.drawable.food)
                            }
                        )
                        Image(
                            painter = painter,
                            contentDescription = "food",
                            contentScale = ContentScale.Crop
                        )
                        Text(
                            text = category.strCategory ?: "", color = Color.Black,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PopularMealList(mealThumbs: List<MealsByCategory>) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
    ) {
        LazyRow {
            items(mealThumbs) { meal ->
                Card(
                    modifier = Modifier
                        .padding(9.dp)
                        .height(100.dp)
                        .width(180.dp)
                        .clickable {
                            val intent = Intent(context, MealDetails::class.java)
                            intent.putExtra("MEAL_ID", meal.idMeal)
                            intent.putExtra("MEAL_NAME", meal.strMeal)
                            intent.putExtra("MEAL_THUMB", meal.strMealThumb)
                            context.startActivity(intent)
                        }
                ) {
                    val painter = rememberAsyncImagePainter(
                        model = meal.strMealThumb
                    )
                    Image(
                        painter = painter,
                        contentDescription = "food",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

            }
        }
    }
}

@Composable
fun RandomMeal(meal: Meal) {
    val context = LocalContext.current
    Card(
        shape = RoundedCornerShape(22.dp),
        modifier = Modifier
            .height(220.dp)
            .padding(12.dp)
            .clickable {
                val intent = Intent(context, MealDetails::class.java)
                intent.putExtra("MEAL_ID", meal.idMeal)
                intent.putExtra("MEAL_NAME", meal.strMeal)
                intent.putExtra("MEAL_THUMB", meal.strMealThumb)
                context.startActivity(intent)
            }
    ) {
        val painter = rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current)
                .data(data = meal.strMealThumb).build()
        )
        Image(
            painter = painter,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop,
            contentDescription = "food"
        )
    }
}