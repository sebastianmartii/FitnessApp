package com.example.fitnessapp.profile_feature.data.mappers

import com.example.fitnessapp.core.database.entity.CurrentUser
import com.example.fitnessapp.profile_feature.domain.model.Gender
import com.example.fitnessapp.profile_feature.domain.model.UserProfile

fun CurrentUser.toUserProfile(): UserProfile {
    return UserProfile(
        name = this.name,
        gender = this.gender.toGender()
    )
}

fun String.toGender(): Gender {
    return when(this) {
        "male" -> Gender.MALE
        else -> Gender.FEMALE
    }
}

fun Gender.toGenderString(): String {
    return when(this) {
        Gender.MALE -> "male"
        Gender.FEMALE -> "female"
        Gender.NONE -> "none"
    }
}