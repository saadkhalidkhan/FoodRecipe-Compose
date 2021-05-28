package com.droidgeeks.foodrecipeapp.di

import com.droidgeeks.foodrecipeapp.network.RecipeService
import com.droidgeeks.foodrecipeapp.network.model.RecipeDtoMapper
import com.droidgeeks.foodrecipeapp.repository.RecipeRepository
import com.droidgeeks.foodrecipeapp.repository.RecipeRepository_Impl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRecipeRepository(
        recipeService: RecipeService,
        recipeMapper: RecipeDtoMapper,
    ): RecipeRepository {
        return RecipeRepository_Impl(
            recipeService = recipeService,
            mapper = recipeMapper
        )
    }
}

