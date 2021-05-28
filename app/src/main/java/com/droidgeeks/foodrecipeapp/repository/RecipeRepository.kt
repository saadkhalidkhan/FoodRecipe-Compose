package com.droidgeeks.foodrecipeapp.repository

import com.droidgeeks.foodrecipeapp.domain.model.Recipe


interface RecipeRepository {

    suspend fun search(token: String, page: Int, query: String): List<Recipe>

    suspend fun get(token: String, id: Int): Recipe

}
