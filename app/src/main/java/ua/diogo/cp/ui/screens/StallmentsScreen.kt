package ua.diogo.cp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ua.diogo.cp.notifications.NotificationService
import ua.diogo.cp.ui.components.ScreenTitle

@Composable
fun StallmentsScreen(notificationService: NotificationService) {

    var notifications by remember { mutableStateOf(notificationService.getAllNotifications()) }

    Column(
        modifier = Modifier
            .padding(top = 46.dp, start = 24.dp, end = 24.dp, bottom = 116.dp)
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
            .height(IntrinsicSize.Max),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        ScreenTitle("Notificações")
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            for (i in notifications.size - 1 downTo 0) {
                if (i < notifications.size - 3) {
                    break
                }
                val notification = notifications[i]
                SettingsButton(
                    label = notification.title + " " + notification.content.replace(
                        "Dentro de momentos,",
                        "na"
                    ), icon = Icons.Default.Circle
                ) {
                }
            }
        }
    }
}