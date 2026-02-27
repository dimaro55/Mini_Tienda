package com.example.minitienda.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.minitienda.coreUI.AppHeader
import com.example.minitienda.coreUI.BottomBar

@Composable
fun AppRoot() {

    val navController = rememberNavController()

    val currentBackStackEntry =
        navController.currentBackStackEntryAsState()

    val currentDestination =
        currentBackStackEntry.value?.destination

    val showBottomBar =
        currentDestination?.route == Products::class.qualifiedName ||
                currentDestination?.route == Favorites::class.qualifiedName

    Scaffold(
        topBar = {
            AppHeader(
                title = "Mini Tienda",
            )
        },
        bottomBar = {
            if (showBottomBar) {
                BottomBar(navController)
            }
        }
    ) { padding ->

        AppNavigation(
            navController = navController,
            modifier = Modifier.padding(padding)
        )
    }
}