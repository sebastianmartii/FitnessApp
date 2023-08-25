package com.example.fitnessapp.activities_feature.data.mappers

import com.example.fitnessapp.activities_feature.data.remote.dto.Data
import com.example.fitnessapp.activities_feature.domain.model.Activity

fun Data.toActivityList(): Activity {
    return Activity(
        id = this.id,
        name = this.activity,
        description = this.description
    )
}

