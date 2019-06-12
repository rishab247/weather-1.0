package data;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Utils.Utils;
import model.Weather;
import model.place;

public class JsonWeatherParser {
    public static Weather getWeather(String data) {
        Weather weather = new Weather();
        try {
            JSONObject jsonObject = new JSONObject(data);
            place place = new place();

            JSONObject coorobj = Utils.getObject("coord", jsonObject);
            place.setLat(Utils.getFloat("lat", coorobj));
            place.setLon(Utils.getFloat("lon", coorobj));
            JSONObject sysobj = Utils.getObject("sys", jsonObject);
            place.setCountry(Utils.getString("country", sysobj));
            place.setLastupdates(Utils.getint("dt", jsonObject));
            place.setSunrise(Utils.getint("sunrise", sysobj));
            place.setSunset(Utils.getint("sunset", sysobj));
            place.setCity(Utils.getString("name", jsonObject));
            weather.place1 = place;
            JSONArray jsonArray = jsonObject.getJSONArray("weather");
            JSONObject jsonweather = jsonArray
                    .getJSONObject(0);
            weather.currentcondition1.setWeatherid(Utils.getint("id", jsonweather));
            weather.currentcondition1.setDescription(Utils.getString("description", jsonweather));
            weather.currentcondition1.setCondition(Utils.getString("main", jsonweather));
            weather.currentcondition1.setIcon(Utils.getString("icon", jsonweather));
            Log.e("icon", Utils.getString("icon", jsonweather));

            JSONObject windobj = Utils.getObject("wind", jsonObject);
            weather.wind.setSpeed(Utils.getFloat("speed", windobj));
            JSONObject cloudobj = Utils.getObject("clouds", jsonObject);
            weather.clouds.setPreipitation(Utils.getint("all", cloudobj));

            JSONObject mainobj = Utils.getObject("main", jsonObject);
            weather.currentcondition1.setTemperature(Utils.getFloat("temp", mainobj));
            weather.currentcondition1.setHumidity(Utils.getint("humidity", mainobj));
            weather.currentcondition1.setPressure(Utils.getint("pressure", mainobj));

            return weather;
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {

            e.printStackTrace();
        }
        return null;

    }

}
