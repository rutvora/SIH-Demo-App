package bphc.sih.demo.sihdemoapp.Helpers;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import java.util.Arrays;

import bphc.sih.demo.sihdemoapp.Helpers.Decoders.DisasterManagement;
import bphc.sih.demo.sihdemoapp.Helpers.Decoders.WeatherWarning;
import bphc.sih.demo.sihdemoapp.WebView;

public class NotificationHandler {

    Context context;

    public NotificationHandler(Context context) {
        this.context = context;
    }

    public void notifyUser(String title, String content, String id, String message, boolean isWeather) {

        Intent intent = new Intent(context, WebView.class);
        if (title.equals("Check Nearby Amenities"))
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        String[] data;
        Log.w("isWeather", isWeather + "");
        if (isWeather) data = WeatherWarning.decode(context, id, message);
        else data = DisasterManagement.decode(context, id, message);
        if (data == null) return;
        Log.w("Data Notif", Arrays.toString(data));
        intent.putExtra("data", data);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, Integer.parseInt(data[0]), intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "Channel ID")
                .setSmallIcon(android.R.drawable.ic_menu_compass)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

// notificationId is a unique int for each notification that you must define
        notificationManager.notify(0, builder.build());
    }

    public void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Channel Name";
            String description = "Channel Description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("Channel ID", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

}
