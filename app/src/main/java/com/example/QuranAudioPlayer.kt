package com.example

import android.media.AudioAttributes
import android.media.MediaPlayer
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
 * مشغل القرآن الصوتي
 * يدعم 8 قراء مع streaming
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuranAudioPlayerDialog(
    surah: QuranSurahMeta,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var selectedReciter by remember { mutableStateOf(QuranReciters.reciters.first()) }
    var isPlaying by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    val mediaPlayer = remember { mutableStateOf<MediaPlayer?>(null) }

    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer.value?.release()
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Column {
                Text("🎵 استماع: سورة ${surah.nameAr}", fontWeight = FontWeight.Bold)
                Text(
                    "${surah.verseCount} آية • صفحة ${surah.startPage}",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text("اختر القارئ:", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Spacer(Modifier.height(8.dp))

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 280.dp)
                ) {
                    items(QuranReciters.reciters) { reciter ->
                        ReciterItem(
                            reciter = reciter,
                            selected = reciter.id == selectedReciter.id,
                            onClick = { selectedReciter = reciter }
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                // Play button
                Button(
                    onClick = {
                        if (isPlaying) {
                            mediaPlayer.value?.pause()
                            isPlaying = false
                        } else {
                            isLoading = true
                            try {
                                mediaPlayer.value?.release()
                                val player = MediaPlayer().apply {
                                    setAudioAttributes(
                                        AudioAttributes.Builder()
                                            .setUsage(AudioAttributes.USAGE_MEDIA)
                                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                            .build()
                                    )
                                    val url = QuranReciters.getSurahAudioUrl(selectedReciter.id, surah.number)
                                    setDataSource(url)
                                    setOnPreparedListener {
                                        start()
                                        isLoading = false
                                        isPlaying = true
                                    }
                                    setOnCompletionListener {
                                        isPlaying = false
                                    }
                                    setOnErrorListener { _, _, _ ->
                                        isLoading = false
                                        isPlaying = false
                                        true
                                    }
                                    prepareAsync()
                                }
                                mediaPlayer.value = player
                            } catch (e: Exception) {
                                isLoading = false
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF0D4A30),
                        contentColor = Color.White
                    ),
                    enabled = !isLoading
                ) {
                    Icon(
                        when {
                            isLoading -> Icons.Default.Refresh
                            isPlaying -> Icons.Default.Pause
                            else -> Icons.Default.PlayArrow
                        },
                        contentDescription = null
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        when {
                            isLoading -> "جاري التحميل..."
                            isPlaying -> "إيقاف مؤقت"
                            else -> "تشغيل التلاوة"
                        }
                    )
                }

                Spacer(Modifier.height(8.dp))

                Text(
                    "ملاحظة: يحتاج اتصال بالإنترنت",
                    fontSize = 11.sp,
                    color = Color.Gray,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                mediaPlayer.value?.release()
                onDismiss()
            }) {
                Text("إغلاق")
            }
        }
    )
}

@Composable
fun ReciterItem(
    reciter: QuranReciter,
    selected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (selected) Color(0xFFE8F5E9) else Color.Transparent
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(
                        if (selected) Color(0xFF0D4A30) else Color.Gray.copy(alpha = 0.2f),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Person,
                    contentDescription = null,
                    tint = if (selected) Color(0xFFC9A84C) else Color.Gray
                )
            }
            Spacer(Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(reciter.nameAr, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Text(
                    "${reciter.nameEn} • ${reciter.style} • ${reciter.bitrate}",
                    fontSize = 11.sp,
                    color = Color.Gray
                )
            }
            if (selected) {
                Icon(
                    Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = Color(0xFF0D4A30)
                )
            }
        }
    }
}
