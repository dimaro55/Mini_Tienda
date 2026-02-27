package com.example.minitienda.ui.presentation.products


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.minitienda.coreUI.ProductItem


@Composable
fun ProductsScreen(
    viewModel: ProductsViewModel = hiltViewModel(),
    onClick: (Int) -> Unit

) {

    val state by viewModel.uiState.collectAsState()

    when (state) {

        is ProductsUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is ProductsUiState.Error -> {
            Text(
                text = (state as ProductsUiState.Error).message,
                modifier = Modifier.padding(16.dp)
            )
        }

        is ProductsUiState.Success -> {
            val products = (state as ProductsUiState.Success).products

            LazyColumn {
                items(products) { product ->
                    ProductItem(product) {
                        onClick(product.id)
                    }
                }
            }
        }
    }
}