package vvdshin.quantumbank;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import static android.content.ContentValues.TAG;

public class MainActivity extends Activity {

/*    private SensorManager sensorManager;
    private Sensor proximitySensor;*/

    private boolean isQuarterVisible = true;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageView quarterImageView = new ImageView(getApplicationContext());
        final RelativeLayout layout = (RelativeLayout) findViewById(R.id.main_layout);
/*        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);*/
        quarterImageView.setImageResource(R.drawable.quarter);
        quarterImageView.setAdjustViewBounds(true);
        quarterImageView.setMaxWidth(300);
        quarterImageView.setMaxHeight(300);
        quarterImageView.setX(500);
        quarterImageView.setY(500);
        quarterImageView.bringToFront();
        final Button quarterButton = findViewById(R.id.quarter_button);
        layout.addView(quarterImageView);

        quarterButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent e) {
                int pointerCount = e.getPointerCount();
                Log.e(TAG, "Point Count: "+ pointerCount);

                if (pointerCount == 2
                    && e.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN
                        && !isQuarterVisible) {
                        int rawTouchX;
                        int rawTouchY;
                        final int location[] = {0,0};
                        v.getLocationOnScreen(location);
                        int secondTouchX =  (int) e.getX(1);
                        int secondTouchY = (int) e.getY(1);
                        rawTouchX = secondTouchX + location[0] - 150;
                        rawTouchY = secondTouchY + location[1] - 50;
                        quarterImageView.setX(rawTouchX);
                        quarterImageView.setY(rawTouchY);
                        layout.addView(quarterImageView);
                        isQuarterVisible = true;
                        return true;
                } else if (pointerCount == 1
                        && isQuarterVisible
                        && e.getAction() == MotionEvent.ACTION_DOWN) {
                    layout.removeView(quarterImageView);
                    isQuarterVisible = false;
                }
                return true;
            }
        });
/*        if (proximitySensor == null) {
            Log.e(TAG, "Proximity sensor is unavilable");
            finish();
        } else {
            Log.e(TAG, "Proximity sensor active");
        }*/
    }



    @Override
    public void onResume() {
        super.onResume();
        /*        sensorManager.registerListener(proximitySensorListener, proximitySensor, 2 * 1000 * 1000);*/
    }

    @Override
    public void onPause() {
        super.onPause();
        /*        sensorManager.unregisterListener(proximitySensorListener);*/
    }

    // Creating listener for proximity sensor
/*    private SensorEventListener proximitySensorListener = new SensorEventListener() {
        private RelativeLayout layout = (RelativeLayout) findViewById(R.id.main_layout);

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            if (sensorEvent.values[0] < proximitySensor.getMaximumRange()) {
                layout.addView(quarterImageView);
            } else {
                if (layout != null)
                    layout.removeAllViewsInLayout();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };*/
}
