package com.example.achiliveapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.achiliveapp.data.db.dao.IAwardSchemeDAO
import com.example.achiliveapp.data.db.dao.ICategoriesSchemeDAO
import com.example.achiliveapp.data.db.dao.IRatingDAO
import com.example.achiliveapp.data.db.utills.AwardTypeConverter
import com.example.achiliveapp.data.db.utills.ImageUriConverter
import com.example.achiliveapp.data.models.entities.AwardSchemeEntity
import com.example.achiliveapp.data.models.entities.CategorySchemeEntity
import com.example.achiliveapp.data.models.entities.RatingEntity

@Database(
    entities = [AwardSchemeEntity::class, CategorySchemeEntity::class, RatingEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(AwardTypeConverter::class, ImageUriConverter::class)
abstract class RoomConnection : RoomDatabase() {

    abstract fun awardsSchemeDao(): IAwardSchemeDAO
    abstract fun categoriesSchemeDao(): ICategoriesSchemeDAO
    abstract fun ratingDao(): IRatingDAO

}