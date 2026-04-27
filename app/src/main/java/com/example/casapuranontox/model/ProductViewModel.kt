package com.example.casapuranontox.model

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.casapuranontox.database.AppDatabase
import com.example.casapuranontox.database.SwapRecord
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json



class ProductViewModel(private val context: Context) : ViewModel() {

    var products by mutableStateOf<List<Product>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var swappedIds by mutableStateOf<Set<String>>(emptySet())
        private set

    var selectedCategory by mutableStateOf("all")
        private set

    private val db  = AppDatabase.getDatabase(context)
    private val dao = db.swapDao()

    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }


    private val productsUrl = "https://api.jsonbin.io/v3/b/69ea5eab856a6821896618d2"
    private val masterKey = "\$2a\$10\$x9nVMorZzhkPECjOb4UNtOJyxsA.oh1Gccls5bVGSaymjIOhZBSua"

    init {
        loadProducts()
        loadSwaps()
    }


    fun loadProducts() {
        viewModelScope.launch {
            isLoading = true
            products = try {
                val response: JsonBinResponse = client.get(productsUrl) {
                    header("X-Master-Key", masterKey)
                }.body()
                android.util.Log.d("CasaPura", "Loaded ${response.record.products.size} products")
                response.record.products
            } catch (e: Exception) {
                android.util.Log.e("CasaPura", "Error: ${e::class.simpleName} - ${e.message}")
                emptyList()
            }
            isLoading = false
        }
    }

    fun loadSwaps() {
        viewModelScope.launch {
            swappedIds = dao.getAllSwaps().map { it.productId }.toSet()
        }
    }

    fun toggleSwap(product: Product) {
        viewModelScope.launch {
            if (product.id in swappedIds) {
                dao.deleteSwap(
                    SwapRecord(
                        product.id,
                        product.name,
                        product.category
                    )
                )
            } else {
                dao.insertSwap(
                    SwapRecord(
                        product.id,
                        product.name,
                        product.category
                    )
                )
            }
            loadSwaps()
        }
    }


    fun setCategory(category:String) {
        selectedCategory = category
    }
    fun getFilteredProducts(): List<Product> {
        return if (selectedCategory == "all") products
        else products.filter { it.category == selectedCategory }
    }
    fun getSwapCount(): Int = swappedIds.size

    class Factory(private val context: Context) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ProductViewModel(context) as T
        }
    }
}
