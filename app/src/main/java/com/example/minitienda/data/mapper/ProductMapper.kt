package com.example.minitienda.data.mapper

import com.example.minitienda.data.local.entity.FavoriteEntity
import com.example.minitienda.data.remote.dto.ProductDto
import com.example.minitienda.domain.model.Product

fun ProductDto.toDomain(): Product {
    return Product(
        id = id,
        title = title,
        description = description,
        price = price,
        rating = rating,
        thumbnail = thumbnail,
        images = images
    )
}

fun Product.toEntity(): FavoriteEntity {
    return FavoriteEntity(
        id = id,
        title = title,
        price = price,
        thumbnail = thumbnail
    )
}

fun FavoriteEntity.toDomain(): Product {
    return Product(
        id = id,
        title = title,
        description = "",
        price = price,
        rating = 0.0,
        thumbnail = thumbnail,
        images = emptyList()
    )
}