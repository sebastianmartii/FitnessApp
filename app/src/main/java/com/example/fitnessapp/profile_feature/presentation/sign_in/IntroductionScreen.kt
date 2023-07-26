package com.example.fitnessapp.profile_feature.presentation.sign_in

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fitnessapp.R
import com.example.fitnessapp.profile_feature.data.mappers.toGenderString
import com.example.fitnessapp.profile_feature.domain.model.Gender
import com.example.fitnessapp.profile_feature.domain.model.genderList
import com.example.fitnessapp.ui.theme.FitnessAppTheme

@Composable
fun IntroductionScreen(
    name: String,
    gender: Gender,
    menuExpanded: Boolean,
    onNameChange: (String) -> Unit,
    onGenderChange: (Gender) -> Unit,
    onMenuExpandedChange: (Boolean) -> Unit,
    onNavigateToMeasurementsScreen: () -> Unit,
    onNavigateToProfileListScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(start = 48.dp, end = 48.dp, bottom = 128.dp)
                .fillMaxSize()
        ) {
            Text(
                text = stringResource(id = R.string.welcome),
                style = MaterialTheme.typography.displayLarge,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = name,
                onValueChange = {
                    onNameChange(it)
                },
                label = {
                    Text(text = stringResource(id = R.string.username))
                },
                supportingText = {
                    Text(
                        text = stringResource(id = R.string.valid_username_text),
                        style = MaterialTheme.typography.labelMedium
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
            Box(
                modifier = Modifier
                    .height(56.dp)
                    .fillMaxWidth()
                    .wrapContentWidth()
                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.outline,
                        MaterialTheme.shapes.extraSmall
                    )
                    .clickable {
                        onMenuExpandedChange(true)
                    }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxSize()
                ) {
                    Text(
                        text = gender.toGenderString(),
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        modifier = Modifier.fillMaxWidth(0.9f)
                    )
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = stringResource(id = R.string.expand),
                    )
                }
                DropdownMenu(
                    expanded = menuExpanded,
                    onDismissRequest = {
                        onMenuExpandedChange(false)
                    },
                    content = {
                        genderList.onEach { gender ->
                            DropdownMenuItem(
                                text = {
                                    Text(text = gender.toGenderString())
                                },
                                onClick = {
                                    onGenderChange(gender)
                                    onMenuExpandedChange(false)
                                }
                            )
                        }
                    }
                )
            }
            Text(
                text = stringResource(id = R.string.introduce_yourself),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = stringResource(id = R.string.existing_profile),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                textDecoration = TextDecoration.Underline,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .clickable {
                        onNavigateToProfileListScreen()
                    }
            )
            Box(modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = {
                        onNavigateToMeasurementsScreen()
                    },
                    enabled = Validators.isUserNameValid(name) && Validators.isGenderValid(gender),
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = stringResource(id = R.string.introduction_done),
                    )
                }
            }
        }
    }
}


@Preview
@Composable
private fun IntroductionScreenPreview() {
    FitnessAppTheme {
        IntroductionScreen(
            name = "",
            gender = Gender.NONE,
            menuExpanded = false,
            onNameChange = {},
            onGenderChange = {},
            onMenuExpandedChange = {},
            onNavigateToProfileListScreen = {},
            onNavigateToMeasurementsScreen = { /*TODO*/ })
    }
}