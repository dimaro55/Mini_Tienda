package com.example.minitienda.products

import com.example.minitienda.MainDispatcherRule
import com.example.minitienda.domain.model.Product
import com.example.minitienda.domain.repository.ProductRepository
import com.example.minitienda.presentation.products.ProductsUiState
import com.example.minitienda.presentation.products.ProductsViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProductsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository: ProductRepository = mockk()

    @Test
    fun `when repository returns data, uiState should be Success`() = runTest {
        val fakeProducts = listOf(Product(
            1, "Test 1",
            description = "description",
            price = 0.0,
            rating = 0.0,
            thumbnail = "thumbnail",
            images = listOf()
        ))

        coEvery { repository.getProducts() } returns fakeProducts

        val viewModel = ProductsViewModel(repository)

        advanceUntilIdle()

        assert(viewModel.uiState.value is ProductsUiState.Success)

        val state = viewModel.uiState.value as ProductsUiState.Success
        assert(state.products == fakeProducts)
    }


    @Test
    fun `when repository throws exception, uiState should be Error`() = runTest {
        coEvery { repository.getProducts() } throws RuntimeException("Network error")

        val viewModel = ProductsViewModel(repository)

        advanceUntilIdle()

        assert(viewModel.uiState.value is ProductsUiState.Error)

        val state = viewModel.uiState.value as ProductsUiState.Error
        assert(state.message == "Network error")
    }
}