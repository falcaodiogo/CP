package ua.diogo.cp.notifications

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ua.diogo.cp.R
import ua.diogo.cp.data.retrofit.entity.Jorney
import ua.diogo.cp.mathFunctHelpers.calculateTrainProgress
import ua.diogo.cp.mathFunctHelpers.currentStation

class NotificationService(
    private val context: Context
) {
    private val notificationManager = context.getSystemService(NotificationManager::class.java)

    fun showProgressNotification(progress: Float, stationName: String) {
        val notification = NotificationCompat.Builder(context, "notification_channel_id")
            .setContentTitle("CP")
            .setContentText("O comboio chegou a $stationName Progresso: ${progress.toInt()}%")
            .setSmallIcon(R.drawable.cplogo)
            .setProgress(100, progress.toInt(), false)
            .setPriority(NotificationManager.IMPORTANCE_MAX)
            .setAutoCancel(false)
            .build()

        notificationManager.notify(
            TRAIN_PROGRESS_NOTIFICATION_ID,
            notification
        )
    }

    fun updateProgressNotification(jorney: Jorney) {
        CoroutineScope(Dispatchers.IO).launch {
            while (jorney.status != "COMPLETED") {
                val progress = calculateTrainProgress(jorney)
                val currentStation = currentStation(jorney) ?: "Desconhecido"

                if (!progress.equals(0f) || !currentStation.equals("Desconhecido")) {
                    showProgressNotification(progress, currentStation)
                }

                delay(60000L)

                if (progress >= 100) {
                    completeProgressNotification()
                    break
                }
            }
        }
    }


    fun completeProgressNotification() {
        val notification = NotificationCompat.Builder(context, "notification_channel_id")
            .setContentTitle("CP")
            .setContentText("O comboio chegou ao destino.")
            .setSmallIcon(R.drawable.cplogo)
            .setPriority(NotificationManager.IMPORTANCE_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(
            TRAIN_PROGRESS_NOTIFICATION_ID,
            notification
        )
    }

    companion object {
        private const val TRAIN_PROGRESS_NOTIFICATION_ID = 1
    }
}
