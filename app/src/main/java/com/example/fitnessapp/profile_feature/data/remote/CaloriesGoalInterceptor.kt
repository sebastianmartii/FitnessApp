package com.example.fitnessapp.profile_feature.data.remote

import com.example.fitnessapp.core.util.ApiKeys
import okhttp3.Interceptor
import okhttp3.Response

class CaloriesGoalInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader(name = "X-RapidAPI-Host", value = "fitness-calculator.p.rapidapi.com")
            .addHeader(name = "X-RapidAPI-Key", value = ApiKeys.FITNESS_CALCULATOR_API_KEY)
            .build()
        return chain.proceed(request)
    }
}