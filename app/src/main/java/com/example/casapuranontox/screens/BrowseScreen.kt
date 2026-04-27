package com.example.casapuranontox.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.casapuranontox.model.ProductViewModel
import com.example.casapuranontox.ui.theme.Brown400
import com.example.casapuranontox.ui.theme.DividerColor
import com.example.casapuranontox.ui.theme.Green700

@Composable
fun BrowseScreen(
    viewModel: ProductViewModel,
    initialCategory: String = "all",
    onProductClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(initialCategory) {
        viewModel.setCategory(initialCategory)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
        ) {
            Text(
                text = "BROWSE",
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal
            )
            Text(
                text = "${viewModel.getFilteredProducts().size} products",
                fontSize = 12.sp,
                color = Brown400
            )
            Spacer(Modifier.height(10.dp))

            val categories = listOf(
                "all"      to "All",
                "water"    to "Water",
                "kitchen"  to "Kitchen",
                "bathroom" to "Bathroom",
                "bedroom"  to "Bedroom",
                "laundry"  to "Laundry",
                "clothing" to "Clothing"
            )

            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                categories.forEach { (key, label) ->
                    FilterChip(
                        selected = viewModel.selectedCategory == key,
                        onClick  = { viewModel.setCategory(key) },
                        label    = { Text(label, fontSize = 12.sp) },
                        shape    = RoundedCornerShape(20.dp),
                        colors   = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Green700,
                            selectedLabelColor     = Color.White,
                            containerColor         = Color.White,
                            labelColor             = Brown400
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled             = true,
                            selected            = viewModel.selectedCategory == key,
                            borderColor         = DividerColor,
                            selectedBorderColor = Green700,
                            borderWidth         = 0.5.dp
                        )
                    )
                }
            }
        }

        Divider(color = DividerColor, thickness = 0.5.dp)

        if (viewModel.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Green700)
            }
        } else {
            val filtered = viewModel.getFilteredProducts()
            LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
                items(filtered.size) { index ->
                    val product = filtered[index]
                    ProductRow(
                        name      = product.name,
                        brand     = product.brand,
                        isSwapped = product.id in viewModel.swappedIds,
                        onClick   = { onProductClick(product.id) }
                    )
                    if (index < filtered.lastIndex) {
                        Divider(color = DividerColor, thickness = 0.5.dp)
                    }
                }
                item { Spacer(Modifier.height(16.dp)) }
            }
        }
    }
}
