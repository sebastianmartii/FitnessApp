package com.example.fitnessapp.nutrition_calculator_feature.presentation

import app.cash.turbine.test
import com.example.fitnessapp.MainDispatcherRule
import com.example.fitnessapp.core.database.dao.CurrentUserDao
import com.google.common.truth.Truth.assertThat
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NutritionViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var currentUserDao: CurrentUserDao

    private lateinit var viewModel: NutritionViewModel

    @Before
    fun setUp() {
        viewModel = NutritionViewModel(currentUserDao)
    }

    @Test
    fun `Change pager page, page channeled correctly`() = runTest {
        viewModel.onPageChange(2)
        viewModel.pagerFlow.test {
            val emission = awaitItem()
            assertThat(emission).isEqualTo(2)
        }
    }
}