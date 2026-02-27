package com.example.minitienda.navigation

import kotlinx.serialization.Serializable

@Serializable
object Products

@Serializable
data class Details(
    val productId: Int
)

@Serializable
object Favorites