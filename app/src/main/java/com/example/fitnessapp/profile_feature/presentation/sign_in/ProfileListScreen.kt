package com.example.fitnessapp.profile_feature.presentation.sign_in

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ProfileListScreen(
    profileList: List<UserProfile>,
    onEvent: (ProfileEvent) -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.back_button)
                        )
                    }
                },
                title = {
                    Text(text = "")
                }
            )
        },
        modifier = modifier
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ){
            FlowRow(
                maxItemsInEachRow = 2,
                modifier = Modifier
                    .align(Alignment.Center)
            ) {
                profileList.onEach {
                    ProfileItem(
                        name = it.name,
                        gender = it.gender,
                        modifier = Modifier.clickable {
                            onEvent(ProfileEvent.OnProfileChosen(it.userID))
                        }
                    )
                }
                if (profileList.isEmpty()) {
                    ProfileItem(
                        name = stringResource(id = R.string.no_profiles),
                        gender = Gender.MALE
                    )
                }
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
                UserProfile(0,"Sebastian", Gender.MALE),
                UserProfile(1,"Marek", Gender.MALE),
                UserProfile(2,"Ania", Gender.FEMALE),
            ),
            onEvent = {},
            onNavigateBack = {}
        )
    }
}