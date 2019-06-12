package rishab.listview.com.weather10;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import Utils.Utils;
import data.CityPreference;
import data.JsonWeatherParser;
import data.WeatherHttpClient;
import model.Weather;

public class MainActivity extends AppCompatActivity {
    Weather weather = new Weather();
    private TextView cityname, temp, desc, humidity, pressure, wind, sunrise, sunset, updated;
    private ImageView icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cityname = findViewById(R.id.location);
        icon = findViewById(R.id.imageView);
        temp = findViewById(R.id.temp);
        desc = findViewById(R.id.cloudstext);
        humidity = findViewById(R.id.humiditytext);
        pressure = findViewById(R.id.pressuretext);
        wind = findViewById(R.id.windtext);
        sunrise = findViewById(R.id.sunrisetext);
        sunset = findViewById(R.id.sunsettext);
        CityPreference cityPreference = new CityPreference(MainActivity.this);

        renderweatherdata(cityPreference.getCity());
    }

    public void renderweatherdata(String city) {
        Weathertask weathertask = new Weathertask();
        weathertask.execute(city + "&unit=metric&appid=87fd8abcd03803d6a80293876f581e3e");
    }

    private void inputdialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Change City");
        final EditText cityinput = new EditText(MainActivity.this);
        cityinput.setInputType(InputType.TYPE_CLASS_TEXT);
        cityinput.setHint("City Name,Country");
        builder.setView(cityinput);
        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CityPreference cityPreference = new CityPreference(MainActivity.this);
                cityPreference.setCity(cityinput.getText().toString());
                String newCity = cityPreference.getCity();
                renderweatherdata(newCity);
            }
        });
        builder.show();
    }

    public String timeConverter(long unixSeconds) {
        Date date = new java.util.Date(unixSeconds * 1000L);
// the format of your date
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm");
// give a timezone reference for formatting (see comment at the bottom)
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+5:30"));
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.change_city) {
            inputdialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            icon.setImageBitmap(bitmap);
            super.onPostExecute(bitmap);
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            return Image(strings[0]);
        }

        private Bitmap Image(String code) {
            final DefaultHttpClient client = new DefaultHttpClient();
            final HttpGet getrequest = new HttpGet(Utils.BASE_ICON + code + ".png");
            ///  final HttpGet getrequest = new HttpGet("https://s3-ap-southeast-1.amazonaws.com/internshala-uploads/logo/Entrepreneurship+Cell,+IIT+Kanpur_537223.png");

            try {
                HttpResponse response = client.execute(getrequest);
                final int statuscode = response.getStatusLine().getStatusCode();
                if (statuscode != HttpStatus.SC_OK) {
                    Log.e("downloadImage", "error" + statuscode + "   " + Utils.BASE_ICON + code + ".png");
                    return null;

                }
                final HttpEntity entity = response.getEntity();
                if (entity != null) {
                    InputStream inputStream = null;
                    inputStream = entity.getContent();
                    final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    return bitmap;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private class Weathertask extends AsyncTask<String, Void, Weather> {

        @Override
        protected Weather doInBackground(String... strings) {
            String data = ((new WeatherHttpClient()).getWeatherData(strings[0]));

            weather = JsonWeatherParser.getWeather(data);
            weather.iconData = weather.currentcondition1.getIcon();
            Log.v("Data:  ", Objects.requireNonNull(weather).currentcondition1.getDescription());
            new DownloadImage().execute(weather.iconData);
            return weather;
        }

        @Override
        protected void onPostExecute(Weather weather) {
            super.onPostExecute(weather);
            //test
            DateFormat df = DateFormat.getTimeInstance();
            String sunrise1 = timeConverter(weather.place1.getSunrise());
            String sunset1 = timeConverter(weather.place1.getSunset());

            DecimalFormat d = new DecimalFormat("#.#");
            String temp1 = d.format(weather.currentcondition1.getTemperature() - 276);
            cityname.setText(weather.place1.getCity() + "," + weather.place1.getCountry());
            temp.setText(temp1 + " °C");
            wind.setText(weather.wind.getSpeed() + " mph");
            humidity.setText(weather.currentcondition1.getHumidity() + "%");
            pressure.setText(weather.currentcondition1.getPressure() + " hpa");
            desc.setText(weather.currentcondition1.getCondition());
            sunrise.setText(sunrise1);
            sunset.setText(sunset1);

        }
    }
}
