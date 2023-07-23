package com.example.fitnessapp.profile_feature.presentation.sign_in

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fitnessapp.R
import com.example.fitnessapp.profile_feature.domain.model.Gender
import com.example.fitnessapp.profile_feature.domain.model.UserProfile
import com.example.fitnessapp.ui.theme.FitnessAppTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProfileListScreen(
    profileList: List<UserProfile>,
    onProfileChosen: (String) -> Unit,
    onGoBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Box(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
        ) {
            Button(
                onClick = onGoBack,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.onBackground
                ),
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = Icons.Default.ChevronLeft,
                    contentDescription = stringResource(id = R.string.go_back),
                )
            }
        }
        FlowRow(
            maxItemsInEachRow = 2,
            modifier = Modifier.align(Alignment.Center)
        ) {
            profileList.onEach {
                ProfileItem(name = it.name, gender = it.gender)
            }
        }
    }
}


@Composable
private fun ProfileItem(
    name: String,
    gender: Gender,
    modifier: Modifier = Modifier
) {
    Surface(
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        shape = MaterialTheme.shapes.small,
        modifier = modifier
            .height(240.dp)
            .width(180.dp)
            .padding(8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxSize()
        ) {
            when (gender) {
                Gender.MALE -> {
                    Image(
                        painter = painterResource(id = R.drawable.profilemale),
                        contentDescription = stringResource(
                            id = R.string.profile_male
                        ),
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
                Gender.FEMALE -> {
                    Image(
                        painter = painterResource(id = R.drawable.profilefemale),
                        contentDescription = stringResource(
                            id = R.string.profile_female
                        ),
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                Gender.NONE -> {}
            }
            Text(
                text = name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Preview
@Composable
private fun ProfileListScreenPreview() {
    FitnessAppTheme {
        ProfileListScreen(
            profileList = listOf(
                UserProfile("Sebastian", Gender.MALE),
                UserProfile("Marek", Gender.MALE),
                UserProfile("Ania", Gender.FEMALE),
            ),
            onProfileChosen = {},
            onGoBack = { /*TODO*/ }
        )
    }
}