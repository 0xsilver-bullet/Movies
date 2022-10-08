package com.silverbullet.moviesapp.presentation.movie_details.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.silverbullet.moviesapp.presentation.ui.theme.SoftColor

@Composable
fun MovieDetailsTopSection(
    modifier: Modifier = Modifier,
    @DrawableRes rightIcon: Int,
    rightIconColor: Color,
    @DrawableRes leftIcon: Int,
    title: String,
    titleColor: Color = Color.White,
    backgroundColor: Color = Color.Transparent,
    rightIconCallback: () -> Unit = {},
    leftIconCallback: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .background(backgroundColor)
            .padding(horizontal = 24.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(SoftColor)
                .size(32.dp)
                .clickable { leftIconCallback() },
            contentAlignment = Alignment.Center
        ) {
            Image(painter = painterResource(id = leftIcon), contentDescription = null)
        }
        Text(
            text = title,
            modifier = Modifier
                .weight(0.5f),
            style = MaterialTheme.typography.h4,
            color = titleColor,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis
        )
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(SoftColor)
                .size(32.dp)
                .clickable { rightIconCallback() },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = rightIcon),
                contentDescription = null,
                colorFilter = ColorFilter.tint(rightIconColor)
            )
        }
    }
}