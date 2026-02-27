package com.example.minitienda.di

import android.content.Context
import androidx.room.Room
import com.example.minitienda.data.local.AppDatabase
import com.example.minitienda.data.local.dao.FavoriteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "mini_tienda_db"
        ).build()
    }

    @Provides
    fun provideFavoriteDao(
        db: AppDatabase
    ): FavoriteDao = db.favoriteDao()
}