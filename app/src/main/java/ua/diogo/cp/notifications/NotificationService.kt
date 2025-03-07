package ua.diogo.cp.notifications

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.core.app.NotificationCompat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("notifications_prefs", Context.MODE_PRIVATE)

    fun showProgressNotification(trainName: String, progress: Float, stationName: String) {
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_MUTABLE
        )
        var contextText = "Dentro de momentos, estação de $stationName"

        if (stationName == "No upcoming stations") {
            contextText = "Não existe informação disponível."
        }

        val notification = NotificationCompat.Builder(context, "notification_channel_id")
            .setContentTitle(trainName)
            .setContentText(contextText)
            .setSmallIcon(R.drawable.cplogo)
            .setProgress(100, progress.toInt(), false)
            .setPriority(NotificationManager.IMPORTANCE_MAX)
            .addAction(
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

        saveNotification(trainName, contextText)
    }

    fun updateProgressNotification(jorney: Jorney) {
        CoroutineScope(Dispatchers.IO).launch {
            val progress = calculateTrainProgress(jorney) / 8
            val currentStation = nextStation(jorney, true) ?: "Desconhecido"
            val trainName = "${jorney.serviceCode.designation} ${jorney.trainNumber}"

            if (progress > 0f && currentStation != "Desconhecido") {
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
            .addAction(
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

        saveNotification("CP", "O comboio chegou ao destino.")
    }

    private fun saveNotification(title: String, content: String) {
        val notifications = getAllNotifications().toMutableList()
        notifications.add(NotificationItem(title, content))

        val editor = sharedPreferences.edit()
        editor.putString("notifications_list", Gson().toJson(notifications))
        editor.apply()
    }

    fun getAllNotifications(): List<NotificationItem> {
        val json = sharedPreferences.getString("notifications_list", "[]")
        val type = object : TypeToken<List<NotificationItem>>() {}.type
        return Gson().fromJson(json, type)
    }

    data class NotificationItem(val title: String, val content: String)

    companion object {
        private const val TRAIN_PROGRESS_NOTIFICATION_ID = 1
    }
}
