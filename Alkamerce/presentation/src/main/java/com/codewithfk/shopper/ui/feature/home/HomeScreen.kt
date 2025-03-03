package com.codewithfk.shopper.ui.feature.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.expandVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.codewithfk.domain.model.Product
import com.codewithfk.shopper.R
import com.codewithfk.shopper.model.UiProductModel
import com.codewithfk.shopper.navigation.CartScreen
import com.codewithfk.shopper.navigation.ProductDetails
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = koinViewModel()) {
    val uiState = viewModel.uiState.collectAsState()

    Scaffold { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .testTag("homeScreen")
        ) {
            when (val state = uiState.value) {
                is HomeScreenUIEvents.Loading -> {
                    HomeContent(
                        featured = emptyList(),
                        popularProducts = emptyList(),
                        categories = emptyList(),
                        isLoading = true,
                        errorMsg = null,
                        onClick = {
                            navController.navigate(ProductDetails(UiProductModel.fromProduct(it)))
                        },
                        onCartClicked = {
                            navController.navigate(CartScreen)
                        }
                    )
                }

                is HomeScreenUIEvents.Success -> {
                    val data = state
                    HomeContent(
                        featured = data.featured,
                        popularProducts = data.popularProducts,
                        categories = data.categories,
                        isLoading = false,
                        errorMsg = null,
                        onClick = {
                            navController.navigate(ProductDetails(UiProductModel.fromProduct(it)))
                        },
                        onCartClicked = {
                            navController.navigate(CartScreen)
                        }
                    )
                }

                is HomeScreenUIEvents.Error -> {
                    val errorMsg = state.message
                    HomeContent(
                        featured = emptyList(),
                        popularProducts = emptyList(),
                        categories = emptyList(),
                        isLoading = false,
                        errorMsg = errorMsg,
                        onClick = {
                            navController.navigate(ProductDetails(UiProductModel.fromProduct(it)))
                        },
                        onCartClicked = {
                            navController.navigate(CartScreen)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileHeader(onCartClicked: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_profile),
                contentDescription = null,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = "Hello,", style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "John Doe",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
        Row(
            modifier = Modifier
                .align(Alignment.CenterEnd)
        ) {
            Image(
                painter = painterResource(id = R.drawable.notificatino),
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray.copy(alpha = 0.3f))
                    .padding(8.dp),
                contentScale = ContentScale.Inside
            )
            Image(
                painter = painterResource(id = R.drawable.ic_cart),
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray.copy(alpha = 0.3f))
                    .padding(8.dp)
                    .clickable {
                        onCartClicked()
                    },
                contentScale = ContentScale.Inside
            )
        }
    }
}

@Composable
fun HomeContent(
    featured: List<Product>,
    popularProducts: List<Product>,
    categories: List<String>,
    isLoading: Boolean = false,
    errorMsg: String? = null,
    onClick: (Product) -> Unit,
    onCartClicked: () -> Unit
) {
    LazyColumn {
        item {
            ProfileHeader(onCartClicked)
            Spacer(modifier = Modifier.size(16.dp))
            SearchBar(value = "", onTextChanged = {})
            Spacer(modifier = Modifier.size(16.dp))
        }
        item {
            if (isLoading) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(50.dp))
                    Text(text = "Loading...", style = MaterialTheme.typography.bodyMedium)
                }
            }
            errorMsg?.let {
                Text(text = it, style = MaterialTheme.typography.bodyMedium)
            }
            if (categories.isNotEmpty()) {
                LazyRow {
                    items(categories, key = { it }) { category ->
                        val isVisible = remember {
                            mutableStateOf(false)
                        }
                        LaunchedEffect(true) {
                            isVisible.value = true
                        }
                        AnimatedVisibility(
                            visible = isVisible.value, enter = fadeIn() + expandVertically()
                        ) {
                            Text(
                                text = category.replaceFirstChar { it.uppercase() },
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier
                                    .padding(horizontal = 8.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(MaterialTheme.colorScheme.primary)
                                    .padding(8.dp)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.size(16.dp))
            }
            if (featured.isNotEmpty()) {
                HomeProductRow(products = featured, title = "Featured", onClick = onClick)
                Spacer(modifier = Modifier.size(16.dp))
            }
            if (popularProducts.isNotEmpty()) {
                HomeProductRow(
                    products = popularProducts,
                    title = "Popular Products",
                    onClick = onClick
                )
            }
        }
    }
}

@Composable
fun SearchBar(value: String, onTextChanged: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = onTextChanged,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(32.dp),
        singleLine = true,
        maxLines = 1,
        leadingIcon = {
            Image(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedContainerColor = Color.LightGray.copy(alpha = 0.3f),
            unfocusedContainerColor = Color.LightGray.copy(alpha = 0.3f),
        ),
        placeholder = {
            Text(
                text = "Search for products", style = MaterialTheme.typography.bodySmall
            )
        }
    )
}

@Composable
fun HomeProductRow(products: List<Product>, title: String, onClick: (Product) -> Unit) {
    Column {
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.CenterStart),
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "View all",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }
        Spacer(modifier = Modifier.size(8.dp))
        LazyRow {
            items(products, key = { it.id }) { product ->
                val isVisible = remember {
                    mutableStateOf(false)
                }
                LaunchedEffect(true) {
                    isVisible.value = true
                }
                AnimatedVisibility(
                    visible = isVisible.value, enter = fadeIn() + expandVertically()
                ) {
                    ProductItem(product = product, onClick)
                }
            }
        }
    }
}

@Composable
fun ProductItem(product: Product, onClick: (Product) -> Unit) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .size(width = 126.dp, height = 144.dp)
            .clickable { onClick(product) },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.LightGray.copy(alpha = 0.3f))
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = product.images.firstOrNull() ?: "",
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(96.dp)
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = product.title,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 8.dp),
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "$${product.price}",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(horizontal = 8.dp),
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}