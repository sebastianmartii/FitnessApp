package com.example.fitnessapp.profile_feature.presentation.sign_in

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fitnessapp.R
import com.example.fitnessapp.profile_feature.data.mappers.toGenderString
import com.example.fitnessapp.profile_feature.domain.model.Gender
import com.example.fitnessapp.ui.theme.FitnessAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IntroductionScreen(
    name: String,
    gender: Gender,
    onEvent: (SignInEvent) -> Unit,
    onNavigateToMeasurementsScreen: () -> Unit,
    onNavigateToProfileListScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .wrapContentSize()
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
                        onEvent(SignInEvent.OnNameChange(it))
                    },
                    label = {
                        Text(text = stringResource(id = R.string.username))
                    },
                    isError = !Validators.isUserNameValid(name) && name.isNotEmpty(),
                    supportingText = {
                        if (!Validators.isUserNameValid(name) && name.isNotEmpty()) {
                            Text(
                                text = stringResource(id = R.string.valid_username_text),
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Gender.MALE.also {
                        FilterChip(
                            selected = gender == it,
                            onClick = {
                                onEvent(SignInEvent.OnGenderChange(it))
                            },
                            label = {
                                Text(text = it.toGenderString())
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Male,
                                    contentDescription = stringResource(id = R.string.gender_male)
                                )
                            }
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Gender.FEMALE.also {
                        FilterChip(
                            selected = gender == it,
                            onClick = {
                                onEvent(SignInEvent.OnGenderChange(it))
                            },
                            label = {
                                Text(text = it.toGenderString())
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Female,
                                    contentDescription = stringResource(id = R.string.gender_female)
                                )
                            }
                        )
                    }
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
                            onEvent(SignInEvent.OnSignInSelect)
                            onNavigateToProfileListScreen()
                        }
                )
                Box(modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = {
                            onNavigateToMeasurementsScreen()
                        },
                        enabled = Validators.isUserNameValid(name) && Validators.isGenderValid(
                            gender
                        ),
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
}


@Preview
@Composable
private fun IntroductionScreenPreview() {
    FitnessAppTheme {
        IntroductionScreen(
            name = "",
            gender = Gender.NONE,
            onEvent = {},
            onNavigateToMeasurementsScreen = {},
            onNavigateToProfileListScreen = {},
            )
    }
}