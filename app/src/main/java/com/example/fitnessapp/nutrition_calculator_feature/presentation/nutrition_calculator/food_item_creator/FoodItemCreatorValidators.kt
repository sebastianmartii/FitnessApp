package com.example.fitnessapp.nutrition_calculator_feature.presentation.nutrition_calculator.food_item_creator

import androidx.core.text.isDigitsOnly

object FoodItemCreatorValidators {

    fun isFoodItemValid(foodItem: FoodItemCreatorState): Boolean {
        return isFoodNameValid(foodItem.name) && isServingSizeValid(foodItem.servingSize) && areCaloriesValid(foodItem.calories)
                && areCarbsValid(foodItem.carbs) && isProteinValid(foodItem.protein) && isTotalFatValid(foodItem.totalFat)
                && isSaturatedFatValid(foodItem.saturatedFat) && isFiberValid(foodItem.fiber) && isSugarValid(foodItem.sugar)
    }

    fun isFoodNameValid(name: String): Boolean {
        return !name.isDigitsOnly() && name.length > 2 && name.length < 23
    }

    fun isServingSizeValid(servingSize: String): Boolean {
        if (servingSize.isBlank() || !servingSize.isDigitsOnly()) {
            return false
        }
        return servingSize.toDouble() > 0 && servingSize.toDouble() < 2000
    }

    fun areCaloriesValid(calories: String): Boolean {
        if (calories.isBlank() || !calories.isDigitsOnly()) {
            return false
        }
        return calories.toDouble() > 0 && calories.toDouble() < 10000
    }

    fun areCarbsValid(carbs: String): Boolean {
        if (carbs.isBlank() || !carbs.isDigitsOnly()) {
            return false
        }
        return carbs.toDouble() >= 0 && carbs.toDouble() < 800
    }

    fun isProteinValid(protein: String): Boolean {
        if (protein.isBlank() || !protein.isDigitsOnly()) {
            return false
        }
        return protein.toDouble() >= 0 && protein.toDouble() < 350
    }

    fun isTotalFatValid(totalFat: String): Boolean {
        if (totalFat.isBlank() || !totalFat.isDigitsOnly()) {
            return false
        }
        return totalFat.toDouble() >= 0 && totalFat.toDouble() < 160
    }

    fun isSaturatedFatValid(saturatedFat: String): Boolean {
        if (saturatedFat.isBlank() || !saturatedFat.isDigitsOnly()) {
            return false
        }
        return saturatedFat.toDouble() >= 0 && saturatedFat.toDouble() < 40
    }

    fun isFiberValid(fiber: String): Boolean {
        if (fiber.isBlank() || !fiber.isDigitsOnly()) {
            return false
        }
        return fiber.toDouble() >= 0 && fiber.toDouble() < 70
    }

    fun isSugarValid(sugar: String): Boolean {
        if (sugar.isBlank() || !sugar.isDigitsOnly()) {
            return false
        }
        return sugar.toDouble() >= 0 && sugar.toDouble() < 40
    }
}