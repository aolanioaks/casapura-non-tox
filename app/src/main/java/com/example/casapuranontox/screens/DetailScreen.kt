package com.example.casapuranontox.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.casapuranontox.model.Product
import com.example.casapuranontox.model.ProductViewModel
import com.example.casapuranontox.ui.theme.Brown400
import com.example.casapuranontox.ui.theme.DividerColor
import com.example.casapuranontox.ui.theme.Green700
import com.example.casapuranontox.ui.theme.GreenLight


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DetailScreen(
    product: Product,
    viewModel: ProductViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val isSwapped = product.id in viewModel.swappedIds

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .background(Color(0xFFE8E4D8))
        ) {
            if (product.imageUrl.startsWith("drawable://")) {
                val resName = product.imageUrl.removePrefix("drawable://")
                val resId = LocalContext.current.resources.getIdentifier(
                    resName, "drawable", LocalContext.current.packageName
                )
                Image(
                    painter = painterResource(id = resId),
                    contentDescription = product.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                AsyncImage(
                    model = product.imageUrl,
                    contentDescription = product.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .padding(12.dp)
                    .background(Color.Black.copy(alpha = 0.3f), RoundedCornerShape(50))
            ) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        }

        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = product.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "${product.brand}  ·  ${product.subcategory}",
                fontSize = 12.sp,
                color = Brown400
            )

            Spacer(Modifier.height(12.dp))

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement   = Arrangement.spacedBy(6.dp)
            ) {
                product.certifications.forEach { cert ->
                    Box(
                        modifier = Modifier
                            .background(GreenLight, RoundedCornerShape(20.dp))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = cert,
                            fontSize = 11.sp,
                            color = Green700
                        )
                    }
                }
            }

            Spacer(Modifier.height(16.dp))
            Divider(color = DividerColor, thickness = 0.5.dp)
            Spacer(Modifier.height(14.dp))

            Text(
                text = "WHY IT'S CLEAN",
                fontSize = 11.sp,
                color = Brown400,
                letterSpacing = 1.5.sp
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = product.whyItsClean,
                fontSize = 14.sp
            )

            Spacer(Modifier.height(14.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color(0xFFF5F0E8),
                        RoundedCornerShape(10.dp)
                    )
                    .padding(14.dp)
            ) {
                Text(
                    text = "REPLACES",
                    fontSize = 11.sp,
                    color = Brown400,
                    letterSpacing = 1.5.sp
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = product.replaces,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(Modifier.height(24.dp))

            Button(
                onClick  = { viewModel.toggleSwap(product) },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape    = RoundedCornerShape(10.dp),
                colors   = ButtonDefaults.buttonColors(
                    containerColor = if (isSwapped) GreenLight else Green700,
                    contentColor   = if (isSwapped) Green700 else Color.White
                )
            ) {
                if (isSwapped) {
                    Icon(Icons.Outlined.Check,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(
                        Modifier.width(8.dp)
                    )
                    Text("SWAPPED",
                        letterSpacing = 2.sp,
                        fontWeight = FontWeight.Medium
                    )
                } else {
                    Text("MARK AS SWAPPED",
                        letterSpacing = 2.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(Modifier.height(10.dp))

            OutlinedButton(
                onClick  = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(product.productUrl))
                    context.startActivity(intent)
                },
                modifier = Modifier.fillMaxWidth().height(46.dp),
                shape    = RoundedCornerShape(10.dp),
                border   = androidx.compose.foundation.BorderStroke(0.5.dp, DividerColor)
            ) {
                Text("VIEW PRODUCT", letterSpacing = 2.sp, color = Brown400, fontSize = 12.sp)
            }

            Spacer(Modifier.height(32.dp))
        }
    }
}
