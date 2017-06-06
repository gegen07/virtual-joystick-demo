package io.github.controlwear.joystickdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import io.github.controlwear.virtual.joystick.android.JoystickView;

public class MainActivity extends AppCompatActivity {
    EditText edit_text;
    Button rtn;
    Switch switchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        switchButton = (Switch) findViewById(R.id.simpleSwitch);
        rtn = (Button)findViewById(R.id.btnAddTitle);
        edit_text = (EditText)findViewById(R.id.IpAddress);

        final String[] IP = new String[1];
       rtn.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        IP[0] = edit_text.getText().toString();
                        Toast msg = Toast.makeText(getBaseContext(), IP[0],Toast.LENGTH_LONG);
                        msg.show();
                    }
                });

        switchButton.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {

                JoystickView joystickLeft = (JoystickView) findViewById(R.id.joystickView_left);
                JoystickView joystickRight = (JoystickView) findViewById(R.id.joystickView_right);
                joystickLeft.setOnMoveListener(new JoystickView.OnMoveListener() {
                    @Override
                    public void onMove(int angle, int strength) {
                        if (isChecked) {
                            UdpClientThread.sendMessage("LA:" + String.format("%03d", angle) +
                                    " LS:" + String.format("%03d", strength) + " ;", IP[0]);

                        }
                    }
                });

                joystickRight.setOnMoveListener(new JoystickView.OnMoveListener() {
                    @Override
                    public void onMove(int angle, int strength) {
                        if (isChecked) {
                            UdpClientThread.sendMessage("RA:" + String.format("%03d", angle) +
                                    " RS:" + String.format("%03d", strength) + " ;", IP[0]);

                        }
                    }
                });
            }
        });
    }
}