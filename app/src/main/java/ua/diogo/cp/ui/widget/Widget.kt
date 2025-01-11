package ua.diogo.cp.ui.widget

import android.content.Context
import androidx.compose.material3.Text
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent


object Widget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {

        provideContent {
            Text("Hello World")
        }
    }
}