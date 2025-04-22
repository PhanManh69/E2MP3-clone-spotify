package com.emanh.rootapp.presentation.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.emanh.rootapp.presentation.composable.STFHeader
import com.emanh.rootapp.presentation.composable.STFHeaderType

@Composable
fun SearchScreen() {
    STFHeader(userName = "emanh", type = STFHeaderType.HeaderSearch, content = {
        LazyColumn(modifier = it.padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            items(10) { index ->
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(color = if (index == 0) Color.Magenta else Color.Green))
            }
        }
    })
}