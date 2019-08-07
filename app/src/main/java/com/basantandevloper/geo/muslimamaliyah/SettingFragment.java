package com.basantandevloper.geo.muslimamaliyah;


import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends PreferenceFragment {


    public SettingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.halaman_setting);

        final ListPreference listPreferenceArabic = (ListPreference) findPreference(getString(R.string.key_list_arabic));
        if(listPreferenceArabic.getValue()==null) {
            listPreferenceArabic.setValueIndex(0);
        }
        listPreferenceArabic.setSummary(listPreferenceArabic.getEntry().toString());

        listPreferenceArabic.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                listPreferenceArabic.setValue(newValue.toString());
                preference.setSummary(listPreferenceArabic.getEntry());

                return true;
            }
        });

        //
        final ListPreference listPreferenceArabicSize = (ListPreference) findPreference(getString(R.string.key_list_arabic_size));
        if(listPreferenceArabicSize.getValue()==null) {
            listPreferenceArabicSize.setValueIndex(0);
        }
        listPreferenceArabicSize.setSummary(listPreferenceArabicSize.getEntry().toString());
        listPreferenceArabicSize.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                listPreferenceArabicSize.setValue(newValue.toString());
                preference.setSummary(listPreferenceArabicSize.getEntry());

                return true;
            }
        });
        //
        final ListPreference listPreferenceLatinSize = (ListPreference) findPreference(getString(R.string.key_list_latin_size));
        if(listPreferenceLatinSize.getValue()==null) {
            listPreferenceLatinSize.setValueIndex(0);
        }
        listPreferenceLatinSize.setSummary(listPreferenceLatinSize.getEntry().toString());
        listPreferenceLatinSize.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                listPreferenceLatinSize.setValue(newValue.toString());
                preference.setSummary(listPreferenceLatinSize.getEntry());

                return true;
            }
        });
        //list terjemahan
        final ListPreference listPreferenceTerjemahanSize = (ListPreference) findPreference(getString(R.string.key_list_terjemahan_size));
        if(listPreferenceTerjemahanSize.getValue()==null) {
            listPreferenceTerjemahanSize.setValueIndex(0);
        }
        listPreferenceTerjemahanSize.setSummary(listPreferenceTerjemahanSize.getEntry().toString());
        listPreferenceTerjemahanSize.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                listPreferenceTerjemahanSize.setValue(newValue.toString());
                preference.setSummary(listPreferenceTerjemahanSize.getEntry());
                return true;
            }
        });

        settingPref();
    }

    private void settingPref() {
        SwitchPreference switchLatin = (SwitchPreference) findPreference(getString(R.string.key_latin));
        SwitchPreference switchTerjemahan = (SwitchPreference) findPreference(getString(R.string.key_terjemahan));
        switchLatin.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if ((Boolean) newValue) {
                    Toast.makeText(getActivity(), "Latin Hidup", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Latin Mati", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
        //
        switchTerjemahan.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if ((Boolean) newValue) {
                    Toast.makeText(getActivity(), "Terjemahan Hidup", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Terjemahan Mati", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
    }

}
