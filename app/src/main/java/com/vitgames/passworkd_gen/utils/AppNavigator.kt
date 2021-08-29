package com.vitgames.passworkd_gen.utils

import android.content.Context
import android.content.Intent
import android.provider.Settings
import javax.inject.Inject

interface AppNavigator {
    fun navigateTo(destination: AppNavigationInput)
}

class AppNavigatorImpl @Inject constructor(
    private val context: Context
) : AppNavigator {
    override fun navigateTo(destination: AppNavigationInput) {
        when (destination) {
            AppNavigationInput.Settings -> {
                context.startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
            }
        }
    }
}

sealed class AppNavigationInput {
    object Settings : AppNavigationInput()
}