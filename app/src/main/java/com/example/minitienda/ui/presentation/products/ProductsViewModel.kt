package com.example.minitienda.ui.presentation.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minitienda.domain.repository.ProductRepository
import com.example.minitienda.utils.ERROR_LOAD_PRODUCTS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<ProductsUiState>(ProductsUiState.Loading)
    val uiState: StateFlow<ProductsUiState> = _uiState

    init {
        getProducts()
    }

    private fun getProducts() {
        viewModelScope.launch {
            try {
                val products = repository.getProducts()
                _uiState.value = ProductsUiState.Success(products)
            } catch (e: Exception) {
                _uiState.value =
                    ProductsUiState.Error( ERROR_LOAD_PRODUCTS)
            }
        }
    }
}