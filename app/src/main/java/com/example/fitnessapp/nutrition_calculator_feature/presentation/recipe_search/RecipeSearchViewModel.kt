package com.example.fitnessapp.nutrition_calculator_feature.presentation.recipe_search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnessapp.core.util.Resource
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

    private val _state = MutableStateFlow(RecipeSearchState())
    val state = _state.asStateFlow()

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
        }
    }
}