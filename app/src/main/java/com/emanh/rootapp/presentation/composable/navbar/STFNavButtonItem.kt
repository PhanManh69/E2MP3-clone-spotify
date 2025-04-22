package com.emanh.rootapp.presentation.composable.navbar

import com.emanh.rootapp.R
import com.emanh.rootapp.presentation.composable.STFTabIconType
import com.emanh.rootapp.presentation.navigation.route.AppNavigationRoute
import kotlinx.serialization.Serializable

@Serializable
sealed class STFNavButtonItem<T>(
    val name: Int, val iconDefault: Int, val iconSelected: Int, val route: T
) {
    abstract fun type(isSelected: Boolean): STFTabIconType
    fun icon(isSelected: Boolean): Int = if (isSelected) iconSelected else iconDefault

    @Serializable
    data object Home : STFNavButtonItem<AppNavigationRoute.Home>(name = R.string.home,
                                                                 iconDefault = R.drawable.ic_32_home,
                                                                 iconSelected = R.drawable.ic_32_home_fill,
                                                                 route = AppNavigationRoute.Home) {
        override fun type(isSelected: Boolean) = if (isSelected) STFTabIconType.Select else STFTabIconType.Default
    }

    @Serializable
    data object Search : STFNavButtonItem<AppNavigationRoute.Search>(name = R.string.search,
                                                                     iconDefault = R.drawable.ic_32_search,
                                                                     iconSelected = R.drawable.ic_32_search_fill,
                                                                     route = AppNavigationRoute.Search) {
        override fun type(isSelected: Boolean) = if (isSelected) STFTabIconType.Select else STFTabIconType.Default
    }

    @Serializable
    data object YourLibrary : STFNavButtonItem<AppNavigationRoute.YourLibrary>(name = R.string.your_library,
                                                                               iconDefault = R.drawable.ic_32_library,
                                                                               iconSelected = R.drawable.ic_32_library_fill,
                                                                               route = AppNavigationRoute.YourLibrary) {
        override fun type(isSelected: Boolean) = if (isSelected) STFTabIconType.Select else STFTabIconType.Default
    }

//    @Serializable
//    data object TestComposable :
//            STFNavButtonItem<AppNavigationRoute.TestComposable>(name = R.string.premium, iconDefault = R.drawable.ic_32_premium,
//                                                                iconSelected = R.drawable.ic_32_premium, route = AppNavigationRoute.TestComposable) {
//        override fun type(isSelected: Boolean) = if (isSelected) STFTabIconType.Select else STFTabIconType.Default
//    }
}