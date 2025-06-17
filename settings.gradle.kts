@file:Suppress("UnstableApiUsage")

import java.io.FileInputStream
import java.util.Properties

val keystorePropertiesFile = file("keystore.properties")
val keystoreProperties = Properties()

if (keystorePropertiesFile.exists()) {
    keystoreProperties.load(FileInputStream(keystorePropertiesFile))
}

pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = keystoreProperties["jitpackUrl"]?.toString() ?: "https://jitpack.io")
    }
}

rootProject.name = keystoreProperties["rootProjectName"]?.toString() ?: "E2MP3"
include(":${keystoreProperties["appModuleName"]?.toString() ?: "app"}")