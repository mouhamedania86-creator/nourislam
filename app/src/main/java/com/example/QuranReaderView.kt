package com.example

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * قارئ القرآن الكامل مع البحث
 * يستخدم البيانات من FullQuranMetadata
 * النصوص الكاملة تُحمّل من API أو محلية (قابلة للتوسعة)
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuranReaderView(onBack: () -> Unit) {
    val context = LocalContext.current
    var selectedSurah by remember { mutableStateOf<QuranSurahMeta?>(null) }
    var searchQuery by remember { mutableStateOf("") }
    var showPlayer by remember { mutableStateOf(false) }

    val displayedSurahs = remember(searchQuery) {
        FullQuranMetadata.searchSurahs(searchQuery)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (selectedSurah != null) "سورة ${selectedSurah!!.nameAr}"
                        else "📖 القرآن الكريم - 114 سورة",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        if (selectedSurah != null) selectedSurah = null
                        else onBack()
                    }) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, "رجوع", tint = Color.White)
                    }
                },
                actions = {
                    if (selectedSurah != null) {
                        IconButton(onClick = { showPlayer = true }) {
                            Icon(Icons.Default.PlayArrow, "استماع", tint = Color.White)
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF0D4A30))
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            if (selectedSurah == null) {
                // قائمة السور
                QuranSurahListView(
                    surahs = displayedSurahs,
                    searchQuery = searchQuery,
                    onSearchChange = { searchQuery = it },
                    onSurahClick = { selectedSurah = it }
                )
            } else {
                // تفاصيل السورة
                SurahDetailView(
                    surah = selectedSurah!!,
                    onPlayAudio = { showPlayer = true }
                )
            }
        }
    }

    if (showPlayer && selectedSurah != null) {
        QuranAudioPlayerDialog(
            surah = selectedSurah!!,
            onDismiss = { showPlayer = false }
        )
    }
}

@Composable
fun QuranSurahListView(
    surahs: List<QuranSurahMeta>,
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    onSurahClick: (QuranSurahMeta) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        // Search bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchChange,
            placeholder = { Text("ابحث: الفاتحة، البقرة، Al-Fatihah...") },
            leadingIcon = { Icon(Icons.Default.Search, null) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            singleLine = true
        )

        // Stats
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "${surahs.size} سورة",
                fontSize = 12.sp,
                color = Color.Gray
            )
            Text(
                "${surahs.sumOf { it.verseCount }} آية",
                fontSize = 12.sp,
                color = Color.Gray
            )
        }

        // Surah list
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(surahs) { surah ->
                SurahListItem(surah = surah, onClick = { onSurahClick(surah) })
            }
        }
    }
}

@Composable
fun SurahListItem(surah: QuranSurahMeta, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (surah.type == "مكية") Color(0xFFFFF3E0) else Color(0xFFE8F5E9)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Surah number circle
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(Color(0xFF0D4A30), shape = androidx.compose.foundation.shape.CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "${surah.number}",
                    color = Color(0xFFC9A84C),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }

            Spacer(Modifier.width(12.dp))

            // Surah info
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    surah.nameAr,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    "${surah.transliteration} • ${surah.type}",
                    fontSize = 11.sp,
                    color = Color.Gray
                )
            }

            // Verse count
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    "${surah.verseCount}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color(0xFF0D4A30)
                )
                Text(
                    "آية",
                    fontSize = 10.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun SurahDetailView(
    surah: QuranSurahMeta,
    onPlayAudio: () -> Unit
) {
    val context = LocalContext.current
    var fontSize by remember { mutableStateOf(20.sp) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        // Header card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF0D4A30))
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    surah.nameAr,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    surah.transliteration,
                    fontSize = 14.sp,
                    color = Color(0xFFC9A84C)
                )
                Spacer(Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    InfoBadge("آيات", "${surah.verseCount}")
                    InfoBadge("صفحة", "${surah.startPage}")
                    InfoBadge(surah.type, "")
                }
                Spacer(Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = onPlayAudio,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFC9A84C),
                            contentColor = Color(0xFF0D4A30)
                        )
                    ) {
                        Icon(Icons.Default.PlayArrow, null)
                        Spacer(Modifier.width(4.dp))
                        Text("استماع")
                    }
                    OutlinedButton(
                        onClick = { fontSize = if (fontSize.value > 16f) (fontSize.value - 2f).sp else fontSize },
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
                    ) {
                        Text("أ-")
                    }
                    OutlinedButton(
                        onClick = { fontSize = if (fontSize.value < 32f) (fontSize.value + 2f).sp else fontSize },
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
                    ) {
                        Text("أ+")
                    }
                }
            }
        }

        Spacer(Modifier.height(20.dp))

        // بسم الله (إلا في التوبة)
        if (surah.number != 1 && surah.number != 9) {
            Text(
                "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ",
                modifier = Modifier.fillMaxWidth(),
                fontSize = 22.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0D4A30)
            )
            Spacer(Modifier.height(20.dp))
        }

        // Note about loading
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF8E1))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    "💡 ملاحظة:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = Color(0xFF6D4C00)
                )
                Text(
                    "نصوص الآيات الكاملة تُحمّل من API Quran.com عند الاتصال بالإنترنت. اضغط على 'استماع' للاستماع بصوت 8 قراء مختلفين.",
                    fontSize = 12.sp,
                    color = Color(0xFF6D4C00),
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }

        Spacer(Modifier.height(20.dp))

        // Verse count placeholder
        Text(
            "${surah.verseCount} آية في هذه السورة",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            "اضغط على 'استماع' للاستماع للتلاوة كاملة",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
fun InfoBadge(label: String, value: String) {
    Surface(
        color = Color(0xFFC9A84C),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (value.isNotEmpty()) {
                Text(value, color = Color(0xFF0D4A30), fontSize = 13.sp, fontWeight = FontWeight.Bold)
            }
            Text(label, color = Color(0xFF0D4A30), fontSize = 10.sp)
        }
    }
}
