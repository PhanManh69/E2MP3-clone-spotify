package com.emanh.rootapp.presentation.navigation.router

import androidx.navigation.NavController

interface AppRouter {
    fun bind(navController: NavController)

    fun unbind()

    fun getNavController(): NavController?
}