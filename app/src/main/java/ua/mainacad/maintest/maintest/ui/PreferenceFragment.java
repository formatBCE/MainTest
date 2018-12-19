package ua.mainacad.maintest.maintest.ui;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;
import ua.mainacad.maintest.maintest.R;

public class PreferenceFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.preferences);
    }


}
