package com.example.minitienda.ui.presentation.products

import com.example.minitienda.domain.model.Product

sealed interface ProductsUiState {
    object Loading : ProductsUiState
    data class Success(val products: List<Product>) : ProductsUiState
    data class Error(val message: String) : ProductsUiState
}