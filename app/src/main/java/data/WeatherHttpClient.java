package data;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import Utils.Utils;

import static android.content.ContentValues.TAG;

public class WeatherHttpClient {
    public String getWeatherData(String place) {
        HttpURLConnection connection = null;
        InputStream inputStream = null;

        try {
            connection = (HttpURLConnection) (new URL(Utils.BASE_URL + place)).openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();
            StringBuffer stringBuffer = new StringBuffer();
            inputStream = connection.getInputStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line + "\r\n");
            }
            inputStream.close();
            connection.disconnect();
            Log.e("input", stringBuffer.toString());
            return stringBuffer.toString();

        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + place);

            try {
                connection = (HttpURLConnection) (new URL(Utils.BASE_URL + "delhi,in&unit=metric&appid=87fd8abcd03803d6a80293876f581e3e")).openConnection();
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.setDoInput(true);
                connection.connect();
                StringBuffer stringBuffer = new StringBuffer();
                inputStream = connection.getInputStream();
                Log.e(TAG, "getWeatherData: ");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line + "\r\n");
                }
                inputStream.close();
                connection.disconnect();


                return stringBuffer.toString();
            } catch (Exception e1) {
                Log.e(TAG, "Exception: " + e1.getMessage());
            }

        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        return null;

    }

}
