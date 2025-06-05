package com.emanh.rootapp.presentation.navigation.router

import androidx.navigation.NavController

interface AppRouter {
    // Auth navigation
    fun bindAuth(navController: NavController)
    fun unbindAuth()
    fun getAuthNavController(): NavController?

    // Main navigation
    fun bindMain(navController: NavController)
    fun unbindMain()
    fun getMainNavController(): NavController?
}