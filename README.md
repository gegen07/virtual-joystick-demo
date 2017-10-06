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

    <io.github.controlwear.virtual.joystick.android.JoystickView
        xmlns:custom="http://schemas.android.com/apk/res-auto"
        android:id="@+id/joystickView_left"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        android:layout_alignParentLeft="true"
        android:layout_marginTop="64dp"
        custom:JV_backgroundColor="#430FCC"
        custom:JV_buttonImage="@drawable/green_ball" />


    <io.github.controlwear.virtual.joystick.android.JoystickView
        xmlns:custom="http://schemas.android.com/apk/res-auto"
        android:id="@+id/joystickView_right"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        android:layout_alignParentRight="true"
        android:layout_marginTop="64dp"
        custom:JV_backgroundColor="#430FCC"
        custom:JV_buttonImage="@drawable/green_ball"/>


    <Switch
        android:id="@+id/simpleSwitch"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentRight="true"
        android:colorControlActivated="#ff932c"
        android:text="Udp"
        android:trackTint="@color/switch_color" />

    <EditText
        android:id="@+id/IpAddress"
        android:layout_width="150dp"
        android:layout_height="wrap_content"
        android:layout_alignParentStart="true"
        android:layout_alignParentTop="true"
        android:drawableLeft="@drawable/ic_network_wifi"
        android:hint="@string/ip"
        android:inputType="text"
        android:padding="5dp"
        android:singleLine="true"
        android:textColor="#999999"
        android:textSize="14dp"
        android:visibility="visible" />

    <Button
        android:id="@+id/btnAddTitle"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignBaseline="@+id/IpAddress"
        android:layout_alignBottom="@+id/IpAddress"
        android:layout_toEndOf="@+id/IpAddress"
        android:text="@string/btn_AddTitle" />

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
