package com.example.fitnessapp.profile_feature.presentation.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.fitnessapp.R
import com.example.fitnessapp.core.navigation_drawer.DrawerAction
import com.example.fitnessapp.core.navigation_drawer.DrawerContent
import com.example.fitnessapp.core.navigation_drawer.DrawerEvent
import com.example.fitnessapp.core.navigation_drawer.DrawerItem
import com.example.fitnessapp.profile_feature.data.mappers.toActivityLevelString
import com.example.fitnessapp.profile_feature.data.mappers.toDropDownMenuString
import com.example.fitnessapp.profile_feature.data.mappers.toGenderString
import com.example.fitnessapp.profile_feature.domain.model.Gender
import com.example.fitnessapp.profile_feature.presentation.ProfileItem
import com.example.fitnessapp.profile_feature.presentation.sign_in.ActivityLevel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest


@Composable
fun CurrentUserProfileScreen(
    state: ProfileState,
    drawerState: DrawerState,
    onFocusClear: () -> Unit,
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
                onGenderExpandedChange = { expanded ->
                    onEvent(ProfileEvent.OnGenderExpandedChange(expanded))
                },
                onActivityLevelExpandedChange = { expanded ->
                    onEvent(ProfileEvent.OnActivityLevelExpandedChange(expanded))
                },
                onActivityLevelChange = { activityLevel ->
                    onEvent(ProfileEvent.OnActivityLevelChange(activityLevel))
                },
                onGenderChange = { gender ->
                    onEvent(ProfileEvent.OnGenderChange(gender))
                },
                onFocusClear = onFocusClear,
                onKeyboardHide = onKeyboardHide
            )
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CurrentUserProfileScreenContent(
    state: ProfileState,
    onDrawerStateChange: () -> Unit,
    onAgeChange: (String) -> Unit,
    onWeightChange: (String) -> Unit,
    onHeightChange: (String) -> Unit,
    onCaloriesGoalChange: (String) -> Unit,
    onGenderExpandedChange: (expanded: Boolean) -> Unit,
    onActivityLevelExpandedChange: (expanded: Boolean) -> Unit,
    onActivityLevelChange: (ActivityLevel) -> Unit,
    onGenderChange: (Gender) -> Unit,
    onFocusClear: () -> Unit,
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
        ) {
            ProfileItem(
                name = state.userName,
                gender = state.gender
            )
            LazyVerticalGrid(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(32.dp),
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 32.dp, vertical = 16.dp)
            ) {
                item {
                    UserInfoItem(
                        valueText = state.age,
                        labelText = stringResource(id = R.string.age),
                        onKeyboardHide = onKeyboardHide,
                        onFocusClear = onFocusClear,
                        suffix = stringResource(id = R.string.suffix_age),
                        onValueChange = { age ->
                            onAgeChange(age)
                        }
                    )
                }
                item {
                    UserInfoItem(
                        valueText = state.gender.toGenderString(),
                        labelText = stringResource(id = R.string.gender),
                        dropDownMenuItems = listOf(
                            Gender.FEMALE.name,
                            Gender.MALE.name
                        ),
                        isDropDownMenu = true,
                        isDropDownMenuExpanded = state.genderExpanded,
                        onMenuExpand = {
                            onGenderExpandedChange(true)
                        },
                        onMenuDismiss = {
                            onGenderExpandedChange(false)
                        },
                        onValueChange = { gender ->
                            onGenderChange(Gender.valueOf(gender))
                        }
                    )
                }
                item {
                    UserInfoItem(
                        valueText = state.weight,
                        labelText = stringResource(id = R.string.weight),
                        onKeyboardHide = onKeyboardHide,
                        onFocusClear = onFocusClear,
                        suffix = stringResource(id = R.string.suffix_weight),
                        onValueChange = { weight ->
                            onWeightChange(weight)
                        }
                    )
                }
                item {
                    UserInfoItem(
                        valueText = state.height,
                        labelText = stringResource(id =R.string.height),
                        onKeyboardHide = onKeyboardHide,
                        onFocusClear = onFocusClear,
                        suffix = stringResource(id = R.string.suffix_height),
                        onValueChange = { height ->
                            onHeightChange(height)
                        }
                    )
                }
                item {
                    UserInfoItem(
                        valueText = state.caloriesGoal,
                        labelText = stringResource(id =R.string.calories),
                        onKeyboardHide = onKeyboardHide,
                        onFocusClear = onFocusClear,
                        suffix = stringResource(id = R.string.suffix_calories),
                        onValueChange = { calories ->
                            onCaloriesGoalChange(calories)
                        }
                    )
                }
                item {
                    UserInfoItem(
                        valueText = state.activityLevel.toActivityLevelString(),
                        labelText = stringResource(id = R.string.activity_level),
                        dropDownMenuItems = listOf(
                            ActivityLevel.LEVEL_1.name,
                            ActivityLevel.LEVEL_2.name,
                            ActivityLevel.LEVEL_3.name,
                            ActivityLevel.LEVEL_4.name,
                            ActivityLevel.LEVEL_5.name,
                            ActivityLevel.LEVEL_6.name,
                        ),
                        isDropDownMenu = true,
                        isDropDownMenuExpanded = state.activityLevelExpanded,
                        onMenuExpand = {
                            onActivityLevelExpandedChange(true)
                        },
                        onMenuDismiss = {
                            onActivityLevelExpandedChange(false)
                        },
                        onValueChange = { activityLevel ->
                            onActivityLevelChange(ActivityLevel.valueOf(activityLevel))
                        }
                    )
                }
            }
        }   
    }
}

@Composable
private fun UserInfoItem(
    valueText: String,
    labelText: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    dropDownMenuItems: List<String> = emptyList(),
    suffix: String = "",
    isDropDownMenu: Boolean = false,
    isDropDownMenuExpanded: Boolean = false,
    onKeyboardHide: () -> Unit = {},
    onFocusClear: () -> Unit = {},
    onMenuExpand: () -> Unit = {},
    onMenuDismiss: () -> Unit = {},
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        shadowElevation = 1.dp,
        tonalElevation = 2.dp,
        modifier = modifier
            .wrapContentSize()
    ) {
        Column {
            Text(
                text = labelText,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontStyle = FontStyle.Italic
                ),
                modifier = Modifier
                    .padding(start = 4.dp, top = 4.dp)
            )
            OutlinedTextField(
                value = valueText,
                onValueChange = onValueChange,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    errorBorderColor = Color.Transparent,
                    disabledBorderColor = Color.Transparent,
                    disabledContainerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp),
                    errorContainerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp),
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp),
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp)
                ),
                readOnly = isDropDownMenu,
                trailingIcon = {
                    if (isDropDownMenu) {
                        IconButton(onClick = onMenuExpand) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = stringResource(
                                    id = R.string.expand
                                )
                            )
                        }
                    } else {
                        Text(
                            text = suffix,
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Decimal
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        onKeyboardHide()
                        onFocusClear()
                    }
                )
            )
            if (isDropDownMenu) {
                DropdownMenu(
                    expanded = isDropDownMenuExpanded,
                    onDismissRequest = onMenuDismiss
                ) {
                    dropDownMenuItems.onEach { item ->
                        DropdownMenuItem(
                            text = {
                                Text(text = item.toDropDownMenuString())
                            },
                            onClick = {
                                onValueChange(item)
                            }
                        )
                    }
                }
            }
        }
    }
}