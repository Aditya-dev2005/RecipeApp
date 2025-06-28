package eu.tutorials.myrecipeapp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter

@Composable
fun RecipeScreen(modifier: Modifier = Modifier,
                 navigateToDetail: (Category) -> Unit,
                 viewState : MainViewModel.RecipeState) {
    val isDarkMode = isSystemInDarkTheme()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = if (isDarkMode)
                        listOf(Color.Black, Color.DarkGray)
                    else
                        listOf(Color.White, Color.LightGray)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        when {
            viewState.loading -> {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator(color = if (isDarkMode) Color.White else Color(0xFFFF5722))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Fetching Recipes...",
                        fontSize = 16.sp,
                        color = if (isDarkMode) Color.LightGray else Color.Gray
                    )
                }
            }

            viewState.error != null -> {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "Oops! Something went wrong",
                        color = Color.Red,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.SansSerif
                    )
                    Text(
                        "Please try again later.",
                        color = if (isDarkMode) Color.LightGray else Color.Gray,
                        fontSize = 14.sp
                    )
                }
            }

            else -> {
                CategoryScreen(categories = viewState.list, isDarkMode = isDarkMode, navigateToDetail = navigateToDetail)
            }
        }
    }
}

@Composable
fun CategoryScreen(
    categories: List<Category>,
    isDarkMode: Boolean,
    navigateToDetail: (Category) -> Unit,
) {
    LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.padding(8.dp)) {
        items(categories) { category ->
            AnimatedVisibility(
                visible = true,
                enter = fadeIn(animationSpec = tween(500)),
                exit = fadeOut()
            ) {
                CategoryItem(category = category, isDarkMode = isDarkMode, navigateToDetail = navigateToDetail)
            }
        }
    }
}

@Composable
fun CategoryItem(
    category: Category,
    isDarkMode: Boolean,
    navigateToDetail: (Category) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { navigateToDetail(category) },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = if (isDarkMode) Color.DarkGray else Color.White),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = rememberAsyncImagePainter(category.strCategoryThumb),
                contentDescription = category.strCategory,
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = category.strCategory,
                color = if (isDarkMode) Color.White else Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                fontFamily = FontFamily.Serif,
                textAlign = TextAlign.Center
            )
        }
    }
}
