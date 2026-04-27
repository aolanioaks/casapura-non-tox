package com.example.casapuranontox.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.casapuranontox.model.ProductViewModel
import com.example.casapuranontox.ui.theme.Brown400
import com.example.casapuranontox.ui.theme.Brown900
import com.example.casapuranontox.ui.theme.Green700

@Composable
fun HomeScreen(
    viewModel: ProductViewModel,
    onCategoryClick: (String) -> Unit,
    onProductClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Brown900)
                    .padding(vertical = 36.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "CASA PURA",
                        color = Color(0xFFD4C4A8),
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Normal,
                        letterSpacing = 8.sp
                    )
                    Text(
                        text = "LIVING",
                        color = Color(0xFFD4C4A8),
                        fontSize = 13.sp,
                        letterSpacing = 10.sp
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "NON-TOXIC HOME GUIDE",
                        color = Color(0xFF7A7060),
                        fontSize = 10.sp,
                        letterSpacing = 2.sp
                    )
                    if (viewModel.products.isNotEmpty()) {
                        Spacer(Modifier.height(14.dp))
                        Text(
                            text = "${viewModel.getSwapCount()} of ${viewModel.products.size} products swapped",
                            color = Color(0xFF8AAA8A),
                            fontSize = 11.sp
                        )
                    }
                }
            }
        }

        item {
            Column(modifier = Modifier.padding(16.dp)) {
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "BROWSE BY ROOM",
                    fontSize = 11.sp,
                    color = Brown400,
                    letterSpacing = 2.sp
                )
                Spacer(Modifier.height(10.dp))

                val categories = listOf(
                    Pair("Water",    "water"),
                    Pair("Kitchen",  "kitchen"),
                    Pair("Bathroom", "bathroom"),
                    Pair("Bedroom",  "bedroom"),
                    Pair("Laundry",  "laundry"),
                    Pair("Clothing", "clothing")
                )

                categories.chunked(2).forEach { row ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(bottom = 8.dp)
                    ) {
                        row.forEach { (label, key) ->
                            val count = viewModel.products.count { it.category == key }
                            Card(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { onCategoryClick(key) },
                                shape = RoundedCornerShape(10.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Column {
                                        Text(
                                            text = label,
                                            fontWeight = FontWeight.Medium,
                                            fontSize = 13.sp
                                        )
                                        Text(
                                            text = "$count products",
                                            fontSize = 11.sp,
                                            color = Brown400
                                        )
                                    }
                                }
                            }
                        }
                        if (row.size == 1) Spacer(Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
fun ProductRow(
    name: String,
    brand: String,
    isSwapped: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = name, fontWeight = FontWeight.Medium, fontSize = 14.sp)
            Text(text = brand, fontSize = 12.sp, color = Brown400)
        }
        if (isSwapped) {
            Text(text = "✓", color = Green700, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
    }
}