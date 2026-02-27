package com.example.minitienda.favorites

import app.cash.turbine.test
import com.example.minitienda.MainDispatcherRule
import com.example.minitienda.domain.model.Product
import com.example.minitienda.domain.repository.ProductRepository
import com.example.minitienda.ui.presentation.favorites.FavoritesViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FavoritesViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository: ProductRepository = mockk()

    @Test
    fun `favorites should emit updated values`() = runTest {

        val flow = MutableStateFlow<List<Product>>(emptyList())
        every { repository.getFavorites() } returns flow

        val viewModel = FavoritesViewModel(repository)

        viewModel.favorites.test {

            assert(awaitItem().isEmpty())

            val fakeFavorites = listOf(
                Product(
                    1, "Product 1",
                    description = "description 1",
                    price = 0.0,
                    rating = 0.0,
                    thumbnail = "thumbnail",
                    images = listOf()
                ),
                Product(
                    2, "Product 2",
                    description = "description 2",
                    price = 0.0,
                    rating = 0.0,
                    thumbnail = "thumbnail",
                    images = listOf()
                )
            )


            flow.value = fakeFavorites

            assert(awaitItem() == fakeFavorites)

            cancelAndIgnoreRemainingEvents()
        }
    }
}