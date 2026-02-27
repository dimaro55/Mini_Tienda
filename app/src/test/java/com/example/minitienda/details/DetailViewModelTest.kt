package com.example.minitienda.details

import app.cash.turbine.test
import com.example.minitienda.MainDispatcherRule
import com.example.minitienda.domain.model.Product
import com.example.minitienda.domain.repository.ProductRepository
import com.example.minitienda.ui.presentation.details.DetailUiState
import com.example.minitienda.ui.presentation.details.DetailViewModel
import com.example.minitienda.utils.ERROR_LOAD_PRODUCT
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository: ProductRepository = mockk(relaxed = true)


    @Test
    fun `loadProduct should emit Success when repository returns product`() = runTest {

        val product =  Product(
            1, "Product 1",
            description = "description 1",
            price = 0.0,
            rating = 0.0,
            thumbnail = "thumbnail",
            images = listOf()
        )

        coEvery { repository.getProductById(1) } returns product
        every { repository.isFavorite(1) } returns flowOf(false)

        val viewModel = DetailViewModel(repository)

        viewModel.loadProduct(1)

        advanceUntilIdle()

        val state = viewModel.uiState.value
        assert(state is DetailUiState.Success)
        assert((state as DetailUiState.Success).product == product)
    }

    @Test
    fun `loadProduct should emit Error when repository throws`() = runTest {

        coEvery { repository.getProductById(1) } throws RuntimeException(ERROR_LOAD_PRODUCT)
        every { repository.isFavorite(1) } returns flowOf(false)

        val viewModel = DetailViewModel(repository)

        viewModel.loadProduct(1)

        advanceUntilIdle()

        val state = viewModel.uiState.value
        assert(state is DetailUiState.Error)
    }

    @Test
    fun `isFavorite should emit value from repository`() = runTest {

        val favoriteFlow = MutableStateFlow(false)

        val product = Product(
            1, "Product 1",
            "description 1",
            0.0,
            0.0,
            "thumbnail",
            emptyList()
        )

        coEvery { repository.getProductById(1) } returns product
        every { repository.isFavorite(1) } returns favoriteFlow

        val viewModel = DetailViewModel(repository)

        viewModel.loadProduct(1)

        viewModel.isFavorite.test {

            assert(!awaitItem())

            favoriteFlow.value = true

            assert(awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `toggleFavorite should remove when already favorite`() = runTest {

        val product = Product(
            1, "Product 1",
            "description 1",
            0.0,
            0.0,
            "thumbnail",
            emptyList()
        )

        val favoriteFlow = MutableStateFlow(true)

        coEvery { repository.getProductById(1) } returns product
        every { repository.isFavorite(1) } returns favoriteFlow

        val viewModel = DetailViewModel(repository)

        viewModel.loadProduct(1)


        val job = launch {
            viewModel.isFavorite.collect()
        }

        advanceUntilIdle()

        assert(viewModel.isFavorite.value)

        viewModel.toggleFavorite(product)

        advanceUntilIdle()

        coVerify(exactly = 1) { repository.removeFavorite(product) }

        job.cancel()
    }

    @Test
    fun `toggleFavorite should add when not favorite`() = runTest {

        val product =  Product(
            1, "Product 1",
            description = "description 1",
            price = 0.0,
            rating = 0.0,
            thumbnail = "thumbnail",
            images = listOf()
        )

        val favoriteFlow = MutableStateFlow(false)

        coEvery { repository.getProductById(1) } returns product
        every { repository.isFavorite(1) } returns favoriteFlow

        val viewModel = DetailViewModel(repository)

        viewModel.loadProduct(1)

        advanceUntilIdle()

        viewModel.toggleFavorite(product)

        advanceUntilIdle()

        coVerify { repository.addFavorite(product) }
    }

}

