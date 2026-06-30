package com.example

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * شاشة الترحيب - تظهر أول مرة فقط
 * تشرح للمستخدم الميزات الأساسية للتطبيق
 */

data class OnboardingPage(
    val icon: ImageVector,
    val title: String,
    val description: String,
    val color: Color
)

@Composable
fun OnboardingView(onFinish: () -> Unit) {
    val pages = listOf(
        OnboardingPage(
            icon = Icons.Default.AutoAwesome,
            title = "مرحبًا بك في نور الإسلام",
            description = "تطبيقك الإسلامي الشامل للصلاة والقرآن والأذكار. صُمم بحب لخدمة المسلمين في الجزائر والعالم.",
            color = Color(0xFF0D4A30)
        ),
        OnboardingPage(
            icon = Icons.Default.LocationOn,
            title = "مواقيت الصلاة بـ GPS",
            description = "يحسب التطبيق مواقيت الصلاة تلقائيًا بناءً على موقعك الجغرافي عبر GPS، مع تنبيه قبل 10 دقائق من كل صلاة.",
            color = Color(0xFF1B5E20)
        ),
        OnboardingPage(
            icon = Icons.Default.Notifications,
            title = "إشعارات ذكية وتنبيهات",
            description = "عداد تنازلي حي للصلاة القادمة، أذان كامل، أذكار ما بعد الصلاة، تذكيرات يومية — كلها في تطبيق واحد.",
            color = Color(0xFFC9A84C)
        ),
        OnboardingPage(
            icon = Icons.Default.Star,
            title = "ميزات إسلامية كاملة",
            description = "القرآن الكريم كاملاً، التفسير، 365 حديث نبوي، صحيح البخاري ومسلم، الأذكار، حاسبة الزكاة، اتجاه القبلة، رمضان، والسبحة الإلكترونية.",
            color = Color(0xFF8B5A3C)
        )
    )

    var currentPage by remember { mutableStateOf(0) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                if (currentPage < pages.size - 1) {
                    TextButton(onClick = onFinish) {
                        Text("تخطي", color = Color.Gray)
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            // Content
            val page = pages[currentPage]
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(140.dp)
                        .background(
                            color = page.color.copy(alpha = 0.1f),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        page.icon,
                        contentDescription = null,
                        modifier = Modifier.size(80.dp),
                        tint = page.color
                    )
                }

                Spacer(Modifier.height(32.dp))

                Text(
                    page.title,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(20.dp))

                Text(
                    page.description,
                    fontSize = 16.sp,
                    lineHeight = 26.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            // Page indicators
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                pages.forEachIndexed { index, _ ->
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .size(if (currentPage == index) 12.dp else 8.dp)
                            .background(
                                color = if (currentPage == index) page.color else Color.LightGray,
                                shape = CircleShape
                            )
                    )
                }
            }

            // Navigation buttons
            Button(
                onClick = {
                    if (currentPage < pages.size - 1) {
                        currentPage++
                    } else {
                        onFinish()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = page.color)
            ) {
                Text(
                    if (currentPage < pages.size - 1) "التالي" else "ابدأ التطبيق",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}
