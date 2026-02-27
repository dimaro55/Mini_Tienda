package com.example.minitienda.ui.presentation.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.minitienda.coreUI.ImageCarousel

@Composable
fun DetailScreen(
    productId: Int,
    viewModel: DetailViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()
    val isFavorite by viewModel.isFavorite.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadProduct(productId)
    }

    when (uiState) {

        is DetailUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is DetailUiState.Error -> {
            Text(
                text = (uiState as DetailUiState.Error).message,
                modifier = Modifier.padding(16.dp)
            )
        }

        is DetailUiState.Success -> {

            val product = (uiState as DetailUiState.Success).product

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {

                ImageCarousel(images = product.images)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = product.title,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text = "$${product.price}",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = "Rating: ${product.rating}"
                        )
                    }

                    IconButton(
                        onClick = {
                            viewModel.toggleFavorite(product)
                        }
                    ) {
                        Icon(
                            imageVector =
                                if (isFavorite)
                                    Icons.Default.Favorite
                                else
                                    Icons.Default.FavoriteBorder,
                            contentDescription = "Favorite",
                            tint = if (isFavorite)
                                Color.Red
                            else
                                Color.Gray
                        )
                    }
                }

                Text(
                    text = product.description,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}