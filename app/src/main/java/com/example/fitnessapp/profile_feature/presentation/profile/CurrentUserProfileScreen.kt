package com.example.fitnessapp.profile_feature.presentation.profile

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DoneAll
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
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
import com.example.fitnessapp.profile_feature.presentation.sign_in.Validators
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest


@Composable
fun CurrentUserProfileScreen(
    state: ProfileState,
    snackbarHostState: SnackbarHostState,
    eventFlow: Flow<ProfileViewModel.UiEvent>,
    drawerState: DrawerState,
    onFocusClear: () -> Unit,
    onKeyboardHide: () -> Unit,
    drawerItemList: List<DrawerItem>,
    selectedDrawerItem: DrawerItem?,
    drawerEventFlow: Flow<DrawerAction>,
    onDrawerEvent: (DrawerEvent) -> Unit,
    onEvent: (ProfileEvent) -> Unit,
    onNavigateToNavigationDrawerDestination: (String) -> Unit,
    onNavigateToCaloriesGoalListScreen: () -> Unit,
    onNavigateBack: () -> Unit
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
    LaunchedEffect(key1 = true) {
        eventFlow.collectLatest { event ->
            when(event) {
                is ProfileViewModel.UiEvent.Navigate -> {
                    if (event.route.isEmpty()) {
                        onNavigateBack()
                    } else {
                        onNavigateToNavigationDrawerDestination(event.route)
                    }
                }
                is ProfileViewModel.UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(event.message)
                }
            }
        }
    }
    if (state.isUserUpdateDialogVisible) {
        UserUpdateDialog(
            onUserUpdateDialogDismiss = {
                onEvent(ProfileEvent.OnUserUpdateDialogDismiss)
            },
            onUserUpdateDialogDecline = {
                onEvent(ProfileEvent.OnUserUpdateDialogDecline)
            },
            onUserUpdateDialogConfirm = {
                onEvent(ProfileEvent.OnUserUpdate(
                    state,
                    Validators.isAgeValid(state.age),
                    Validators.isHeightValid(state.height),
                    Validators.isWeightValid(state.weight),
                    Validators.areCaloriesValid(state.caloriesGoal)
                ))
            }
        )
    }
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                selectedDrawerItem = selectedDrawerItem,
                drawerItemList = drawerItemList,
                onDrawerItemSelect = { selectedDrawerItem ->
                    if (state.isSaveUserActionVisible && selectedDrawerItem.label != "Sign Out") {
                        onDrawerEvent(DrawerEvent.CloseDrawer)
                        onEvent(ProfileEvent.OnUserUpdateDialogShow(selectedDrawerItem.route ?: ""))
                    } else {
                        onDrawerEvent(DrawerEvent.OnDrawerItemSelect(selectedDrawerItem))
                        onDrawerEvent(DrawerEvent.CloseDrawer)
                    }
                }
            )
        },
        content = {
            CurrentUserProfileScreenContent(
                state = state,
                snackbarHostState = snackbarHostState,
                onDrawerStateChange = {
                    onDrawerEvent(DrawerEvent.OpenDrawer)
                },
                onNameChange = { name ->
                    onEvent(ProfileEvent.OnUserNameChange(name))
                },
                onAgeChange = { age, isValid ->
                    onEvent(ProfileEvent.OnAgeChange(age, isValid))
                },
                onWeightChange = { weight, isValid ->
                    onEvent(ProfileEvent.OnWeightChange(weight, isValid))
                },
                onHeightChange = { height, isValid ->
                    onEvent(ProfileEvent.OnHeightChange(height, isValid))
                },
                onCaloriesGoalChange = { caloriesGoal, isValid ->
                    onEvent(ProfileEvent.OnCaloriesGoalChange(caloriesGoal, isValid))
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
                onKeyboardHide = onKeyboardHide,
                onUserUpdate = { state ->
                    onEvent(ProfileEvent.OnUserUpdate(
                        state,
                        Validators.isAgeValid(state.age),
                        Validators.isHeightValid(state.height),
                        Validators.isWeightValid(state.weight),
                        Validators.areCaloriesValid(state.caloriesGoal)
                    ))
                },
                onNavigateToCaloriesGoalListScreen = onNavigateToCaloriesGoalListScreen
            )
        }
    )
    BackHandler {
        if (state.isSaveUserActionVisible) {
            onEvent(ProfileEvent.OnUserUpdateDialogShow(""))
        } else {
            onNavigateBack()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CurrentUserProfileScreenContent(
    state: ProfileState,
    snackbarHostState: SnackbarHostState,
    onDrawerStateChange: () -> Unit,
    onAgeChange: (age: String, isValid: Boolean) -> Unit,
    onNameChange: (String) -> Unit,
    onWeightChange: (weight: String, isValid: Boolean) -> Unit,
    onHeightChange: (height: String, isValid: Boolean) -> Unit,
    onCaloriesGoalChange: (caloriesGoal: String, isValid: Boolean) -> Unit,
    onGenderExpandedChange: (expanded: Boolean) -> Unit,
    onActivityLevelExpandedChange: (expanded: Boolean) -> Unit,
    onActivityLevelChange: (ActivityLevel) -> Unit,
    onGenderChange: (Gender) -> Unit,
    onFocusClear: () -> Unit,
    onKeyboardHide: () -> Unit,
    onUserUpdate: (ProfileState) -> Unit,
    onNavigateToCaloriesGoalListScreen: () -> Unit
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
                },
                actions = {
                    if (state.isSaveUserActionVisible) {
                        IconButton(onClick = {
                            onUserUpdate(state)
                        }) {
                            Icon(
                                imageVector = Icons.Default.DoneAll,
                                contentDescription = stringResource(id = R.string.save_text)
                            )
                        }
                    }
                }
            )
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState)
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
                gender = state.gender,
                isNameVisible = false,
                hasBorder = false
            )
            UserInfoItem(
                valueText = state.userName,
                labelText = stringResource(id = R.string.username),
                onKeyboardHide = onKeyboardHide,
                onFocusClear = onFocusClear,
                isNumeric = false,
                onValueChange = { name ->
                    onNameChange(name)
                }
            )
            UserInfoItem(
                valueText = state.age,
                labelText = stringResource(id = R.string.age),
                onKeyboardHide = onKeyboardHide,
                onFocusClear = onFocusClear,
                isError = if (state.shouldUseValidators) !state.isAgeValid else false,
                suffix = stringResource(id = R.string.suffix_age),
                onValueChange = { age ->
                    onAgeChange(age, Validators.isAgeValid(age))
                }
            )
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
            UserInfoItem(
                valueText = state.weight,
                labelText = stringResource(id = R.string.weight),
                onKeyboardHide = onKeyboardHide,
                onFocusClear = onFocusClear,
                isError = if (state.shouldUseValidators) !state.isWeightValid else false,
                suffix = stringResource(id = R.string.suffix_weight),
                onValueChange = { weight ->
                    onWeightChange(weight, Validators.isWeightValid(weight))
                }
            )
            UserInfoItem(
                valueText = state.height,
                labelText = stringResource(id =R.string.height),
                onKeyboardHide = onKeyboardHide,
                onFocusClear = onFocusClear,
                isError = if (state.shouldUseValidators) !state.isHeightValid else false,
                suffix = stringResource(id = R.string.suffix_height),
                onValueChange = { height ->
                    onHeightChange(height, Validators.isHeightValid(height))
                }
            )
            UserInfoItem(
                valueText = state.caloriesGoal,
                labelText = stringResource(id =R.string.calories),
                onKeyboardHide = onKeyboardHide,
                onFocusClear = onFocusClear,
                isCaloriesGoal = true,
                isError = if (state.shouldUseValidators) !state.isCaloriesGoalValid else false,
                suffix = stringResource(id = R.string.suffix_calories),
                onValueChange = { calories ->
                    onCaloriesGoalChange(calories, Validators.areCaloriesValid(calories))
                },
                onNavigateToCaloriesGoalListScreen = onNavigateToCaloriesGoalListScreen
            )
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
            Spacer(modifier = Modifier.height(404.dp))
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
    isError: Boolean = false,
    isCaloriesGoal: Boolean = false,
    isNumeric: Boolean = true,
    isDropDownMenuExpanded: Boolean = false,
    onKeyboardHide: () -> Unit = {},
    onFocusClear: () -> Unit = {},
    onMenuExpand: () -> Unit = {},
    onMenuDismiss: () -> Unit = {},
    onNavigateToCaloriesGoalListScreen: () -> Unit = {}
) {

    var dropDownMenuSize by remember {
        mutableStateOf(Size.Zero)
    }

    Column(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopStart)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = labelText,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontStyle = FontStyle.Italic
                )
            )
            Spacer(modifier = Modifier.weight(1f))
            if (isCaloriesGoal) {
                Text(
                    text = stringResource(id = R.string.calculate),
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = MaterialTheme.colorScheme.tertiary,
                        fontWeight = FontWeight.SemiBold,
                        textDecoration = TextDecoration.Underline
                    ),
                    modifier = Modifier.clickable {
                        onNavigateToCaloriesGoalListScreen()
                    }
                )
            }
        }
        TextField(
            value = valueText,
            onValueChange = onValueChange,
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
            isError = isError,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = if (isNumeric) KeyboardType.Decimal else KeyboardType.Text
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    onKeyboardHide()
                    onFocusClear()
                }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { layoutCoordinates ->
                    dropDownMenuSize = layoutCoordinates.size.toSize()
                }
        )
        if (isDropDownMenu) {
            DropdownMenu(
                expanded = isDropDownMenuExpanded,
                onDismissRequest = onMenuDismiss,
                modifier = Modifier
                    .width(with(LocalDensity.current) { dropDownMenuSize.width.toDp() })
            ) {
                dropDownMenuItems.onEach { item ->
                    DropdownMenuItem(
                        text = {
                            Text(text = item.toDropDownMenuString())
                        },
                        onClick = {
                            onValueChange(item)
                            onMenuDismiss()
                        }
                    )
                }
            }
        }
    }
}