package vvdshin.quantumbank;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static android.content.ContentValues.TAG;

public class MainActivity extends Activity {

/*    private SensorManager sensorManager;
    private Sensor proximitySensor;*/

    private boolean isQuarterVisible = true;
    private boolean isSharpieVisible = true;
    private String mode = "quarter";
    private boolean isObjectVisible = true;
    private boolean isLocked = false;
    private int lockTouchCount = 0;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageView quarterImageView = new ImageView(getApplicationContext());
        final ImageView sharpieImageView = new ImageView(getApplicationContext());
        final RelativeLayout layout = (RelativeLayout) findViewById(R.id.main_layout);

        //Quarter
        quarterImageView.setImageResource(R.drawable.quarter);
        quarterImageView.setAdjustViewBounds(true);
        quarterImageView.setMaxWidth(300);
        quarterImageView.setMaxHeight(300);
        quarterImageView.setX(500);
        quarterImageView.setY(500);

        //Sharpie
        sharpieImageView.setImageResource(R.drawable.black_sharpie);
        sharpieImageView.setAdjustViewBounds(true);
        sharpieImageView.setMaxHeight(2000);
        sharpieImageView.setX(500);
        sharpieImageView.setY(50);
        sharpieImageView.setVisibility(View.INVISIBLE);

        final TextView quarterButton = findViewById(R.id.quarter_button);
        final TextView lockButton = findViewById(R.id.lock_button);
        final TextView lockNotifier = findViewById(R.id.lock_notifier);
        final TextView switchButton = findViewById(R.id.switch_button);

        layout.addView(quarterImageView);
        layout.addView(sharpieImageView);

        //Quarter Button
        quarterButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent e) {
                int pointerCount = e.getPointerCount();
                Log.e(TAG, "Point Count: " + pointerCount);

                    if (
                            //pointerCount == 2 &&
                            //e.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN
                            e.getAction() == MotionEvent.ACTION_DOWN && !isObjectVisible) {
                        //int rawTouchX;
                        //int rawTouchY;
                        //final int location[] = {0, 0};
                        //v.getLocationOnScreen(location);
                        //int secondTouchX = (int) e.getX(1);
                        //int secondTouchY = (int) e.getY(1);
                        //rawTouchX = secondTouchX + location[0] - 150;
                        //rawTouchY = secondTouchY + location[1] - 50;
                        if (mode.equals("quarter")) {
                            //quarterImageView.setX(rawTouchX);
                            //quarterImageView.setY(rawTouchY);
                            quarterImageView.setX(500);
                            quarterImageView.setY(500);
                            quarterImageView.setVisibility(View.VISIBLE);
                            isQuarterVisible = true;
                        } else if (mode.equals("sharpie")){
                            sharpieImageView.setX(500);
                            sharpieImageView.setY(50);
                            sharpieImageView.setVisibility(View.VISIBLE);
                            isSharpieVisible = true;
                        }
                        isObjectVisible = true;
                    } else if (
                            //pointerCount == 1 &&
                            isObjectVisible
                            && e.getAction() == MotionEvent.ACTION_DOWN) {

                        if (mode.equals("quarter")){
                            quarterImageView.setVisibility(View.INVISIBLE);
                            isQuarterVisible = false;
                        } else if(mode.equals("sharpie")){
                            sharpieImageView.setVisibility(View.INVISIBLE);
                            isSharpieVisible = false;
                        }
                        isObjectVisible = false;
                    }


                return true;
            }
        });

        //Lock Button
        lockButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    lockTouchCount++;
                    if (lockTouchCount == 3){
                        lockTouchCount = 0;
                        isLocked = !isLocked;
                        if (isLocked){
                            lockNotifier.setVisibility(View.INVISIBLE);
                            quarterButton.setVisibility(View.INVISIBLE);

                        } else {
                            lockNotifier.setVisibility(View.VISIBLE);
                            quarterButton.setVisibility(View.VISIBLE);
                        }
                    }
                }

                return false;
            }
        });

        //Switch Button
        switchButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    if (mode.equals("quarter")) {
                        quarterImageView.setVisibility(View.INVISIBLE);
                        if (isSharpieVisible){
                            sharpieImageView.setVisibility(View.VISIBLE);
                            isObjectVisible = true;
                        }
                        mode = "sharpie";
                        switchButton.setText("s");
                        Log.e(TAG, "sharpie on");
                    } else if (mode.equals("sharpie")){
                        if (isQuarterVisible) {
                            quarterImageView.setVisibility(View.VISIBLE);
                            isObjectVisible = true;
                        }
                        sharpieImageView.setVisibility(View.INVISIBLE);

                        mode = "quarter";
                        switchButton.setText("q");
                        Log.e(TAG, "quarter on");
                    }
                }
                return false;
            }
        });
    }


}
