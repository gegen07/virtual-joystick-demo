# Android Joystick Demo

_This is just a very simple demo which implement this [virtual joystick android Library](https://github.com/controlwear/virtual-joystick-android)._

![Alt text](virtual-joystick.png?raw=true "Double Android Joystick")

## Code
For those who don't want to browse the project or files, here it is...

#### Gradle file
Add library to dependencies.

```
dependencies {
    ...
    compile 'io.github.controlwear:virtualjoystick:1.3.0'
}
```

#### Manifest
Force activity to landscape.
```xml
<activity
    android:name=".MainActivity"
    android:screenOrientation="landscape">
    ...
```

####  Layout
Just a couple of TextView and JoystickView to activity_main.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="io.github.controlwear.joystickdemo.MainActivity"
    android:padding="16dp">

    <TextView
        android:id="@+id/textView_angle_left"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="0°"/>


    <TextView
        android:id="@+id/textView_strength_left"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_below="@+id/textView_angle_left"
        android:text="0%"/>


    <io.github.controlwear.virtual.joystick.android.JoystickView
        xmlns:custom="http://schemas.android.com/apk/res-auto"
        android:id="@+id/joystickView_left"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        android:layout_margin="32dp"
        android:background="@drawable/joystick_background"
        custom:JV_buttonImage="@drawable/pink_ball"
        custom:JV_fixedCenter="true"/>


    <TextView
        android:id="@+id/textView_angle_right"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentRight="true"
        android:text="0°"/>


    <TextView
        android:id="@+id/textView_strength_right"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentRight="true"
        android:layout_below="@+id/textView_angle_right"
        android:text="0%"/>


    <io.github.controlwear.virtual.joystick.android.JoystickView
        xmlns:custom="http://schemas.android.com/apk/res-auto"
        android:id="@+id/joystickView_right"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        android:layout_alignParentRight="true"
        android:layout_marginTop="64dp"
        custom:JV_borderWidth="8dp"
        custom:JV_backgroundColor="#009688"
        custom:JV_borderColor="#00796B"
        custom:JV_buttonColor="#FF6E40"/>

</RelativeLayout>
```

#### Java
MainActivity.java
```java
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
```

### And that's it.
The (quick) documentation of the library is [here](https://github.com/controlwear/virtual-joystick-android).
