package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Initialize notification channels (آمن)
        try {
            IslamicNotificationManager.initNotificationChannels(this)
        } catch (e: Exception) {
            android.util.Log.e("MainActivity", "Channel init error", e)
        }

        // 2. Schedule countdown worker (آمن)
        try {
            CountdownWorker.schedule(this)
        } catch (e: Exception) {
            android.util.Log.e("MainActivity", "Countdown schedule error", e)
        }

        // 3. Request notification permission (Android 13+)
        try {
            if (!NotificationPermissionHelper.hasNotificationPermission(this)) {
                NotificationPermissionHelper.requestNotificationPermission(this)
            }
        } catch (e: Exception) {
            android.util.Log.e("MainActivity", "Notification permission error", e)
        }

        // 4. Request battery optimization exemption
        try {
            if (!BatteryOptimizationHelper.isIgnoringBatteryOptimizations(this)) {
                BatteryOptimizationHelper.requestIgnoreBatteryOptimizations(this)
            }
        } catch (e: Exception) {
            android.util.Log.e("MainActivity", "Battery optimization error", e)
        }

        enableEdgeToEdge()

        setContent {
            val viewModel: IslamicViewModel by viewModels()
            val settingsState by viewModel.settingsState.collectAsState()
            val settings = settingsState ?: SettingsEntity()

            MyApplicationTheme(darkTheme = settings.useDarkTheme) {
                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "splash",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        // شاشة الانتظار (Splash) أولاً
                        composable("splash") {
                            SplashView(
                                viewModel = viewModel,
                                onLoadingFinished = {
                                    navController.navigate("home") {
                                        popUpTo("splash") { inclusive = true }
                                    }
                                }
                            )
                        }

                        // الرئيسية بعد Splash
                        composable("home") {
                            HomeView(
                                viewModel = viewModel,
                                settings = settings,
                                onNavigate = { route ->
                                    navController.navigate(route)
                                }
                            )
                        }
                        composable("quran") { QuranView(viewModel = viewModel, onBack = { navController.popBackStack() }) }
                        composable("prayers") { PrayersView(viewModel = viewModel, settings = settings, onBack = { navController.popBackStack() }) }
                        composable("azkar") { AzkarView(viewModel = viewModel, onBack = { navController.popBackStack() }) }
                        composable("duas") { AzkarView(viewModel = viewModel, onBack = { navController.popBackStack() }) }
                        composable("radio") { RadioView(viewModel = viewModel, onBack = { navController.popBackStack() }) }
                        composable("sebha") { SebhaView(viewModel = viewModel, onBack = { navController.popBackStack() }) }
                        composable("names") { NamesView(viewModel = viewModel, onBack = { navController.popBackStack() }) }
                        composable("qibla") { QiblaView(viewModel = viewModel, settings = settings, onBack = { navController.popBackStack() }) }
                        composable("chat") { ChatView(viewModel = viewModel, onBack = { navController.popBackStack() }) }
                        composable("settings") { SettingsView(viewModel = viewModel, settings = settings, onBack = { navController.popBackStack() }) }
                        composable("calendar") { HijriCalendarView(onBack = { navController.popBackStack() }) }
                        composable("tajweed") { TajweedView(onBack = { navController.popBackStack() }) }
                        composable("fiqh") { FiqhView(onBack = { navController.popBackStack() }) }
                        composable("sermons") { SermonsView(onBack = { navController.popBackStack() }) }
                        composable("hajj") { HajjView(onBack = { navController.popBackStack() }) }
                        composable("khatma") { KhatmaView(onBack = { navController.popBackStack() }) }
                        composable("ruqyah") { RuqyahView(onBack = { navController.popBackStack() }) }
                        composable("quiz") { IslamicQuizView(viewModel = viewModel, onBack = { navController.popBackStack() }) }
                        composable("zakat") { ZakatCalculatorView(onBack = { navController.popBackStack() }) }
                        composable("tracker") { PrayerTrackerView(onBack = { navController.popBackStack() }) }
                        composable("hadith") { HadithCollectionView(onBack = { navController.popBackStack() }) }
                        composable("tafseer") { TafseerView(onBack = { navController.popBackStack() }) }
                        composable("quran_full") { QuranReaderView(viewModel = viewModel, onBack = { navController.popBackStack() }) }
                        composable("ramadan") { RamadanView(onBack = { navController.popBackStack() }) }
                        composable("fasting") { WhiteDaysFastingView(onBack = { navController.popBackStack() }) }
                        composable("sahih") { SahihBukhariMuslimView(onBack = { navController.popBackStack() }) }
                        composable("themes") { ThemesView(onBack = { navController.popBackStack() }) }
                        composable("adhkar_after_fajr") { AdhkarAfterSalahView("الفجر") { navController.popBackStack() } }
                        composable("adhkar_after_dhuhr") { AdhkarAfterSalahView("الظهر") { navController.popBackStack() } }
                        composable("adhkar_after_asr") { AdhkarAfterSalahView("العصر") { navController.popBackStack() } }
                        composable("adhkar_after_maghrib") { AdhkarAfterSalahView("المغرب") { navController.popBackStack() } }
                        composable("adhkar_after_isha") { AdhkarAfterSalahView("العشاء") { navController.popBackStack() } }
                        composable("complete_adhkar") { CompleteAdhkarView { navController.popBackStack() } }
                    }
                }
            }
        }
    }
}