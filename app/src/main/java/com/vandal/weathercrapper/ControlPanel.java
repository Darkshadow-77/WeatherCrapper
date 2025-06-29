package com.vandal.weathercrapper;

import android.app.Activity;
import android.widget.TextView;
import android.widget.Switch;
import android.widget.SeekBar;
import android.widget.ImageButton;
import android.widget.Toast;
import android.view.View;

public class ControlPanel {
    // Core UI-related data
    private final Activity activity;
    private final String title;

    // Flags to show/hide specific panel sections
    private final boolean panel;
    private final boolean statePanel;
    private final boolean analogPanel;
    private final boolean numericPanel;
    private final boolean lightsPanel;

    // Constructor: initializes the panel with the configuration flags and title
    public ControlPanel(Activity activity, String title, boolean panel, boolean statePanel, boolean analogPanel, boolean numericPanel, boolean lightsPanel) {
        this.activity = activity;
        this.title = title;
        this.panel = panel;
        this.statePanel = statePanel;
        this.analogPanel = analogPanel;
        this.numericPanel = numericPanel;
        this.lightsPanel = lightsPanel;
    }

    // Sets a Switch control and updates a label based on its state (ON/OFF or Ouvert/Fermé if title is 'garage')
    public void setStatePanel(int switchId, int stateId) {
        Switch vSwitch = activity.findViewById(switchId);
        TextView stateLabel = activity.findViewById(stateId);
        vSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!this.title.equalsIgnoreCase("garage")) {
                String stateText = isChecked ? "ON" : "OFF";
                stateLabel.setText(stateText);
            } else {
                String stateText = isChecked ? "Ouvert" : "Fermé";
                stateLabel.setText(stateText);
            }
        });
    }

    // Sets a SeekBar (slider) and updates a label with its current value while also updating the panel label
    public void setAnalogPanel(int seekBarId, int stateId,String unit) {
        SeekBar seekBar = activity.findViewById(seekBarId);
        TextView analogState = activity.findViewById(stateId);
        TextView analogLabel = activity.findViewById(R.id.analog_label);
        analogLabel.setText(this.title); // Display the name of the analog control

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                analogState.setText(String.valueOf(progress)+unit); // Live update
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Optional: can be used to show help, animation, etc.
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Optional: can be used to commit/send value
            }
        });
    }

    // Sets a numeric increment/decrement control with value limits and real-time updates
    public void setNumericPanel(int decId, int incId, int stateId, int min, int max,int step,String unit) {
        ImageButton dec = activity.findViewById(decId);
        ImageButton inc = activity.findViewById(incId);
        TextView numericState = activity.findViewById(stateId);
        TextView numericLabel = activity.findViewById(R.id.numeric_label);
        numericLabel.setText(this.title); // Label reflects the feature (e.g. ventilation)

        final int[] value = {min};
        numericState.setText(String.valueOf(value[0])+unit); // Initial value

        inc.setOnClickListener(v -> {
            if (value[0] < max) {
                value[0]+= step;
                numericState.setText(String.valueOf(value[0]));
            }
        });

        dec.setOnClickListener(v -> {
            if (value[0] > min) {
                value[0]-= step;
                numericState.setText(String.valueOf(value[0]));
            }
        });
    }

    // Combines multiple Switch+Label pairs for lights (e.g., indoors, outdoors)
    public void setLightPanel(
        int switchId1, int labelId1,
        int switchId2, int labelId2,
        int switchId3, int labelId3
    ) {
        setStatePanel(switchId1, labelId1);
        setStatePanel(switchId2, labelId2);
        setStatePanel(switchId3, labelId3);
    }

    // Dynamically sets up the panel components based on the title
    public void setupPanelByTitle() {
        switch (title.toLowerCase()) {
            case "eclairage":
                setLightPanel(
                    R.id.indoors_state_switch, R.id.indoors_state_text,
                    R.id.compound_state_switch, R.id.compound_state_text,
                    R.id.outdoors_state_switch, R.id.outdoors_state_text
                );
                break;

            case "chauffage":
                setStatePanel(R.id.state_switch, R.id.state_text);
                setAnalogPanel(R.id.analog_seekbar, R.id.analog_state,"°C");
                 break;       
            case "climatisation":
                setStatePanel(R.id.state_switch, R.id.state_text);
                setAnalogPanel(R.id.analog_seekbar, R.id.analog_state,"°F");
                break;

            case "ventilation":
                setNumericPanel(R.id.numeric_dec, R.id.numeric_inc, R.id.numeric_state, 0, 5,1,"");
                setAnalogPanel(R.id.analog_seekbar, R.id.analog_state);
                break;

            case "fenetres":
                setNumericPanel(R.id.btn_decrease, R.id.btn_increase, R.id.label_numeric, 0, 100,10,"%");
                break;

            case "garage":
                setStatePanel(R.id.state_switch, R.id.state_text);
                break;

            default:
                Toast.makeText(activity, "Unknown panel type: " + title, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    // Hides a UI component (by ID) if the associated flag is false
    public void checkViewVisibility(boolean state, int id) {
        if (!state) {
            View removable = activity.findViewById(id);
            removable.setVisibility(View.GONE);
        }
    }

    // Sets up the visibility of each panel block and runs the setup logic by title
    public void setPanelLayout() {
        checkViewVisibility(panel, R.id.panel);
        checkViewVisibility(statePanel, R.id.panel_state);
        checkViewVisibility(analogPanel, R.id.panel_analog);
        checkViewVisibility(numericPanel, R.id.panel_numeric);
        checkViewVisibility(lightsPanel, R.id.panel_lights);

        TextView panelTitle = activity.findViewById(R.id.panel_title);
        panelTitle.setText("Réglages/" + title); // Set the top title

        setupPanelByTitle(); // Apply the controls depending on the selected system
    }
}