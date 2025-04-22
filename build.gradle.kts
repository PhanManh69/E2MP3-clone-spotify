buildscript {
    dependencies {
        classpath(libs.navigation.safeArgsGradlePlugin)
        classpath(libs.hilt.androidGradlePlugin)
    }
}
plugins {
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.serialization.compiler) apply false
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.dagger.hilt.android) apply false
    alias(libs.plugins.kotlin.ksp) apply false
}