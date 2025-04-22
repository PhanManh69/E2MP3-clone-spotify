package com.emanh.rootapp.di

import com.emanh.rootapp.presentation.navigation.router.AppRouter
import com.emanh.rootapp.presentation.navigation.router.AppRouterImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object ActivityModule {
    @ActivityRetainedScoped
    @Provides
    fun provideAppNavigator(): AppRouter {
        return AppRouterImpl()
    }
}