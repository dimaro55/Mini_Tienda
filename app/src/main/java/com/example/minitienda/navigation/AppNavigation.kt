package com.example.minitienda.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.minitienda.ui.presentation.details.DetailScreen
import com.example.minitienda.ui.presentation.favorites.FavoritesScreen
import com.example.minitienda.ui.presentation.products.ProductsScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    NavHost(
        navController = navController,
        startDestination = Products,
        modifier = modifier
    ) {

        composable<Products> {
            ProductsScreen(
                onClick = { id ->
                    navController.navigate(Details(id))
                }
            )
        }

        composable<Details>{ backStackEntry ->
            val route = backStackEntry.toRoute<Details>()
            DetailScreen(productId = route.productId)

        }

        composable<Favorites> {
            FavoritesScreen(
                onProductClick = { id ->
                    navController.navigate(Details(id))
                }
            )
        }

    }

}