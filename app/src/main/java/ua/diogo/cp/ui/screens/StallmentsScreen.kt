package ua.diogo.cp.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ua.diogo.cp.ui.components.ScreenTitle

@Composable
fun StallmentsScreen(context: Context) {
    Column(
        modifier = Modifier
            .padding(top = 46.dp, start = 24.dp, end = 24.dp, bottom = 116.dp)
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
            .height(IntrinsicSize.Max),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        ScreenTitle("Impasses")
    }
}