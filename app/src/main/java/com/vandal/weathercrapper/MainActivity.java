package com.vandal.weathercrapper;

import android.app.*;
import android.os.*;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.lang.Thread;


public class MainActivity extends Activity 
{
	String weatherData ="";
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
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
						String apiKey = "TA_CLE_API"; // remplace ici avec ta clé personnelle
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

						weatherData = jsonData;

						// Tu peux ensuite faire un runOnUiThread() ici pour afficher les données

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();
	}
}

