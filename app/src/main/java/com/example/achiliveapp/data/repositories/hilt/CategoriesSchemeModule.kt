package com.example.achiliveapp.data.repositories.hilt

import com.example.achiliveapp.data.api.RemoteSource
import com.example.achiliveapp.data.api.firebase.CategoryDataSource
import com.example.achiliveapp.data.db.source.CategoriesSchemeLocalSource
import com.example.achiliveapp.data.db.source.LocalSource
import com.example.achiliveapp.data.models.dto.CategoriesSchemeDTO
import com.example.achiliveapp.data.models.entities.CategorySchemeEntity
import com.example.achiliveapp.data.repositories.ModelsRepository
import com.example.achiliveapp.data.repositories.utils.mapper.Mapper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class CategoriesSchemeModule {

    @Binds
    abstract fun bindLocal(categoriesSchemeLocalSource: CategoriesSchemeLocalSource): LocalSource<CategorySchemeEntity, String>

    @Binds
    abstract fun bindRemote(categoryDataSource: CategoryDataSource): RemoteSource<CategoriesSchemeDTO, String>

    @Binds
    abstract fun bindRepository(categorySchemeRepository: ModelsRepository<CategorySchemeEntity, CategoriesSchemeDTO, String>): ModelsRepository<CategorySchemeEntity, CategoriesSchemeDTO, String>

    @Binds
    abstract fun bindMapper(categoryMapper: Mapper<CategorySchemeEntity, CategoriesSchemeDTO, String>) : Mapper<CategorySchemeEntity, CategoriesSchemeDTO, String>
}