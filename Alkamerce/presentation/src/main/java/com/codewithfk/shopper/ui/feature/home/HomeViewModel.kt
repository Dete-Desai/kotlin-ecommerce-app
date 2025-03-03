package com.codewithfk.shopper.ui.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codewithfk.domain.model.Product
import com.codewithfk.domain.network.ResultWrapper
import com.codewithfk.domain.usecase.GetCategoriesUseCase
import com.codewithfk.domain.usecase.GetProductUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getProductUseCase: GetProductUseCase,
    private val categoryUseCase: GetCategoriesUseCase
) : ViewModel() {

    // State flow to manage UI state
    private val _uiState = MutableStateFlow<HomeScreenUIEvents>(HomeScreenUIEvents.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getAllProducts()
    }

    private fun getAllProducts() {
        viewModelScope.launch {
            _uiState.value = HomeScreenUIEvents.Loading

            // Launch all requests in parallel using `async`
            val featured = async {
                getProducts()
            }
            val popularProducts = async {
                getProducts()
            }
            val categories = async {
                getCategory()
            }

            // Wait for all requests to complete
            val featuredResponse = featured.await()
            val popularProductsResponse = popularProducts.await()
            val categoriesResponse = categories.await()

            // Check if responses are valid
            if (featuredResponse.isEmpty() && popularProductsResponse.isEmpty() && categoriesResponse.isNotEmpty()) {
                _uiState.value = HomeScreenUIEvents.Error("Failed to load products")
                return@launch
            }

            // Update UI state with the fetched data
            _uiState.value = HomeScreenUIEvents.Success(
                featuredResponse,
                popularProductsResponse,
                categoriesResponse
            )
        }
    }

    private suspend fun getCategory(): List<String> {
        return when (val result = categoryUseCase.execute()) {
            is ResultWrapper.Success -> result.value.categories.map { it.title }
            is ResultWrapper.Failure -> emptyList()
        }
    }

    private suspend fun getProducts(): List<Product> {
        return when (val result = getProductUseCase.execute()) {
            is ResultWrapper.Success -> result.value.products
            is ResultWrapper.Failure -> emptyList()
        }
    }
}

// Sealed class to represent UI events
sealed class HomeScreenUIEvents {
    data object Loading : HomeScreenUIEvents()
    data class Success(
        val featured: List<Product>,
        val popularProducts: List<Product>,
        val categories: List<String>
    ) : HomeScreenUIEvents()

    data class Error(val message: String) : HomeScreenUIEvents()
}