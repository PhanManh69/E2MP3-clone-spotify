package com.emanh.rootapp.app

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.lifecycleScope
import com.emanh.rootapp.app.main.MainScreen
import com.emanh.rootapp.data.db.initializer.DatabaseInitializer
import com.emanh.rootapp.presentation.navigation.router.AppRouter
import com.emanh.rootapp.presentation.theme.E2MP3Theme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.core.content.edit
import com.emanh.rootapp.app.login.LoginScreen

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var appRouter: AppRouter

    @Inject
    lateinit var databaseInitializer: DatabaseInitializer

    private companion object {
        const val PREFS_NAME = "app_prefs"
        const val KEY_FIRST_INSTALL = "is_first_install"
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        enableEdgeToEdge()
        setImmersiveMode()
        super.onCreate(savedInstanceState)
//        handleFirstInstall()
//        deleteDatabase("e2mp3_db")
        databaseInitialized()
        setContent {
            E2MP3Theme {
                LoginScreen()
//                MainScreen(appRouter = appRouter)
            }
        }
    }

    private fun handleFirstInstall() {
        val prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        val isFirstInstall = prefs.getBoolean(KEY_FIRST_INSTALL, true)

        if (isFirstInstall) {

            try {
                deleteDatabase("e2mp3_db")

                prefs.edit { putBoolean(KEY_FIRST_INSTALL, false) }

            } catch (e: Exception) {
                Log.e(TAG, "Failed to delete database: $e")
            }
        } else {
            Log.d(TAG, "Not first install - keeping existing database")
        }
    }

    private fun setImmersiveMode() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            window.attributes.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        }
    }

    private fun databaseInitialized() {
        lifecycleScope.launch {
            try {
                databaseInitializer.isDatabaseInitialized.collect { isInitialized ->
                    if (!isInitialized) {
                        databaseInitializer.reinitializeDatabase()
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Database validation failed: $e")
                Toast.makeText(this@MainActivity, "Database error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}