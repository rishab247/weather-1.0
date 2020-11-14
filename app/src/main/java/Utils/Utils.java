package Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class Utils {
    public static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather?q=";

    public static final String BASE_ICON = "https://openweathermap.org/img/w/";

    public static JSONObject getObject(String tagname, JSONObject jsonObject) throws JSONException {
        JSONObject jobject = jsonObject.getJSONObject(tagname);
        return jobject;
    }

    public static String getString(String tagname, JSONObject jsonObject) throws JSONException {

        return jsonObject.getString(tagname);
    }


    public static float getFloat(String tagname, JSONObject jsonObject) throws JSONException {
        return (float) jsonObject.getDouble(tagname);
    }

    public static double getdouble(String tagname, JSONObject jsonObject) throws JSONException {
        return (float) jsonObject.getDouble(tagname);
    }

    public static int getint(String tagname, JSONObject jsonObject) throws JSONException {
        return jsonObject.getInt(tagname);
    }
}
