package com.vandal.weathercrapper;

import android.app.*;
import android.os.*;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuItem;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class MainActivity extends Activity {

    private ControlPanel controlPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        getWeatherData();

        findViewById(R.id.light_btn).setOnClickListener(v -> {
            controlPanel = new ControlPanel(this, "Éclairage", true, false, false, false, true);
            controlPanel.setPanelLayout();
           // PanelAnimatorV2.slideInFromBottom(findViewById(R.id.panel), 300);
        });

        findViewById(R.id.heat_btn).setOnClickListener(v -> {
            controlPanel = new ControlPanel(this, "Chauffage", true, true, true, false, false);
            controlPanel.setPanelLayout();
           // PanelAnimatorV2.slideInFromBottom(findViewById(R.id.panel), 300);
        });

        findViewById(R.id.cold_btn).setOnClickListener(v -> {
            controlPanel = new ControlPanel(this, "Climatisation", true, true, true, false, false);
            controlPanel.setPanelLayout();
            //PanelAnimatorV2.slideInFromBottom(findViewById(R.id.panel), 300);
        });

        findViewById(R.id.fan_btn).setOnClickListener(v -> {
            controlPanel = new ControlPanel(this, "Ventilation", true, false, true, true, false);
            controlPanel.setPanelLayout();
            //PanelAnimatorV2.slideInFromBottom(findViewById(R.id.panel), 300);
        });

        findViewById(R.id.win_btn).setOnClickListener(v -> {
            controlPanel = new ControlPanel(this, "Fenêtres", true, false, false, true, false);
            controlPanel.setPanelLayout();
            //PanelAnimatorV2.slideInFromBottom(findViewById(R.id.panel), 300);
        });

        findViewById(R.id.gar_btn).setOnClickListener(v -> {
            controlPanel = new ControlPanel(this, "Garage", true, true, false, false, false);
            controlPanel.setPanelLayout();
           // PanelAnimatorV2.slideInFromBottom(findViewById(R.id.panel), 300);
        });

        findViewById(R.id.close_btn).setOnClickListener(v -> {
            //PanelAnimatorV2.slideOutToBottom(findViewById(R.id.panel), 300);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_refresh) {
            getWeatherData();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getWeatherData() {
        new Thread(() -> {
            try {
                String apiKey = "eb6ebf16116a402a804183302250606"; // remplace ici avec ta clé personnelle
                String city = "Paris";
                String urlString = "https://api.weatherapi.com/v1/current.json?key=" + apiKey + "&q=" + city + "&aqi=no";

                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                InputStream inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();

                String jsonData = response.toString();

                JSONObject obj = new JSONObject(jsonData);
                JSONObject current = obj.getJSONObject("current");

                String condition = current.getJSONObject("condition").getString("text");
                String wind = current.getString("wind_mph");
                String humidity = current.getString("humidity");
                String temperature = current.getString("temp_c");

                WeatherDataDisplayer dsp = new WeatherDataDisplayer(MainActivity.this, condition, wind, humidity, temperature);

                dsp.display(R.id.condition_text, R.id.wind_text, R.id.hum_text, R.id.temp_text);

            } catch (Exception e) {
                runOnUiThread(() -> {
                    Toast.makeText(MainActivity.this, "Une erreur est survenue lors du chargement du service", Toast.LENGTH_SHORT).show();
                });
            }
        }).start();
    }
}