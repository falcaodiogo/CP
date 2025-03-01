package ua.diogo.cp.notifications

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ua.diogo.cp.R
import ua.diogo.cp.activities.MainActivity
import ua.diogo.cp.data.retrofit.entity.Jorney
import ua.diogo.cp.mathFunctHelpers.calculateTrainProgress
import ua.diogo.cp.mathFunctHelpers.nextStation

class NotificationService(
    private val context: Context
) {
    private val notificationManager = context.getSystemService(NotificationManager::class.java)

    fun showProgressNotification(trainName: String, progress: Float, stationName: String) {
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_MUTABLE
        )
        var contextText = "Dentro de momentos, estação de $stationName."

        if (stationName.equals("No upcoming stations")) {
            contextText = "Não existe informação disponível."
        }

        val notification = NotificationCompat.Builder(context, "notification_channel_id")
            .setContentTitle(trainName)
            .setContentText(contextText)
            .setSmallIcon(R.drawable.cplogo)
            .setProgress(100, progress.toInt(), false)
            .setPriority(NotificationManager.IMPORTANCE_MAX)
            .addAction( // open app
                NotificationCompat.Action(
                    R.drawable.cplogo,
                    "Mais informações",
                    pendingIntent
                )
            )
            .setAutoCancel(false)
            .build()

        notificationManager.notify(
            TRAIN_PROGRESS_NOTIFICATION_ID,
            notification
        )
    }

    fun updateProgressNotification(jorney: Jorney) {
        CoroutineScope(Dispatchers.IO).launch {
            val progress = calculateTrainProgress(jorney)/8 // tirar o padding de 8f
            val currentStation = nextStation(jorney, true) ?: "Desconhecido"
            val trainName =
                (jorney.serviceCode.designation + " ${jorney.trainNumber}") ?: "Desconhecido"

            if (!progress.equals(0f) || !currentStation.equals("Desconhecido")) {
                showProgressNotification(trainName, progress, currentStation)
            } else if (progress >= 100) {
                completeProgressNotification()
            }
        }
    }


    fun completeProgressNotification() {
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_MUTABLE
        )

        val notification = NotificationCompat.Builder(context, "notification_channel_id")
            .setContentTitle("CP")
            .setContentText("O comboio chegou ao destino.")
            .setSmallIcon(R.drawable.cplogo)
            .addAction( // open app
                NotificationCompat.Action(
                    R.drawable.cplogo,
                    "Mais informações",
                    pendingIntent
                )
            )
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
