package io.github.controlwear.joystickdemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import io.github.controlwear.virtual.joystick.android.JoystickView;

public class MainActivity extends AppCompatActivity {
    private static String LOG_TAG = MainActivity.class.getSimpleName();
    EditText edit_text;
    Button rtn;
    Switch switchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        final String port = sharedPrefs.getString(
                getString(R.string.port_preference_key),
                getString(R.string.default_value_Port));
        final String IP = sharedPrefs.getString(
                getString(R.string.ip_preference_key),
                getString(R.string.default_value_IP)
        );

        final boolean sendMessage = sharedPrefs.getBoolean(
                getString(R.string.switch_send_key),
                true
        );

        JoystickView joystickLeft = (JoystickView) findViewById(R.id.joystickView_left);
        JoystickView joystickRight = (JoystickView) findViewById(R.id.joystickView_right);
        joystickLeft.setOnMoveListener(new JoystickView.OnMoveListener() {
            @Override
            public void onMove(int angle, int strength) {
                /*Log.d(LOG_TAG, "Servo:" +
                                String.format("%03d", executaServo(angle, strength)) + " ;" +
                        IP + Integer.parseInt(port) + "Enabled: " + sendMessage);*/
                if (sendMessage) {
                    UdpClientThread.sendMessage("Servo:" +
                                    String.format("%03d", executaServo(angle, strength)) + " ;",
                            IP, Integer.parseInt(port));

                }
            }
        });

        joystickRight.setOnMoveListener(new JoystickView.OnMoveListener() {
            @Override
            public void onMove(int angle, int strength) {
                if (sendMessage) {
                    UdpClientThread.sendMessage("Motor:" +
                            String.format("%03d", executaMotor(angle, strength)) + " ;",
                            IP, Integer.parseInt(port));

                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_config, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private long executaServo(int angle, int strength) {
        long valorCos = (long) (strength * Math.cos((Math.PI * angle) / 180.0));
        // mapeia valor entre 0 e 180
        return (map(valorCos, -100, 100, 1, 179));
    }

    private float executaMotor(int angle, int strength) {
        // retorna um valor entre -100 e 100
        return (float) (strength * Math.sin((Math.PI * angle) / 180.0) * (-1));
    }

    private long map(long x, long in_min, long in_max, long out_min, long out_max) {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }
}