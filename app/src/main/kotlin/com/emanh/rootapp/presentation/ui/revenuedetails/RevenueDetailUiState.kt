package com.emanh.rootapp.presentation.ui.revenuedetails

import com.emanh.rootapp.domain.model.SongsModel

data class RevenueData(
    val id: Int, val totalAmount: String, val fluctuations: String, val isIncrease: Boolean, val sampleData: List<Float>
)

data class RevenueDetailUiState(
    val isLoading: Boolean = false,
    val song: SongsModel? = null,
    val revenueList: List<RevenueData> = listOf(
            RevenueData(id = 0,
                        totalAmount = "$2,402.03",
                        fluctuations = "+12.78%",
                        isIncrease = true,
                        sampleData = listOf(10f, 25.7f, 20.5f, 30f, 45f, 40f, 53.2f, 60f)),
            RevenueData(id = 1,
                        totalAmount = "$5,22.00",
                        fluctuations = "+2.07%",
                        isIncrease = true,
                        sampleData = listOf(2f, 24.2f, 17.3f, 22.3f, 45f, 47.2f, 53.2f, 54.3f)),
            RevenueData(id = 2,
                        totalAmount = "$8,182.18",
                        fluctuations = "-2.23%",
                        isIncrease = false,
                        sampleData = listOf(40.3f, 52.1f, 45f, 72.3f, 80.2f, 71.2f, 70.1f, 62.4f)),
            RevenueData(id = 3,
                        totalAmount = "$812.31",
                        fluctuations = "-2.86%",
                        isIncrease = false,
                        sampleData = listOf(4.2f, 6.7f, 8.2f, 10.3f, 12.5f, 11.3f, 10.5f, 10.2f)),
            RevenueData(id = 4,
                        totalAmount = "$1829.23",
                        fluctuations = "8.3%",
                        isIncrease = true,
                        sampleData = listOf(9.2f, 19.2f, 20.1f, 21.6f, 23.1f, 25.3f, 27.6f, 30.1f)),
            RevenueData(id = 5,
                        totalAmount = "$1273.21",
                        fluctuations = "-1.92%",
                        isIncrease = false,
                        sampleData = listOf(42.3f, 31.3f, 52.1f, 56.2f, 55.1f, 60.2f, 57.2f, 56.1f)),
            RevenueData(id = 6,
                        totalAmount = "$312.31",
                        fluctuations = "8.8%",
                        isIncrease = true,
                        sampleData = listOf(1.2f, 4.2f, 5.9f, 7.1f, 10.2f, 9.1f, 12.5f, 13.6f)),
            RevenueData(id = 7,
                        totalAmount = "$2114.23",
                        fluctuations = "-2.49%",
                        isIncrease = false,
                        sampleData = listOf(24.3f, 25.3f, 28.3f, 32.5f, 30.1f, 35.7f, 40.1f, 39.1f)),
    )
)