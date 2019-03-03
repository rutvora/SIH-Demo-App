package bphc.sih.demo.sihdemoapp.Helpers;

import android.content.Context;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONException;
import org.json.JSONObject;

import bphc.sih.demo.sihdemoapp.Helpers.Decoders.Continuous;

public class MQTT implements MqttCallbackExtended {
    private final String serverUri = "tcp://192.168.43.41:1883";      //TODO: Define server
    private final String topic = "bla";          //TODO: Define topic
    private Context context;
    private MqttAndroidClient mqttAndroidClient;

    public MQTT(Context context) {
        this.context = context;
        mqttAndroidClient = new MqttAndroidClient(context, serverUri, MqttClient.generateClientId());
        mqttAndroidClient.setCallback(this);
        connect();

    }

    private void connect() {
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setCleanSession(true);
        Log.w("Mqtt", "Trying to connect");
        //mqttConnectOptions.setUserName(username);
        //mqttConnectOptions.setPassword(password.toCharArray());

        try {
            Log.w("Connected", mqttAndroidClient.isConnected() + "");
            mqttAndroidClient.connect(mqttConnectOptions, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.w("Mqtt", "Connected, Trying to subscribe");
                    DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
                    disconnectedBufferOptions.setBufferEnabled(true);
                    disconnectedBufferOptions.setBufferSize(100);
                    disconnectedBufferOptions.setPersistBuffer(false);
                    disconnectedBufferOptions.setDeleteOldestMessages(false);
                    mqttAndroidClient.setBufferOpts(disconnectedBufferOptions);
                    subscribeToTopic();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.w("Mqtt", "Failed to connect to: " + serverUri + exception.toString());
                    exception.printStackTrace();
                }
            });


        } catch (MqttException ex) {
            ex.printStackTrace();
        }
    }

    public void disconnect() {
        try {
//            mqttAndroidClient.disconnect();
            mqttAndroidClient.unregisterResources();
            mqttAndroidClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void subscribeToTopic() {
        try {
            mqttAndroidClient.subscribe(topic, 2, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.w("Mqtt", "Subscribed!");
                    Toast.makeText(context, "Connection Established", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.w("Mqtt", "Subscribed fail!");
                    Toast.makeText(context, "Connection Failed!", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (MqttException ex) {
            Toast.makeText(context, "Connection Failed!", Toast.LENGTH_SHORT).show();
            ex.printStackTrace();
        }
    }

    @Override
    public void connectComplete(boolean reconnect, String serverURI) {

    }

    @Override
    public void connectionLost(Throwable cause) {

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        Log.w("Mqtt ", message.toString());
        try {
            JSONObject object = new JSONObject(message.toString());
            switch (object.getString("type")) {
                case "text":
                    Pair<Continuous, Integer> result = Continuous.decode(object.getString("data"));
                    Log.w("remaining", result.second + "");
                    if (result.second == 0) Log.w("Continous", result.first.getString());
                    break;
                case "hotspots": {

                    NotificationHandler handler = new NotificationHandler(context);
                    handler.createNotificationChannel();
                    handler.notifyUser("Relief centres near you", "Click to open maps",
                            object.getString("id"), object.getString("data"), object.getString("type"));


//                MapsActivity.hotspots = Hotspots.decode(object.getString("data"));
//                if (MapsActivity.hotspots != null) {
//                    Intent intent = new Intent(context, MapsActivity.class);
//                    context.startActivity(intent);
//                }
                    break;
                }
                case "weather": {
                    NotificationHandler handler = new NotificationHandler(context);
                    handler.createNotificationChannel();
                    handler.notifyUser("Weather Alert", "Click here to know more",
                            object.getString("id"), object.getString("data"), object.getString("type"));
                    break;
                }
                case "disaster": {
                    NotificationHandler handler = new NotificationHandler(context);
                    handler.createNotificationChannel();
                    handler.notifyUser("Disaster Alert", "Click here to know more",
                            object.getString("id"), object.getString("data"), object.getString("type"));

//                DisasterManagement.decode(context, object.getString("id"), object.getString("data"));
                    break;
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }
}
