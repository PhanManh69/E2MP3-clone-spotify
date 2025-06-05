package com.emanh.rootapp.presentation.navigation.router

import androidx.navigation.NavController

class AppRouterImpl : AppRouter {

    private var authNavController: NavController? = null
    private var mainNavController: NavController? = null

    // Auth navigation binding
    override fun bindAuth(navController: NavController) {
        this.authNavController = navController
    }

    override fun unbindAuth() {
        this.authNavController = null
    }

    override fun getAuthNavController(): NavController? = authNavController

    // Main navigation binding
    override fun bindMain(navController: NavController) {
        this.mainNavController = navController
    }

    override fun unbindMain() {
        this.mainNavController = null
    }

    override fun getMainNavController(): NavController? = mainNavController
}
