package vvdshin.quantumbank;

import android.app.Activity;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import static android.content.ContentValues.TAG;

public class MainActivity extends Activity {

    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private ImageView quarterImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        quarterImageView = new ImageView(getApplicationContext());
        quarterImageView.setImageResource(R.drawable.quarter);
        quarterImageView.setMaxWidth(177);
        quarterImageView.setMaxHeight(113);


        if (proximitySensor == null) {
            Log.e(TAG, "Proximity sensor is unavilable");
            finish();
        } else {
            Log.e(TAG, "Proximity sensor active");
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onResume(){
        super.onResume();
        sensorManager.registerListener(proximitySensorListener, proximitySensor, 2*1000*1000);
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(proximitySensorListener);
    }

    // Creating listener for proximity sensor
    private SensorEventListener proximitySensorListener = new SensorEventListener(){
        private RelativeLayout layout = (RelativeLayout) findViewById(R.id.main_layout);
      @Override
      public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.values[0] < proximitySensor.getMaximumRange()){
            layout.addView(quarterImageView);
        } else {
            if (layout != null)
                layout.removeAllViewsInLayout();
        }
      }

      @Override
      public void onAccuracyChanged(Sensor sensor, int i) {

      }
    };
}
