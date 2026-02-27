package com.example.minitienda.domain.repository

import com.example.minitienda.domain.model.Product
import kotlinx.coroutines.flow.Flow


    interface ProductRepository {

        suspend fun getProducts(): List<Product>

        suspend fun getProductById(id: Int): Product

        fun getFavorites(): Flow<List<Product>>

        suspend fun addFavorite(product: Product)

        suspend fun removeFavorite(product: Product)

        fun isFavorite(id: Int): Flow<Boolean>
    }
