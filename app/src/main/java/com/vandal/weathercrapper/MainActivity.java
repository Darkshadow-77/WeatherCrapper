package com.vandal.weathercrapper;

import android.app.*;
import android.os.*;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity 
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
		Button myButton = findViewById(R.id.my_button);

		myButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// Code à exécuter au clic
					Toast.makeText(MainActivity.this, "Tu as cliqué le bouton !", Toast.LENGTH_SHORT).show();
				}
			});
		
    }
}
