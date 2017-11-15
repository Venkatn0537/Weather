package challenge.android.com.weather.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Praneeth on 11/13/2017.
 */

public class SharedPreferenceHelper {

    private static SharedPreferences pref;

    private SharedPreferenceHelper(Context context) {

        pref = PreferenceManager.getDefaultSharedPreferences(context);

    }

    public static SharedPreferenceHelper with(Context context) {

        return new SharedPreferenceHelper(context);

    }

    /**
     * To store city information
     * @param key
     * @param value
     */
    public void setValue(String key, String value) {

        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * To get city informaiton
     * @param key
     * @return
     */
    public String getValue(String key) {
        return pref.getString(key, "");
    }

}
