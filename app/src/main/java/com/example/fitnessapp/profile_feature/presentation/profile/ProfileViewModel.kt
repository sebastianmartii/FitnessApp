package com.example.fitnessapp.profile_feature.presentation.profile

import com.example.fitnessapp.core.database.dao.CurrentUserDao
import com.example.fitnessapp.core.navigation_drawer.NavigationDrawerViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    currentUserDao: CurrentUserDao
) : NavigationDrawerViewModel(currentUserDao) {

    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()

    fun onEvent() {

    }
}