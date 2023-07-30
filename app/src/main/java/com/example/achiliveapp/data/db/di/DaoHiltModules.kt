package com.example.achiliveapp.data.db.di

import android.content.Context
import androidx.room.Room
import com.example.achiliveapp.data.db.RoomConnection
import com.example.achiliveapp.data.db.dao.IAwardSchemeDAO
import com.example.achiliveapp.data.db.dao.ICategoriesSchemeDAO
import com.example.achiliveapp.data.db.dao.IRatingDAO
import com.example.achiliveapp.data.db.utills.AwardTypeConverter
import com.example.achiliveapp.data.db.utills.ImageUriConverter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoHiltModules {

    @Provides
    @Singleton
    fun provideRoomDataBase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, RoomConnection::class.java, "local_db")
            .addTypeConverter(AwardTypeConverter())
            .addTypeConverter(ImageUriConverter())
            .build()


    @Provides
    @Singleton
    fun provideAwardsSchemeDao(roomConnection: RoomConnection) : IAwardSchemeDAO = roomConnection.awardsSchemeDao()

    @Provides
    @Singleton
    fun provideCategorySchemeDao(roomConnection: RoomConnection) : ICategoriesSchemeDAO =
        roomConnection.categoriesSchemeDao()

    @Provides
    @Singleton
    fun provideRatingDao(roomConnection: RoomConnection) : IRatingDAO = roomConnection.ratingDao()

}