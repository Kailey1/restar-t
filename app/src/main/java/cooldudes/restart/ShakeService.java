package cooldudes.restart;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import cooldudes.restart.MainActivity;

public class ShakeService extends Service implements SensorEventListener {

    private SensorManager sensorMgr;
    private Sensor acc;
    private long lastUpdate = -1;
    private float x, y, z;
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 1100;



    @Override
    public void onCreate() {

        sensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);
        acc=sensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        boolean accelSupported=  sensorMgr.registerListener((SensorEventListener) this, acc, SensorManager.SENSOR_DELAY_GAME);
        long curTime11 = System.currentTimeMillis();

        if (!accelSupported) {
            // on accelerometer on this device
            sensorMgr.unregisterListener((SensorEventListener) this,acc);
        }

        super.onCreate();
    }


    protected void onPause() {
        if (sensorMgr != null) {
            sensorMgr.unregisterListener((SensorEventListener) this,acc);
            sensorMgr = null;
        }
        return;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {

        if (sensorMgr != null) {
            sensorMgr.unregisterListener((SensorEventListener) this,acc);
            sensorMgr = null;
        }
        stopSelf();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            long curTime = System.currentTimeMillis();
            // only allow one update every 100ms.
            if ((curTime - lastUpdate) > 100) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                x = sensorEvent.values[SensorManager.DATA_X];
                y = sensorEvent.values[SensorManager.DATA_Y];
                z = sensorEvent.values[SensorManager.DATA_Z];

                float speed = Math.abs(x+y+z - last_x - last_y - last_z) / diffTime * 10000;

                if (speed > SHAKE_THRESHOLD) {
                    Toast.makeText(this, "hold on!", Toast.LENGTH_SHORT).show();

                    Intent myIntent= new Intent(this, ShakeActivity.class);
                    myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(myIntent);
                }
                last_x = x;
                last_y = y;
                last_z = z;
            }

        }

    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }



}