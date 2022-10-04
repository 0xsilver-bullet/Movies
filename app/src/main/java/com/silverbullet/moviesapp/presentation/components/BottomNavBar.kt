package com.silverbullet.moviesapp.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BottomNavBar(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colors.background,
    body: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = modifier.background(backgroundColor),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        this.body()
    }
}

@Composable
fun NavItem(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    unselectedColor: Color,
    selectedColor: Color,
    backgroundSelectedColor: Color,
    name: String,
    route: String,
    isSelected: Boolean = false,
    onSelect: (String) -> Unit = {}
) {
    Box(
        modifier = modifier
            .height(40.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable { onSelect(route) }
            .background(if (isSelected) backgroundSelectedColor else Color.Transparent),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = null,
                colorFilter = ColorFilter.tint(if (isSelected) selectedColor else unselectedColor)
            )
            if (isSelected) {
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = name,
                    color = selectedColor,
                    fontSize = 12.sp
                )
            }
        }
    }
}