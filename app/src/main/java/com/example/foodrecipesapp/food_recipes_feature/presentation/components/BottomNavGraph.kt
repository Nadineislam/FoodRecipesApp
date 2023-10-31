package com.example.foodrecipesapp.food_recipes_feature.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.foodrecipesapp.food_recipes_feature.presentation.viewmodels.HomeViewModel

@Composable
fun BottomNavGraph(
    viewModel: HomeViewModel,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route,
        modifier = modifier
    ) {
        composable(BottomBarScreen.Home.route) {
            HomeScreen(viewModel = viewModel, navController = navController)
        }
        composable(BottomBarScreen.Favorites.route) {
            FavoritesScreen(viewModel)
        }
        composable(BottomBarScreen.Categories.route) {
            CategoriesScreen(viewModel)
        }
        composable("Search") {
            SearchScreen(viewModel)
        }
    }
}