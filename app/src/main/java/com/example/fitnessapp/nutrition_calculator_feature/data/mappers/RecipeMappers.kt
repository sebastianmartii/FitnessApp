package com.example.fitnessapp.nutrition_calculator_feature.data.mappers

import com.example.fitnessapp.nutrition_calculator_feature.data.local.entity.RecipesEntity
import com.example.fitnessapp.nutrition_calculator_feature.data.remote.recipes.dto.Hit
import com.example.fitnessapp.nutrition_calculator_feature.domain.model.Recipe

fun Hit.toRecipe(): Recipe {
    return Recipe(
        label = this.recipe.label,
        smallImage = this.recipe.image,
        bigImage = this.recipe.image,
        dietLabels = this.recipe.dietLabels,
        ingredients = this.recipe.ingredientLines,
        calories = this.recipe.calories,
        servingSize = this.recipe.totalWeight,
        carbs = this.recipe.totalNutrients.chocdf.quantity,
        protein = this.recipe.totalNutrients.procnt.quantity,
        fat = this.recipe.totalNutrients.fat.quantity,
        saturatedFat = this.recipe.totalNutrients.fasat.quantity,
        fiber = this.recipe.totalNutrients.fibtg.quantity,
        sugar = this.recipe.totalNutrients.sugar.quantity,
        externalUrl = this.recipe.url
    )
}

fun Recipe.toRecipeEntity(): RecipesEntity {
    return RecipesEntity(
        label,
        smallImage,
        bigImage,
        dietLabels,
        ingredients,
        calories,
        servingSize,
        carbs,
        fat,
        protein,
        saturatedFat,
        fiber,
        sugar,
        externalUrl
    )
}

fun RecipesEntity.toRecipe(): Recipe {
    return Recipe(
        label,
        smallImage,
        bigImage,
        dietLabels,
        ingredients,
        calories,
        servingSize,
        carbs,
        protein,
        fat,
        saturatedFat,
        fiber,
        sugar,
        externalUrl
    )
}