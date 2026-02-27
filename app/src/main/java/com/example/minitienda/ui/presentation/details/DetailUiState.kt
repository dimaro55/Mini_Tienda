package com.example.minitienda.ui.presentation.details

import com.example.minitienda.domain.model.Product

sealed interface DetailUiState {
    object Loading : DetailUiState
    data class Success(val product: Product) : DetailUiState
    data class Error(val message: String) : DetailUiState
}