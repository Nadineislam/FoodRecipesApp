package com.example.foodrecipesapp.food_recipes_feature.presentation.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.foodrecipesapp.food_recipes_feature.presentation.components.MainScreen
import com.example.foodrecipesapp.food_recipes_feature.presentation.viewmodels.HomeViewModel
import com.example.foodrecipesapp.ui.theme.FoodRecipesAppTheme
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel :HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dexOutputDir: File = codeCacheDir
        dexOutputDir.setReadOnly()
        setContent {
            FoodRecipesAppTheme {
                MainScreen(viewModel)
                }

        }
    }
}

