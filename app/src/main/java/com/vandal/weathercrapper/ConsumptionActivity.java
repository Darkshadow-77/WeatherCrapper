package com.vandal.weathercrapper;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ConsumptionActivity extends Activity {

    // Simulons des données de consommation
    private final double lightingKWh = 1.2;
    private final double heatingKWh = 3.6;
    private final double coolingKWh = 2.1;
    private final double fanKWh = 0.9;
    private final double windowsKWh = 0.5;

    private final double kWhPrice = 0.20; // Exemple : 0.20 €/kWh

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consumption);

        updateConsumptionDisplay();
    }

    private void updateConsumptionDisplay() {
        setItem(R.id.lighting_text, R.id.lighting_bar, lightingKWh);
        setItem(R.id.heating_text, R.id.heating_bar, heatingKWh);
        setItem(R.id.cooling_text, R.id.cooling_bar, coolingKWh);
        setItem(R.id.fan_text, R.id.fan_bar, fanKWh);
        setItem(R.id.windows_text, R.id.windows_bar, windowsKWh);

        double total = lightingKWh + heatingKWh + coolingKWh + fanKWh + windowsKWh;
        double cost = total * kWhPrice;

        TextView totalText = findViewById(R.id.total_text);
        totalText.setText(String.format("Total: %.2f kWh (%.2f €)", total, cost));
    }

    private void setItem(int textId, int barId, double value) {
        TextView label = findViewById(textId);
        ProgressBar bar = findViewById(barId);

        label.setText(String.format("%.2f kWh", value));
        bar.setProgress((int)(value * 10)); // Suppose une barre max à 100
    }
}