package com.example.minitienda.coreUI

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.minitienda.R
import com.example.minitienda.navigation.Favorites
import com.example.minitienda.navigation.Products
import com.example.minitienda.ui.theme.AppGradient

@SuppressLint("RestrictedApi")
@Composable
fun BottomBar(navController: NavHostController) {

    val items = listOf(
        Products,
        Favorites
    )

    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    NavigationBar {

        items.forEach { screen ->

            val selected =
                currentBackStackEntry?.destination?.hasRoute(screen::class) == true

            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(screen) {
                        popUpTo(Products) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector =
                            if (screen == Products)
                                Icons.Default.Home
                            else
                                Icons.Default.Favorite,
                        contentDescription = null,
                        tint =  Color.Unspecified,
                        modifier = Modifier
                            .graphicsLayer(alpha = 0.99f)
                            .drawWithContent {
                                drawContent()
                                drawRect(brush = AppGradient.Primary, blendMode = BlendMode.SrcIn)
                            }
                    )
                },
                label = {
                    Text(
                        if (screen == Products)
                            stringResource(R.string.txt_products)
                        else
                            stringResource(R.string.txt_favorites)
                    )
                }
            )
        }
    }
}