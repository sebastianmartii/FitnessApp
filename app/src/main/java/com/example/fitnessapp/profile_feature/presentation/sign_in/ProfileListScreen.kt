package com.example.fitnessapp.profile_feature.presentation.sign_in

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.fitnessapp.R
import com.example.fitnessapp.profile_feature.domain.model.Gender
import com.example.fitnessapp.profile_feature.domain.model.UserProfile
import com.example.fitnessapp.profile_feature.presentation.ProfileItem

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ProfileListScreen(
    profileList: List<UserProfile>,
    onEvent: (SignInEvent) -> Unit,
    onNavigateBack: () -> Unit,
    onNavigateToOverviewScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
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
                            onEvent(SignInEvent.OnSignInChosen(it.userID))
                            onNavigateToOverviewScreen()
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