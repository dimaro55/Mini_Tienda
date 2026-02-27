package com.example.minitienda.ui.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minitienda.domain.model.Product
import com.example.minitienda.domain.repository.ProductRepository
import com.example.minitienda.utils.ERROR_LOAD_PRODUCT
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState

    private val productId = MutableStateFlow<Int?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val isFavorite: StateFlow<Boolean> =
        productId
            .filterNotNull()
            .flatMapLatest { id ->
                repository.isFavorite(id)
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                false
            )

    fun loadProduct(id: Int) {
        productId.value = id

        viewModelScope.launch {
            try {
                val product = repository.getProductById(id)
                _uiState.value = DetailUiState.Success(product)
            } catch (e: Exception) {
                _uiState.value =
                    DetailUiState.Error( ERROR_LOAD_PRODUCT)
            }
        }
    }

    fun toggleFavorite(product: Product) {
        viewModelScope.launch {
            if (isFavorite.value) {
                repository.removeFavorite(product)
            } else {
                repository.addFavorite(product)
            }
        }
    }
}