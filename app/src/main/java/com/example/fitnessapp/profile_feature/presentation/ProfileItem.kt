package com.example.fitnessapp.profile_feature.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.fitnessapp.R
import com.example.fitnessapp.profile_feature.domain.model.Gender

@Composable
fun ProfileItem(
    name: String,
    gender: Gender,
    modifier: Modifier = Modifier,
    isNameVisible: Boolean = true,
    hasBorder: Boolean = true
) {
    Surface(
        border = BorderStroke(1.dp, if (hasBorder) MaterialTheme.colorScheme.outline else Color.Transparent),
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
            if (isNameVisible) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}