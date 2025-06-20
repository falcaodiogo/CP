package ua.diogo.cp.ui.navigation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.coroutines.launch

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val navItems = listOf(
        NavItem.Home,
        NavItem.Stations,
        NavItem.Trains,
        NavItem.ChatBot,
        NavItem.Settings
    )

    val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route
    val haptic = LocalHapticFeedback.current
    val coroutineScope = rememberCoroutineScope()

    NavigationBar(
        containerColor = Color.Transparent
    ) {
        navItems.forEachIndexed { index, item ->
            val offsetY = remember { Animatable(0f) }

            NavigationBarItem(
                alwaysShowLabel = true,
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title,
                        modifier = Modifier.offset(y = offsetY.value.dp)
                    )
                },
                label = { Text(item.title) },
                selected = currentDestination == item.path,
                onClick = {
                    coroutineScope.launch {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)

                        offsetY.animateTo(
                            targetValue = 4f,
                            animationSpec = tween(durationMillis = 80)
                        )

                        offsetY.animateTo(
                            targetValue = 0f,
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioLowBouncy,
                                stiffness = Spring.StiffnessMediumLow
                            )
                        )
                    }

                    navController.navigate(item.path) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) { saveState = true }
                        }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}