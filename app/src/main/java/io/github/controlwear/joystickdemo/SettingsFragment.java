package io.github.controlwear.joystickdemo;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SettingsFragment extends PreferenceFragment
        implements Preference.OnPreferenceChangeListener {

    private EditTextPreference ip;
    private EditTextPreference port;
    private SwitchPreference sendMessage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);

            ip = (EditTextPreference) (findPreference(getString(R.string.ip_preference_key)));
            port = (EditTextPreference) (findPreference(getString(R.string.port_preference_key)));
            sendMessage = (SwitchPreference) (findPreference(getString(R.string.switch_send_key)));

            bindPreferenceSummaryToValue(ip);
            bindPreferenceSummaryToValue(port);
            bindSwitchPreferenceEnabledToValue(sendMessage);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object value) {

            if (preference.getKey().equals(sendMessage.getKey())) {
                sendMessage.setChecked((Boolean) value);
            } else if (preference.getKey().equals(ip.getKey())) {
                ip.setSummary(value.toString());
            } else {
                port.setSummary(value.toString());
            }

            return true;
    }

    private void bindPreferenceSummaryToValue(Preference preference) {
            // Set the listener to watch for value changes.
            preference.setOnPreferenceChangeListener(this);

            // Trigger the listener immediately with the preference's
            // current value.
            onPreferenceChange(preference, PreferenceManager
                    .getDefaultSharedPreferences(preference.getContext())
                    .getString(preference.getKey(), ""));
    }

    private void bindSwitchPreferenceEnabledToValue(SwitchPreference preference) {
            // Set the listener to watch for value changes.
            preference.setOnPreferenceChangeListener(this);


            onPreferenceChange(preference,
            PreferenceManager
            .getDefaultSharedPreferences(preference.getContext())
            .getBoolean(preference.getKey(), preference.isChecked()));
    }
}
