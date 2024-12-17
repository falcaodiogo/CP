package ua.diogo.cp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AlarmOff
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.FlightLand
import androidx.compose.material.icons.filled.FlightTakeoff
import androidx.compose.material.icons.filled.Settings

sealed class NavItem {
    object Home :
        Item(path = NavPath.HOME.toString(), title = NavTitle.HOME)

    object Schedule :
        Item(
            path = NavPath.SCHEDULE.toString(),
            title = NavTitle.SCHEDULE,
            icon = Icons.Default.DateRange
        )

    object Departures :
        Item(
            path = NavPath.DEPARTURES.toString(),
            title = NavTitle.DEPARTURES,
            icon = Icons.Default.FlightTakeoff // melhorar ícone enventualmente
        )

    object Arrivals :
        Item(
            path = NavPath.ARRIVALS.toString(),
            title = NavTitle.ARRIVALS,
            icon = Icons.Default.FlightLand // melhorar ícone enventualmente
        )

    object Atrasos :
        Item(
            path = NavPath.ATRASOS.toString(),
            title = NavTitle.ATRASOS,
            icon = Icons.Default.AlarmOff
        )

    object ChatBot :
        Item(
            path = NavPath.CHATBOT.toString(),
            title = NavTitle.CHATBOT,
            icon = Icons.Default.Android // meter ícone do chatbot
        )

    object Settings :
        Item(
            path = NavPath.SETTINGS.toString(),
            title = NavTitle.SETTINGS,
            icon = Icons.Default.Settings
        )
}