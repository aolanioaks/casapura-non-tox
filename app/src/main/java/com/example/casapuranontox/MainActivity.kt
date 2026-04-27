package com.example.casapuranontox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.casapuranontox.model.ProductViewModel
import com.example.casapuranontox.screens.BrowseScreen
import com.example.casapuranontox.screens.DetailScreen
import com.example.casapuranontox.screens.HomeScreen
import com.example.casapuranontox.screens.SwapScreen
import com.example.casapuranontox.ui.theme.CasapuranontoxTheme
import com.example.casapuranontox.ui.theme.Green700



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            CasapuranontoxTheme {
                val viewModel: ProductViewModel = viewModel(
                    factory = ProductViewModel.Factory(applicationContext)
                )

                var currentScreen     by remember { mutableStateOf("home") }
                var selectedCategory  by remember { mutableStateOf("all") }
                var selectedProductId by remember { mutableStateOf("") }

                val showBottomNavigation = currentScreen != "detail"

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (showBottomNavigation) {
                            NavigationBar(containerColor = Color.White) {
                                NavigationBarItem(
                                    selected = currentScreen == "home",
                                    onClick  = { currentScreen = "home" },
                                    icon     = { Icon(Icons.Outlined.Home, contentDescription = "Home") },
                                    label    = { Text("Home") },
                                    colors   = NavigationBarItemDefaults.colors(
                                        selectedIconColor   = Green700,
                                        selectedTextColor   = Green700,
                                        indicatorColor      = Color(0xFFE8F0E4),
                                        unselectedIconColor = Color(0xFF9A9080),
                                        unselectedTextColor = Color(0xFF9A9080)
                                    )
                                )
                                NavigationBarItem(
                                    selected = currentScreen == "browse",
                                    onClick  = { currentScreen = "browse" },
                                    icon     = { Icon(Icons.Outlined.Search, contentDescription = "Browse") },
                                    label    = { Text("Browse") },
                                    colors   = NavigationBarItemDefaults.colors(
                                        selectedIconColor   = Green700,
                                        selectedTextColor   = Green700,
                                        indicatorColor      = Color(0xFFE8F0E4),
                                        unselectedIconColor = Color(0xFF9A9080),
                                        unselectedTextColor = Color(0xFF9A9080)
                                    )
                                )
                                NavigationBarItem(
                                    selected = currentScreen == "swaps",
                                    onClick  = { currentScreen = "swaps" },
                                    icon     = { Icon(Icons.Outlined.CheckCircle, contentDescription = "Swaps") },
                                    label    = { Text("Swaps") },
                                    colors   = NavigationBarItemDefaults.colors(
                                        selectedIconColor   = Green700,
                                        selectedTextColor   = Green700,
                                        indicatorColor      = Color(0xFFE8F0E4),
                                        unselectedIconColor = Color(0xFF9A9080),
                                        unselectedTextColor = Color(0xFF9A9080)
                                    )
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    when (currentScreen) {
                        "home" -> HomeScreen(
                            viewModel       = viewModel,
                            onCategoryClick = { category ->
                                selectedCategory = category
                                currentScreen    = "browse"
                            },
                            onProductClick  = { productId ->
                                selectedProductId = productId
                                currentScreen     = "detail"
                            },
                            modifier = Modifier.padding(innerPadding)
                        )
                        "browse" -> BrowseScreen(
                            viewModel       = viewModel,
                            initialCategory = selectedCategory,
                            onProductClick  = { productId ->
                                selectedProductId = productId
                                currentScreen     = "detail"
                            },
                            modifier = Modifier.padding(innerPadding)
                        )
                        "swaps" -> SwapScreen(
                            viewModel = viewModel,
                            modifier  = Modifier.padding(innerPadding)
                        )
                        "detail" -> {
                            val product = viewModel.products.find { it.id == selectedProductId }
                            if (product != null) {
                                DetailScreen(
                                    product   = product,
                                    viewModel = viewModel,
                                    onBack    = { currentScreen = "browse" },
                                    modifier  = Modifier.padding(innerPadding)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}