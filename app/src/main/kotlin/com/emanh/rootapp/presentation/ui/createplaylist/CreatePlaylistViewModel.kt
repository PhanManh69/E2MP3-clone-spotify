package com.emanh.rootapp.presentation.ui.createplaylist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emanh.rootapp.domain.model.PlaylistsModel
import com.emanh.rootapp.domain.usecase.PlaylistsUseCase
import com.emanh.rootapp.presentation.navigation.CreatePlaylistScreenNavigation
import com.emanh.rootapp.presentation.navigation.PlaylistYourScreenNavigation
import com.emanh.rootapp.presentation.navigation.extensions.NavActions.goBack
import com.emanh.rootapp.presentation.navigation.extensions.NavActions.navigateTo
import com.emanh.rootapp.presentation.navigation.router.AppRouter
import com.emanh.rootapp.utils.removeAccents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class CreatePlaylistViewModel @Inject constructor(
    private val appRouter: AppRouter,
    private val playlistsUseCase: PlaylistsUseCase,
) : ViewModel() {

    fun onCancelClick() {
        appRouter.getMainNavController()?.goBack()
    }

    fun onCreateClick(playlistName: String) {
        val userIdFake = 2L
        val playlist = PlaylistsModel(title = playlistName,
                                      subtitle = playlistName,
                                      normalizedSearchValue = playlistName.removeAccents(),
                                      ownerId = userIdFake,
                                      releaseDate = getCurrentDate(),
                                      songsIdList = emptyList())

        viewModelScope.launch {
            val playlistId = playlistsUseCase.insertPlaylistYour(playlist)
            appRouter.getMainNavController()
                ?.navigateTo(route = PlaylistYourScreenNavigation.getRoute(playlistId),
                             popUpToRoute = CreatePlaylistScreenNavigation.getRoute(),
                             inclusive = true)
        }
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        return dateFormat.format(Date())
    }
}