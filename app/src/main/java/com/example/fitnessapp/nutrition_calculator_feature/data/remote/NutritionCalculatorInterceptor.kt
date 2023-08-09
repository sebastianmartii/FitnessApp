package com.example.fitnessapp.nutrition_calculator_feature.data.remote

import com.example.fitnessapp.core.util.ApiKeys
import okhttp3.Interceptor
import okhttp3.Response

class NutritionCalculatorInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader(name = "X-Api-Key", value = ApiKeys.NUTRITION_CALCULATOR_API_KEY)
            .build()
        return chain.proceed(request)
    }
}