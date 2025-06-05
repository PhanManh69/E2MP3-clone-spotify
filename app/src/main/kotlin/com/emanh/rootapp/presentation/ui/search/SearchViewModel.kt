package com.emanh.rootapp.presentation.ui.search

import androidx.lifecycle.ViewModel
import com.emanh.rootapp.presentation.navigation.SearchInputScreenNavigation
import com.emanh.rootapp.presentation.navigation.extensions.NavActions.navigateTo
import com.emanh.rootapp.presentation.navigation.router.AppRouter
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val appRouter: AppRouter
) : ViewModel() {

    fun goToSearchInput() {
        appRouter.getMainNavController()?.navigateTo(SearchInputScreenNavigation.getRoute())
    }
}