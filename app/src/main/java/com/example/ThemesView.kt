package com.example

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Brightness4
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * 5 ثيمات للتطبيق
 * - أخضر إسلامي (افتراضي)
 * - أزرق ليلي
 * - ذهبي فاخر
 * - رمادي مودرن
 * - ليلي داكن
 */

data class AppTheme(
    val id: String,
    val nameAr: String,
    val nameEn: String,
    val primary: Color,
    val secondary: Color,
    val accent: Color,
    val background: Color,
    val surface: Color,
    val onPrimary: Color = Color.White,
    val description: String
)

object AppThemes {
    val islamicGreen = AppTheme(
        id = "green",
        nameAr = "الأخضر الإسلامي",
        nameEn = "Islamic Green",
        primary = Color(0xFF0D4A30),
        secondary = Color(0xFFC9A84C),
        accent = Color(0xFFC9A84C),
        background = Color(0xFFFAFAFA),
        surface = Color(0xFFFFFFFF),
        description = "الث التقليدي الأخضر والذهبي"
    )

    val nightBlue = AppTheme(
        id = "blue",
        nameAr = "الأزرق الليلي",
        nameEn = "Night Blue",
        primary = Color(0xFF1A237E),
        secondary = Color(0xFF42A5F5),
        accent = Color(0xFFFFCA28),
        background = Color(0xFFF5F7FA),
        surface = Color(0xFFFFFFFF),
        description = "أنيق ومريح للعين"
    )

    val golden = AppTheme(
        id = "golden",
        nameAr = "الذهبي الفاخر",
        nameEn = "Royal Gold",
        primary = Color(0xFF6D4C00),
        secondary = Color(0xFFFFC107),
        accent = Color(0xFFFF8F00),
        background = Color(0xFFFFFBF5),
        surface = Color(0xFFFFFFFF),
        description = "فخامة وأناقة"
    )

    val modernGray = AppTheme(
        id = "gray",
        nameAr = "الرمادي المودرن",
        nameEn = "Modern Gray",
        primary = Color(0xFF455A64),
        secondary = Color(0xFF78909C),
        accent = Color(0xFF26A69A),
        background = Color(0xFFFAFAFA),
        surface = Color(0xFFFFFFFF),
        description = "بسيط وعصري"
    )

    val darkMode = AppTheme(
        id = "dark",
        nameAr = "الليلي الداكن",
        nameEn = "Dark Mode",
        primary = Color(0xFF1B5E20),
        secondary = Color(0xFFC9A84C),
        accent = Color(0xFFC9A84C),
        background = Color(0xFF121212),
        surface = Color(0xFF1E1E1E),
        onPrimary = Color.White,
        description = "مريح للعين في الليل"
    )

    val allThemes = listOf(islamicGreen, nightBlue, golden, modernGray, darkMode)

    fun getTheme(id: String): AppTheme = allThemes.find { it.id == id } ?: islamicGreen
}

object ThemeStorage {
    private const val PREF_NAME = "noor_islam_theme"
    private const val KEY_THEME = "selected_theme_id"

    fun save(context: Context, themeId: String) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_THEME, themeId).apply()
    }

    fun getSelected(context: Context): String {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_THEME, "green") ?: "green"
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemesView(onBack: () -> Unit) {
    val context = LocalContext.current
    var selectedThemeId by remember { mutableStateOf(ThemeStorage.getSelected(context)) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("🎨 الثيمات والألوان", fontWeight = FontWeight.Bold, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, "رجوع", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF0D4A30))
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    "اختر الثيم الذي يناسبك:",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            items(AppThemes.allThemes) { theme ->
                ThemeCard(
                    theme = theme,
                    isSelected = theme.id == selectedThemeId,
                    onClick = {
                        selectedThemeId = theme.id
                        ThemeStorage.save(context, theme.id)
                    }
                )
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF8E1))
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text("💡 ملاحظة:", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF6D4C00))
                        Text(
                            "التغييرات على الثيم تحتاج إعادة فتح بعض الشاشات لتظهر. " +
                            "في الإصدار القادم، سيتم تطبيق التغييرات فورياً.",
                            fontSize = 12.sp,
                            color = Color(0xFF6D4C00),
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ThemeCard(
    theme: AppTheme,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .border(
                width = if (isSelected) 3.dp else 0.dp,
                color = if (isSelected) Color(0xFF0D4A30) else Color.Transparent,
                shape = RoundedCornerShape(12.dp)
            ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color(0xFFE8F5E9) else Color(0xFFFAFAFA)
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Theme preview circles
                Row {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(theme.primary, CircleShape)
                            .border(2.dp, Color.White, CircleShape)
                    )
                    Spacer(Modifier.width(4.dp))
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(theme.secondary, CircleShape)
                            .border(2.dp, Color.White, CircleShape)
                    )
                    Spacer(Modifier.width(4.dp))
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(theme.accent, CircleShape)
                            .border(2.dp, Color.White, CircleShape)
                    )
                }

                Spacer(Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(theme.nameAr, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text(theme.nameEn, fontSize = 11.sp, color = Color.Gray)
                    Text(
                        theme.description,
                        fontSize = 11.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }

                if (isSelected) {
                    Icon(
                        Icons.Default.Check,
                        contentDescription = "محدد",
                        tint = Color(0xFF0D4A30),
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            // Preview bar
            Spacer(Modifier.height(12.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(36.dp)
                    .background(theme.primary, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .background(theme.secondary, CircleShape)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        "معاينة الثيم",
                        color = theme.onPrimary,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.weight(1f))
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .background(theme.accent, CircleShape)
                    )
                }
            }
        }
    }
}
