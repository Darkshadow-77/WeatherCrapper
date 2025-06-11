package com.vandal.weathercrapper;

import android.app.Activity;
import android.widget.TextView;
import android.widget.Toast;

public class WeatherDataDisplayer {
    
    private final Activity activity;
    private final String condition;
    private final String wind;
    private final String humidity;
    private final String temperature;
    
    public WeatherDataDisplayer(
        Activity activity,
        String condition,
        String wind,
        String humidity,
        String temperature
    ){
        this.activity = activity;
        this.condition = condition;
        this.wind = wind;
        this.humidity = humidity;
        this.temperature = temperature;
    }
    
    public void display(int condId,int winId,int humId,int tempId){
        activity.runOnUiThread(()->{
            TextView condTextView = activity.findViewById(condId);
            TextView winTextView = activity.findViewById(winId);
            TextView humTextView = activity.findViewById(humId);
            TextView tempTextView = activity.findViewById(tempId);
            
            condTextView.setText(" "+condition);
            winTextView.setText(" Vent :"+wind+" mph");
            humTextView.setText(" Humidité :"+humidity+"%");
            tempTextView.setText(" Température :"+temperature+" °C");

            Toast.makeText(activity,"Chargé avec succès",Toast.LENGTH_SHORT).show();
            
        });
        
    }
    
}