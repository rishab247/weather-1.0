package data;

import android.app.Activity;
import android.content.SharedPreferences;

public class CityPreference {
    SharedPreferences preferences;

    public CityPreference(Activity activity) {
        preferences = activity.getPreferences(Activity.MODE_PRIVATE);
    }

    public String getCity() {
        return preferences.getString("city", "Delhi,in");
    }

    public void setCity(String city) {
        preferences.edit().putString("city", city).commit();
    }
}
