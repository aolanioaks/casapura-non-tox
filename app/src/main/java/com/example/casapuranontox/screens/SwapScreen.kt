package com.example.casapuranontox.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.casapuranontox.model.ProductViewModel
import com.example.casapuranontox.ui.theme.Brown400
import com.example.casapuranontox.ui.theme.DividerColor
import com.example.casapuranontox.ui.theme.Green700
import com.example.casapuranontox.ui.theme.GreenLight

@Composable
fun SwapScreen(viewModel: ProductViewModel, modifier: Modifier = Modifier) {

    LaunchedEffect(Unit) {
        viewModel.loadSwaps()
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                Text(
                    text = "MY SWAPS",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    letterSpacing = 5.sp
                )
                Text(
                    text = "${viewModel.getSwapCount()} of ${viewModel.products.size} products swapped",
                    fontSize = 12.sp,
                    color = Brown400
                )
            }
            Divider(color = DividerColor, thickness = 0.5.dp)
        }

        item {
            Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)) {
                Text(
                    text = "COMPLETED SWAPS",
                    fontSize = 11.sp,
                    color = Brown400,
                    letterSpacing = 2.sp
                )
                Spacer(Modifier.height(6.dp))
                if (viewModel.swappedIds.isEmpty()) {
                    Text(
                        text = "You don't have any swaps yet. Tap 'Swap' on a product.",
                        fontSize = 13.sp,
                        color = Brown400,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        }

        val swappedProducts = viewModel.products.filter { it.id in viewModel.swappedIds }
        items(swappedProducts.size) { index ->
            val product = swappedProducts[index]
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(22.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(Green700),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Outlined.Check,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(14.dp)
                    )
                }
                Spacer(Modifier.width(12.dp))
                Text(product.name, fontSize = 14.sp, modifier = Modifier.weight(1f))
                Box(
                    modifier = Modifier
                        .background(GreenLight, RoundedCornerShape(20.dp))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        product.category.replaceFirstChar { it.uppercase() },
                        fontSize = 11.sp,
                        color = Green700
                    )
                }
            }
            Divider(
                modifier = Modifier.padding(horizontal = 16.dp),
                color = DividerColor,
                thickness = 0.5.dp
            )
        }

        item { Spacer(Modifier.height(24.dp)) }
    }
}