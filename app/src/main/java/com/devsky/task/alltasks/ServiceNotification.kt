
package com.devsky.task.alltasks

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat.*
import androidx.core.app.NotificationManagerCompat.IMPORTANCE_MIN

/**
 * Created by Iman on 08/09/2016.
 */
class ServiceNotification @JvmOverloads constructor(
    context: Context,
    runningInBackground: Boolean = false
) {
    private var notificationBuilder: Builder? = null
    var notification: Notification? = null
    private var notificationPendingIntent: PendingIntent? = null
    private var notificationManager: NotificationManager? = null

    companion object {
        private val TAG : String = Notification::class.java.simpleName
        private val CHANNEL_ID = TAG
        var notificationIcon = 0
        var notificationTitle: String? = null
        var thoughtOfTheDay: String? = null
        var notificationText: String? = null

    }

    init {
        notification = if (runningInBackground) {
            val message = "Running in the background"
            val title = "Never Ending Task"
            setNotification(context, message)
        } else {
            val thoughOfTheDay =
                thoughtOfTheDay
            val notificationTitle =
                notificationTitle
            val notificationIcon =
                notificationIcon
            setNotification(context, notificationTitle, thoughOfTheDay, notificationIcon)
        }
    }
    /**
     * This is the method that can be called to update the ServiceNotification
     */
    private fun setNotification(context: Context, title: String?, text: String?, icon: Int): Notification {
        val notification: Notification
        notificationManager = context
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // OREO
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            val name: CharSequence = "Permanent ServiceNotification"
            val importance: Int = NotificationManager.IMPORTANCE_LOW
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            val description = "I would like to receive travel alerts and notifications for:"
            channel.description = description
            notificationBuilder = Builder(context, CHANNEL_ID)
            if (notificationManager != null) {
                notificationManager!!.createNotificationChannel(channel)
            }
            notification =
                notificationBuilder!!
                    .setSmallIcon(icon)
                    .setContentTitle(title)
                    .setContentText(text)
                    .setContentIntent(notificationPendingIntent)
                    .setVisibility(VISIBILITY_PRIVATE)
                    .build()
        } else {
            notification = Builder(
                context,
                "channelID"
            )
                .setSmallIcon(icon)
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) NotificationManager.IMPORTANCE_MIN
                    else IMPORTANCE_MIN
                )
                .setContentIntent(notificationPendingIntent)
                .setVisibility(VISIBILITY_PRIVATE)
                .build()
        }
        return notification
    }


    private fun setNotification(context: Context, text: String?): Notification {
        return setNotification(context, notificationTitle, text, notificationIcon)
    }

}