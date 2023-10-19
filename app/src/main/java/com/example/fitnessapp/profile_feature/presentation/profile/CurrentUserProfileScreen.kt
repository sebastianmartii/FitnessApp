package com.example.fitnessapp.profile_feature.presentation.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Height
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MonitorWeight
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material.icons.filled.SetMeal
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.fitnessapp.R
import com.example.fitnessapp.core.navigation_drawer.DrawerAction
import com.example.fitnessapp.core.navigation_drawer.DrawerContent
import com.example.fitnessapp.core.navigation_drawer.DrawerEvent
import com.example.fitnessapp.core.navigation_drawer.DrawerItem
import com.example.fitnessapp.profile_feature.data.mappers.toGenderString
import com.example.fitnessapp.profile_feature.domain.model.Gender
import com.example.fitnessapp.profile_feature.presentation.ProfileItem
import com.example.fitnessapp.profile_feature.presentation.sign_in.ActivityLevel
import com.example.fitnessapp.profile_feature.presentation.sign_in.toActivityLevelString
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest


@Composable
fun CurrentUserProfileScreen(
    state: ProfileState,
    activityLevelItems: List<ActivityLevel>,
    genderItems: List<Gender>,
    drawerState: DrawerState,
    onFocusMove: () -> Unit,
    onKeyboardHide: () -> Unit,
    drawerItemList: List<DrawerItem>,
    selectedDrawerItem: DrawerItem?,
    drawerEventFlow: Flow<DrawerAction>,
    onDrawerEvent: (DrawerEvent) -> Unit,
    onEvent: (ProfileEvent) -> Unit,
    onNavigateToNavigationDrawerDestination: (String) -> Unit,
) {
    LaunchedEffect(key1 = true) {
        drawerEventFlow.collectLatest {  action ->
            when(action) {
                DrawerAction.CloseNavigationDrawer -> {
                    drawerState.close()
                }
                DrawerAction.OpenNavigationDrawer -> {
                    drawerState.open()
                }
                is DrawerAction.NavigateToDrawerDestination -> {
                    onNavigateToNavigationDrawerDestination(action.route)
                }
            }
        }
    }
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                selectedDrawerItem = selectedDrawerItem,
                drawerItemList = drawerItemList,
                onDrawerItemSelect = { selectedDrawerItem ->
                    onDrawerEvent(DrawerEvent.OnDrawerItemSelect(selectedDrawerItem))
                    onDrawerEvent(DrawerEvent.CloseDrawer)
                }
            )
        },
        content = {
            CurrentUserProfileScreenContent(
                state = state,
                activityLevelItems = activityLevelItems,
                genderItems = genderItems,
                onDrawerStateChange = {
                    onDrawerEvent(DrawerEvent.OpenDrawer)
                },
                onAgeChange = { age ->
                    onEvent(ProfileEvent.OnAgeChange(age))
                },
                onWeightChange = { weight ->
                    onEvent(ProfileEvent.OnWeightChange(weight))
                },
                onHeightChange = { height ->
                    onEvent(ProfileEvent.OnHeightChange(height))
                },
                onCaloriesGoalChange = { caloriesGoal ->
                    onEvent(ProfileEvent.OnCaloriesGoalChange(caloriesGoal))
                },
                onGenderExpandedChange = {
                    onEvent(ProfileEvent.OnGenderExpandedChange(false))
                },
                onActivityLevelExpandedChange = {
                    onEvent(ProfileEvent.OnActivityLevelExpandedChange(false))
                },
                onActivityLevelChange = { activityLevel ->
                    onEvent(ProfileEvent.OnActivityLevelChange(activityLevel))
                },
                onGenderChange = { gender ->
                    onEvent(ProfileEvent.OnGenderChange(gender))
                },
                onFocusMove = onFocusMove,
                onKeyboardHide = onKeyboardHide
            )
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
private fun CurrentUserProfileScreenContent(
    state: ProfileState,
    activityLevelItems: List<ActivityLevel>,
    genderItems: List<Gender>,
    onDrawerStateChange: () -> Unit,
    onAgeChange: (String) -> Unit,
    onWeightChange: (String) -> Unit,
    onHeightChange: (String) -> Unit,
    onCaloriesGoalChange: (String) -> Unit,
    onGenderExpandedChange: () -> Unit,
    onActivityLevelExpandedChange: () -> Unit,
    onActivityLevelChange: (ActivityLevel) -> Unit,
    onGenderChange: (Gender) -> Unit,
    onFocusMove: () -> Unit,
    onKeyboardHide: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.profile_screen_title))
                },
                navigationIcon = {
                    IconButton(onClick = onDrawerStateChange) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = stringResource(id = R.string.navigation_drawer_icon)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            ProfileItem(
                name = state.userName,
                gender = state.gender
            )
            FlowRow(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .fillMaxWidth()
            ) {
                InputItem(
                    value = state.age,
                    onValueChange = onAgeChange,
                    onFocusMove = onFocusMove,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Numbers,
                            contentDescription = stringResource(id = R.string.age)
                        )
                    }
                )
                Box(
                    modifier = Modifier
                        .wrapContentSize(Alignment.TopStart)
                ) {
                    Row {
                        Text(text = state.gender.toGenderString())
                        Spacer(modifier = Modifier.width(16.dp))
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = stringResource(id = R.string.expand)
                        )
                    }
                    DropdownMenu(
                        expanded = state.genderExpanded,
                        onDismissRequest = onGenderExpandedChange
                    ) {
                        genderItems.onEach { item ->
                            DropdownMenuItem(
                                text = {
                                    Text(text = item.toGenderString())
                                },
                                onClick = {
                                    onGenderChange(item)
                                }
                            )
                        }
                    }
                }
                InputItem(
                    value = state.weight,
                    onValueChange = onWeightChange,
                    onFocusMove = onFocusMove,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.MonitorWeight,
                            contentDescription = stringResource(id = R.string.weight)
                        )
                    }
                )
                InputItem(
                    value = state.height,
                    onValueChange = onHeightChange,
                    onFocusMove = onFocusMove,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Height,
                            contentDescription = stringResource(id = R.string.height)
                        )
                    }
                )
                Box(
                    modifier = Modifier
                        .wrapContentSize(Alignment.TopStart)
                ) {
                    Row {
                        Text(text = state.activityLevel.toActivityLevelString())
                        Spacer(modifier = Modifier.width(16.dp))
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = stringResource(id = R.string.expand)
                        )
                    }
                    DropdownMenu(
                        expanded = state.activityLevelExpanded,
                        onDismissRequest = onActivityLevelExpandedChange
                    ) {
                        activityLevelItems.onEach { item ->
                            DropdownMenuItem(
                                text = {
                                    Text(text = item.toActivityLevelString())
                                },
                                onClick = {
                                    onActivityLevelChange(item)
                                }
                            )
                        }
                    }
                }
                InputItem(
                    value = state.caloriesGoal,
                    onValueChange = onCaloriesGoalChange,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            onKeyboardHide()
                        }
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.SetMeal,
                            contentDescription = stringResource(id = R.string.calories)
                        )
                    }
                )
            }
        }   
    }
}


@Composable
private fun InputItem(
    value: String,
    onValueChange: (String) -> Unit,
    onFocusMove: () -> Unit = {},
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        imeAction = ImeAction.Next
    ),
    keyboardActions: KeyboardActions = KeyboardActions(
        onNext = {
            onFocusMove()
        }
    ),
    leadingIcon: @Composable () -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            errorBorderColor = Color.Transparent,
            disabledBorderColor = Color.Transparent,
            disabledContainerColor = MaterialTheme.colorScheme.background,
            errorContainerColor = MaterialTheme.colorScheme.background,
            focusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedLeadingIconColor = MaterialTheme.colorScheme.onBackground,
            disabledLeadingIconColor = MaterialTheme.colorScheme.onBackground,
            errorLeadingIconColor = MaterialTheme.colorScheme.onBackground,
            focusedLeadingIconColor = MaterialTheme.colorScheme.onBackground
        ),
        leadingIcon = leadingIcon,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions
    )
}