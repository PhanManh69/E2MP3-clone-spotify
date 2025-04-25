package com.emanh.rootapp.presentation.composable.navbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.emanh.rootapp.presentation.composable.STFTabIcon
import com.emanh.rootapp.presentation.composable.STFTabTitle
import com.emanh.rootapp.presentation.composable.utils.debounceClickable
import com.emanh.rootapp.presentation.theme.E2MP3Theme
import com.emanh.rootapp.presentation.theme.GradientTabbar

@Composable
fun STFTabbar(modifier: Modifier = Modifier, navController: NavController) {
    val buttonItems = remember {
        listOf(STFNavButtonItem.Home, STFNavButtonItem.Search, STFNavButtonItem.YourLibrary)
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Column(modifier = modifier
        .background(GradientTabbar)
        .debounceClickable(indication = null) { }) {
        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            buttonItems.forEach { item ->
                val isSelected = currentDestination?.hierarchy?.any {
                    it.route == item.route::class.qualifiedName
                } == true
                STFTabIcon(modifier = Modifier.weight(1f), icon = item.icon(isSelected), type = item.type(isSelected), onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                })
            }
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            buttonItems.forEach { item ->
                val isSelected = currentDestination?.hierarchy?.any {
                    it.route == item.route::class.qualifiedName
                } == true
                STFTabTitle(title = stringResource(item.name), type = item.type(isSelected), modifier = Modifier.weight(1f))
            }
        }
    }
}

@Preview
@Composable
fun TabbarPreview() {
    E2MP3Theme {
        STFTabbar(navController = rememberNavController())
    }
}