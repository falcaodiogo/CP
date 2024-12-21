package ua.diogo.cp.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.AlarmOff
import androidx.compose.material.icons.filled.AllOut
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Train

sealed class NavItem {
    object Home :
        Item(path = NavPath.HOME.toString(), title = NavTitle.HOME, icon = Icons.Default.Home)

    object Trains :
        Item(
            path = NavPath.TRAINS.toString(),
            title = NavTitle.TRAINS,
            icon = Icons.Default.Train
        )

    object Stallments :
        Item(
            path = NavPath.STALLMENTS.toString(),
            title = NavTitle.STALLMENTS,
            icon = Icons.Default.Alarm
        )

    object ChatBot :
        Item(
            path = NavPath.CHATBOT.toString(),
            title = NavTitle.CHATBOT,
            icon = Icons.Default.AutoAwesome
        )

    object Settings :
        Item(
            path = NavPath.SETTINGS.toString(),
            title = NavTitle.SETTINGS,
            icon = Icons.Default.Settings
        )
}