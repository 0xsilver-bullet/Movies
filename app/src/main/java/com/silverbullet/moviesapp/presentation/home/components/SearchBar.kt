package com.silverbullet.moviesapp.presentation.home.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    hint: String = "",
    hintColor: Color = Color.Gray,
    backgroundColor: Color = Color.Gray,
    @DrawableRes icon: Int,
    onSearch: (String) -> Unit = {}
) {
    var searchText by remember {
        mutableStateOf("")
    }
    var shouldDisplayHint by remember {
        mutableStateOf(true)
    }
    Box(
        modifier = modifier
    ) {
        BasicTextField(
            value = searchText,
            onValueChange = {
                searchText = it
                onSearch(it)
            },
            textStyle = MaterialTheme.typography.h5.copy(color = Color.White),
            maxLines = 1,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(backgroundColor)
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .onFocusChanged { focusState ->
                    shouldDisplayHint = !focusState.isFocused && searchText.isBlank()
                }
        )
        if (shouldDisplayHint) {
            Row(modifier = Modifier.padding(horizontal = 10.dp, vertical = 12.dp)) {
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    modifier = Modifier.scale(0.8f),
                    colorFilter = ColorFilter.tint(hintColor)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = hint,
                    style = MaterialTheme.typography.h5,
                    color = hintColor
                )
            }
        }
    }
}