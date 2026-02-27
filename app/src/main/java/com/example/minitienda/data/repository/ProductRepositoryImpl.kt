package com.example.minitienda.data.repository

import com.example.minitienda.data.local.dao.FavoriteDao
import com.example.minitienda.data.mapper.toDomain
import com.example.minitienda.data.mapper.toEntity
import com.example.minitienda.data.remote.api.ProductApi
import com.example.minitienda.domain.model.Product
import com.example.minitienda.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class ProductRepositoryImpl @Inject constructor(
    private val api: ProductApi,
    private val dao: FavoriteDao
) : ProductRepository {

    override suspend fun getProducts(): List<Product> {
        return api.getProducts().products.map { it.toDomain() }
    }

    override suspend fun getProductById(id: Int): Product {
        return api.getProductById(id).toDomain()
    }

    override fun getFavorites(): Flow<List<Product>> {
        return dao.getFavorites().map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun addFavorite(product: Product) {
        dao.insertFavorite(product.toEntity())
    }

    override suspend fun removeFavorite(product: Product) {
        dao.deleteFavoriteById(product.toEntity().id)
    }

    override fun isFavorite(id: Int): Flow<Boolean> {
        return dao.isFavorite(id)
    }
}