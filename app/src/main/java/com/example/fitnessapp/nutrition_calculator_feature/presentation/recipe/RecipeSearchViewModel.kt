package com.example.fitnessapp.nutrition_calculator_feature.presentation.recipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnessapp.core.util.Resource
import com.example.fitnessapp.nutrition_calculator_feature.domain.model.Recipe
import com.example.fitnessapp.nutrition_calculator_feature.domain.repository.RecipesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeSearchViewModel @Inject constructor(
    private val repo: RecipesRepository
) : ViewModel() {

    private val _isRecipeSaved = MutableStateFlow(false)
    val isRecipeSaved = _isRecipeSaved.asStateFlow()

    private val _state = MutableStateFlow(RecipeSearchState())
    val state = _state.asStateFlow()

    private val _inspectedRecipe = MutableStateFlow<Recipe?>(null)
    val inspectedRecipe = _inspectedRecipe.asStateFlow()

    fun onEvent(event: RecipeSearchEvent) {
        when(event) {
            is RecipeSearchEvent.OnQueryChange -> {
                _state.update {
                    it.copy(
                        query = event.query
                    )
                }
            }
            RecipeSearchEvent.OnRecipeSearch -> {
                viewModelScope.launch {
                    repo.getRecipes(_state.value.query).collectLatest { result ->
                        when(result) {
                            is Resource.Error -> {
                                println(result.message)
                                _state.update {
                                    it.copy(
                                        recipes = result.data ?: emptyList(),
                                        isLoading = false
                                    )
                                }
                            }
                            is Resource.Loading -> {
                                _state.update {
                                    it.copy(
                                        recipes = result.data ?: emptyList(),
                                        isLoading = true
                                    )
                                }
                            }
                            is Resource.Success -> {
                                _state.update {
                                    it.copy(
                                        recipes = result.data ?: emptyList(),
                                        isLoading = false
                                    )
                                }
                            }
                        }
                    }
                }
            }
            is RecipeSearchEvent.OnIsSearchBarActiveChange -> {
                _state.update {
                    it.copy(
                        isSearchBarActive = event.isActive,
                    )
                }
            }
            RecipeSearchEvent.OnTextFieldClear -> {
                _state.update {
                    it.copy(
                        query = "",
                    )
                }
            }
            is RecipeSearchEvent.OnNavigateToRecipeDetails -> {
                _inspectedRecipe.value = _state.value.recipes[event.recipeIndex]
                viewModelScope.launch {
                    repo.getIsRecipeSaved(_inspectedRecipe.value!!).collectLatest {  isSaved ->
                        _isRecipeSaved.value = isSaved
                    }
                }
            }
            is RecipeSearchEvent.OnIsRecipeSavedChange -> {
                viewModelScope.launch {
                    if (event.isSaved) {
                        repo.deleteRecipe(event.recipe)
                    } else {
                        repo.saveRecipe(event.recipe)
                    }
                }
            }
        }
    }
}