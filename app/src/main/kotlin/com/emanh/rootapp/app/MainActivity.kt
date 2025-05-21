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

private const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var appRouter: AppRouter

    @Inject
    lateinit var databaseInitializer: DatabaseInitializer

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        enableEdgeToEdge()
        setImmersiveMode()
        super.onCreate(savedInstanceState)
//        deleteDatabase("e2mp3_db")
        databaseInitialized()
        setContent {
            E2MP3Theme {
                MainScreen(appRouter = appRouter)
            }
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
                    Log.d(TAG, "Database initialized: $isInitialized")
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