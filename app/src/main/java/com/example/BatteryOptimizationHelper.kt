package com.example

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import android.provider.Settings
import android.util.Log

/**
 * Helper to request battery optimization exemption.
 * Without this, the OS may delay or prevent prayer alarms from firing on time.
 */
object BatteryOptimizationHelper {

    private const val TAG = "BatteryOptimization"

    /**
     * Check if the app is exempt from battery optimization
     */
    fun isIgnoringBatteryOptimizations(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val pm = context.getSystemService(Context.POWER_SERVICE) as PowerManager
            pm.isIgnoringBatteryOptimizations(context.packageName)
        } else {
            true
        }
    }

    /**
     * Request the user to disable battery optimization for this app.
     * This opens the system settings where the user can grant the permission.
     */
    fun requestIgnoreBatteryOptimizations(activity: Activity) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!isIgnoringBatteryOptimizations(activity)) {
                    val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
                    intent.data = Uri.parse("package:${activity.packageName}")
                    activity.startActivity(intent)
                    Log.d(TAG, "📱 Opened battery optimization settings")
                } else {
                    Log.d(TAG, "✅ Battery optimization already disabled")
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to open battery optimization settings", e)
            // Fallback: open app settings
            openAppSettings(activity)
        }
    }

    /**
     * Open app details in system settings (fallback)
     */
    fun openAppSettings(context: Context) {
        try {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.data = Uri.parse("package:${context.packageName}")
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to open app settings", e)
        }
    }

    /**
     * Open autostart settings for the device manufacturer.
     * This is needed on Xiaomi, Huawei, Oppo, Vivo, etc. for background services.
     */
    fun openAutoStartSettings(context: Context) {
        try {
            val manufacturers = listOf(
                "xiaomi" to "com.miui.securitycenter",
                "huawei" to "com.huawei.systemmanager",
                "huawei" to "com.huawei.systemmanager.optimize.process.ProtectActivity",
                "oppo" to "com.coloros.safecenter",
                "oppo" to "com.oppo.safe",
                "vivo" to "com.iqoo.secure",
                "samsung" to "com.samsung.android.lool",
                "oneplus" to "com.oneplus.security"
            )

            for ((_, component) in manufacturers) {
                try {
                    val intent = Intent()
                    intent.component = ComponentName(
                        component.substringBeforeLast("."),
                        component
                    )
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                    Log.d(TAG, "📱 Opened autostart settings for: $component")
                    return
                } catch (e: Exception) {
                    // Try next manufacturer
                    continue
                }
            }

            // If no manufacturer-specific settings found, open app settings
            openAppSettings(context)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to open autostart settings", e)
        }
    }
}
