package com.example

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ZakatCalculatorView(onBack: () -> Unit) {
    var cashAmount by remember { mutableStateOf("0") }
    var goldAmount by remember { mutableStateOf("0") } // بالغرام
    var silverAmount by remember { mutableStateOf("0") } // بالغرام
    var stocksAmount by remember { mutableStateOf("0") }
    var businessAmount by remember { mutableStateOf("0") }
    var debtsAmount by remember { mutableStateOf("0") }

    // Nisab 2026 (approximate)
    val goldNisabGrams = 85f  // 85 grams of gold
    val silverNisabGrams = 595f  // 595 grams of silver
    val goldPricePerGram = 8500f // DZD per gram (approximate)
    val silverPricePerGram = 110f // DZD per gram (approximate)

    val goldValue = (goldAmount.toFloatOrNull() ?: 0f) * goldPricePerGram
    val silverValue = (silverAmount.toFloatOrNull() ?: 0f) * silverPricePerGram
    val cashValue = cashAmount.toFloatOrNull() ?: 0f
    val stocksValue = stocksAmount.toFloatOrNull() ?: 0f
    val businessValue = businessAmount.toFloatOrNull() ?: 0f
    val debtsValue = debtsAmount.toFloatOrNull() ?: 0f

    val totalAssets = goldValue + silverValue + cashValue + stocksValue + businessValue
    val netAssets = totalAssets - debtsValue

    // Nisab is the lower of gold or silver value
    val goldNisabValue = goldNisabGrams * goldPricePerGram
    val silverNisabValue = silverNisabGrams * silverPricePerGram
    val nisab = minOf(goldNisabValue, silverNisabValue)

    val meetsNisab = netAssets >= nisab
    val zakat = if (meetsNisab) netAssets * 0.025f else 0f

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("حاسبة الزكاة", fontWeight = FontWeight.Bold, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, "رجوع", tint = Color.White)
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
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {
            // Header card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF0D4A30))
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Default.Calculate,
                        contentDescription = null,
                        tint = Color(0xFFC9A84C),
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "حاسبة الزكاة الشرعية",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        "نصاب الزكاة: ${nisab.toLong()} د.ج",
                        fontSize = 14.sp,
                        color = Color(0xFFC9A84C),
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    Text(
                        "(يعتمد على الفضة لتيسير الفقراء)",
                        fontSize = 11.sp,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            // Input fields
            ZakatInputField("💰 النقود (د.ج)", cashAmount) { cashAmount = it }
            ZakatInputField("🥇 الذهب (غرام)", goldAmount) { goldAmount = it }
            ZakatInputField("🥈 الفضة (غرام)", silverAmount) { silverAmount = it }
            ZakatInputField("📈 الأسهم/الاستثمارات (د.ج)", stocksAmount) { stocksAmount = it }
            ZakatInputField("🏪 بضاعة التجارة (د.ج)", businessAmount) { businessAmount = it }
            ZakatInputField("💳 الديون المستحقة عليك (د.ج)", debtsAmount) { debtsAmount = it }

            Spacer(Modifier.height(20.dp))

            // Result
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = if (meetsNisab) Color(0xFF0D4A30) else Color.Gray.copy(alpha = 0.2f)
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        "النتيجة:",
                        fontSize = 16.sp,
                        color = if (meetsNisab) Color.White else MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(Modifier.height(8.dp))

                    Text(
                        "إجمالي الأصول: ${totalAssets.toLong()} د.ج",
                        fontSize = 14.sp,
                        color = if (meetsNisab) Color.White.copy(alpha = 0.8f) else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                    Text(
                        "صافي الأصول: ${netAssets.toLong()} د.ج",
                        fontSize = 14.sp,
                        color = if (meetsNisab) Color.White.copy(alpha = 0.8f) else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                    Spacer(Modifier.height(12.dp))

                    if (meetsNisab) {
                        Text(
                            "✅ زكاتك الواجبة:",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            "${zakat.toLong()} د.ج",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFC9A84C)
                        )
                        Text(
                            "(2.5% من صافي أموالك)",
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.7f),
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    } else {
                        Text(
                            "❌ لم يبلغ أموالك النصاب",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            "النصاب الحالي: ${nisab.toLong()} د.ج\nينقصك: ${(nisab - netAssets).toLong()} د.ج",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            // Fiqh notes
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF8E1))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "📖 أحكام الزكاة:",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color(0xFF6D4C00)
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "• الزكاة 2.5% على من ملك النصاب\n" +
                        "• النصاب يُحسب بالفضة أو الذهب (أيهما أقل)\n" +
                        "• تُزكى عن سنة كاملة (الحول)\n" +
                        "• تُخرج لـ 8 مصارف (الأصناف الثمانية)",
                        fontSize = 12.sp,
                        color = Color(0xFF6D4C00)
                    )
                }
            }
        }
    }
}

@Composable
fun ZakatInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true
    )
}
