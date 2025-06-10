package com.vandal.weathercrapper;

import android.app.*;
import android.os.*;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.lang.Thread;
import org.json.JSONObject;


public class MainActivity extends Activity 
{
	TextView errorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		errorMessage = findViewById(R.id.error_output);
   		          getWeatherData();
 
		Button myButton = findViewById(R.id.my_button);

		myButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// Code à exécuter au clic
					Toast.makeText(MainActivity.this, "Tu as cliqué le bouton !", Toast.LENGTH_SHORT).show();
				}
			});
		
    }
	private void getWeatherData() {
		new Thread(new Runnable() {
				@Override
				public void run() {
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
						
						WeatherDataDisplayer dsp = new WeatherDataDisplayer(MainActivity.this ,condition,wind,humidity,temperature);
						
						dsp.display(R.id.condition_text,R.id.wind_text,R.id.hum_text,R.id.temp_text);
						

					} catch (Exception e) {
						  						runOnUiThread(()->{
							   	errorMessage.setText("Une erreur est survenue lors de l'appel du service");
						});
					}
				}
			}).start();
	}
	@Override
public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main_menu, menu);
    return true;
}

@Override
public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.action_refresh) {
        getWeatherData(); // ou une autre méthode à appeler
        return true;
    }
    return super.onOptionsItemSelected(item);
}
}

