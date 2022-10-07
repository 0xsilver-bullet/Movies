package com.silverbullet.moviesapp.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.silverbullet.moviesapp.domain.model.Category

@Composable
fun CategorySelector(
    modifier: Modifier = Modifier,
    notSelectedTextColor: Color = Color.White,
    notSelectedBackgroundColor: Color = Color.Transparent,
    selectedTextColor: Color,
    selectedBackgroundColor: Color,
    categories: List<Category>,
    onCategorySelected: (Int) -> Unit = {} // calls lambda function with id of category
) {
    var selectedIndex by remember {
        mutableStateOf(0)
    }
    Column(modifier = modifier) {
        Text(
            text = "Categories",
            style = MaterialTheme.typography.h4,
            color = Color.White,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow(
            modifier = Modifier.padding(horizontal = 24.dp)
        ) {
            items(categories) { item ->
                val isItemSelected = selectedIndex == categories.indexOf(item)
                Box(
                    modifier = modifier
                        .width(80.dp)
                        .height(30.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isItemSelected) selectedBackgroundColor else notSelectedBackgroundColor)
                        .clickable {
                            selectedIndex = categories.indexOf(item)
                            onCategorySelected(item.id)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = item.name,
                        color = if (isItemSelected) selectedTextColor else notSelectedTextColor,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center,
                        fontSize = 12.sp
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}