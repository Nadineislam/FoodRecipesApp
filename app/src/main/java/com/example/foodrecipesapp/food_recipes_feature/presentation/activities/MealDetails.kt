package com.example.foodrecipesapp.food_recipes_feature.presentation.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.example.foodrecipesapp.R
import com.example.foodrecipesapp.utils.Constants.Companion.MEAL_ID
import com.example.foodrecipesapp.utils.Constants.Companion.MEAL_NAME
import com.example.foodrecipesapp.utils.Constants.Companion.MEAL_THUMB
import com.example.foodrecipesapp.utils.Resource
import com.example.foodrecipesapp.food_recipes_feature.data.models.Meal
import com.example.foodrecipesapp.food_recipes_feature.presentation.viewmodels.MealsViewModel
import com.example.foodrecipesapp.ui.theme.FoodRecipesAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MealDetails : ComponentActivity() {
    private lateinit var mealId: String
    private lateinit var mealName: String
    private lateinit var mealThumb: String
    private val viewModel: MealsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = intent
        mealName = intent.getStringExtra(MEAL_NAME) ?: ""
        mealId = intent.getStringExtra(MEAL_ID) ?: ""
        mealThumb = intent.getStringExtra(MEAL_THUMB) ?: ""
        viewModel.getMealDetails(mealId)
        setContent {
            FoodRecipesAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GetMealsDetails(mealName, mealThumb, viewModel)
                }
            }
        }
    }
}

var savedMeal: Meal? = null

@Composable
fun GetMealsDetails(mealName: String, mealThumb: String, viewModel: MealsViewModel) {
    val mealDetails by viewModel.mealDetails.collectAsStateWithLifecycle()
    when (val resource = mealDetails) {
        is Resource.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is Resource.Success -> {
            val mealList = resource.data
            savedMeal = mealList?.meals?.firstOrNull()
            MealScreen(
                mealName = mealName,
                mealThumb = mealThumb,
                categoryName = savedMeal?.strCategory.toString(),
                areaName = savedMeal?.strArea.toString(),
                instructionsName = savedMeal?.strInstructions.toString(),
                youtubeLink = savedMeal?.strYoutube.toString(),
                viewModel=viewModel
            )
        }

        is Resource.Error -> {
            val message = resource.message ?: "Error fetching meal"
            Toast.makeText(LocalContext.current, message, Toast.LENGTH_LONG)
                .show()
        }
    }

}

@Composable
fun MealScreen(
    mealName: String,
    mealThumb: String,
    categoryName: String,
    areaName: String,
    instructionsName: String,
    youtubeLink:String,
    viewModel: MealsViewModel
) {
    val context= LocalContext.current
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                Box(modifier = Modifier.height(230.dp)) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        val painter = rememberAsyncImagePainter(model = mealThumb)
                        Image(
                            painter = painter,
                            contentDescription = "food",
                            contentScale = ContentScale.Crop
                        )
                    }

                    Text(
                        text = mealName,
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(10.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        fontStyle = FontStyle.Italic
                    )
                    FloatingActionButton(
                        onClick = {
                            savedMeal?.let {
                                viewModel.upsertMeal(it)
                                Toast.makeText(context, "Meal Saved", Toast.LENGTH_LONG)
                                    .show()
                            }
                           },
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(5.dp)
                            .size(50.dp)

                    ) {
                        Icon(
                            Icons.Filled.Favorite,
                            "Floating action button."
                        )
                    }

                }

                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxSize()
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    val (categoryText, areaText, categoryImage, instructionsText, areaImage) = createRefs()

                    Text(
                        modifier = Modifier
                            .constrainAs(categoryText) {
                                start.linkTo(categoryImage.end)
                                top.linkTo(parent.top)
                            }
                            .padding(5.dp),
                        text = categoryName,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Start
                    )
                    Image(painter = painterResource(id = R.drawable.ic_category),
                        contentDescription = "categoryImage",
                        modifier = Modifier.constrainAs(categoryImage) {
                            start.linkTo(parent.start)
                            end.linkTo(categoryText.start)
                            top.linkTo(categoryText.top)
                            bottom.linkTo(categoryText.bottom)
                        })
                    Text(
                        modifier = Modifier
                            .constrainAs(areaText) {
                                start.linkTo(categoryText.end)
                                end.linkTo(parent.end)
                                top.linkTo(parent.top)
                            }
                            .padding(5.dp),
                        text = areaName,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Start
                    )
                    Image(painter = painterResource(id = R.drawable.ic_location),
                        contentDescription = "location",
                        modifier = Modifier.constrainAs(areaImage) {
                            end.linkTo(areaText.start)
                            top.linkTo(areaText.top)
                            bottom.linkTo(areaText.bottom)
                        })

                    Text(
                        modifier = Modifier
                            .constrainAs(instructionsText) {
                                start.linkTo(parent.start)
                                top.linkTo(categoryText.bottom)
                            }
                            .padding(10.dp),
                        text = instructionsName,
                        fontSize = 15.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Start,
                        maxLines = 15
                    )
                }
            }
        }
        Image(
            painter = painterResource(id = R.drawable.ic_video),
            contentDescription = "video",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .size(50.dp)
                .clickable {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
                    context.startActivity(intent)
                }
        )

    }
}