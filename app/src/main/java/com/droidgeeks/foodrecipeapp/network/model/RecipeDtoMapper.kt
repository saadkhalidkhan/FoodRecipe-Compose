package com.droidgeeks.foodrecipeapp.network.model

import com.droidgeeks.foodrecipeapp.domain.model.Recipe
import com.droidgeeks.foodrecipeapp.domain.util.DomainMapper

class RecipeDtoMapper : DomainMapper<RecipeDto, Recipe> {

    override fun mapToDomainModel(model: RecipeDto): Recipe {
        return Recipe(
                id = model.pk,
                title = model.title,
                featuredImage = model.featuredImage,
                rating = model.rating,
                publisher = model.publisher,
                sourceUrl = model.sourceUrl,
                description = model.description,
                cookingInstructions = model.cookingInstructions,
                ingredients = model.ingredients.orEmpty(),
                dateAdded = model.dateAdded,
                dateUpdated = model.dateUpdated,
        )
    }

    override fun mapFromDomainModel(domainModel: Recipe): RecipeDto {
        return RecipeDto(
                pk = domainModel.id,
                title = domainModel.title,
                featuredImage = domainModel.featuredImage,
                rating = domainModel.rating,
                publisher = domainModel.publisher,
                sourceUrl = domainModel.sourceUrl,
                description = domainModel.description,
                cookingInstructions = domainModel.cookingInstructions,
                ingredients = domainModel.ingredients,
                dateAdded = domainModel.dateAdded,
                dateUpdated = domainModel.dateUpdated,
        )
    }

    fun toDomainList(initial: List<RecipeDto>): List<Recipe>{
        return initial.map { mapToDomainModel(it) }
    }

    fun fromDomainList(initial: List<Recipe>): List<RecipeDto>{
        return initial.map { mapFromDomainModel(it) }
    }


}
