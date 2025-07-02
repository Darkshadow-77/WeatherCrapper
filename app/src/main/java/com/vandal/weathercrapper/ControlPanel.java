package com.vandal.weathercrapper;

import android.app.Activity;
import android.widget.TextView;
import android.widget.Switch;
import android.widget.SeekBar;
import android.widget.ImageButton;
import android.widget.Toast;
import android.view.View;

import java.util.function.Supplier;
import java.util.function.Consumer;
import java.util.function.IntSupplier;
import java.util.function.IntConsumer;

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
    
    //Setting data manager
    UserData data = UserData.getInstance();

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
     /*Suppliers and consummers on panels setters
      * are used to retrieve getters 
      * and setters from UserData
      */
    // Sets a Switch control and updates a label based on its state (ON/OFF or Ouvert/Fermé if title is 'garage')
    private void setStateLabel(TextView stateLabel,boolean isChecked){
        if (!this.title.equalsIgnoreCase("garage")) {
                String stateText = isChecked ? "ON" : "OFF";
                stateLabel.setText(stateText);
            } else {
                String stateText = isChecked ? "Ouvert" : "Fermé";
                stateLabel.setText(stateText);
            }
    }
    public void setStatePanel(Supplier<Boolean> getter,Consumer<Boolean> setter,int switchId, int stateId) {
        Switch vSwitch = activity.findViewById(switchId);
        vSwitch.setChecked(getter.get());
        TextView stateLabel = activity.findViewById(stateId);
        vSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            setter.accept(isChecked);
            setStateLabel(stateLabel,isChecked);
        });
    }

    // Sets a SeekBar (slider) and updates a label with its current value while also updating the panel label
    public void setAnalogPanel(IntSupplier getter,IntConsumer setter,int seekBarId, int stateId,String unit) {
        SeekBar seekBar = activity.findViewById(seekBarId);
        seekBar.setProgress(getter.getAsInt());
        TextView analogState = activity.findViewById(stateId);
        analogState.setText(String.valueOf(getter.getAsInt())+unit);
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
                // Used to commit/send value
                int prog = seekBar.getProgress();
                setter.accept(prog);
            }
        });
    }

    // Sets a numeric increment/decrement control with value limits and real-time updates
    public void setNumericPanel(IntSupplier getter,IntConsumer setter,int decId, int incId, int stateId, int min, int max,int step,String unit) {
        ImageButton dec = activity.findViewById(decId);
        ImageButton inc = activity.findViewById(incId);
        TextView numericState = activity.findViewById(stateId);
        numericState.setText(String.valueOf(getter.getAsInt())+unit);
        TextView numericLabel = activity.findViewById(R.id.numeric_label);
        numericLabel.setText(this.title); // Label reflects the feature (e.g. ventilation)

        final int[] value = {getter.getAsInt()};

        inc.setOnClickListener(v -> {
            if (value[0] < max) {
                value[0]+= step;
                numericState.setText(String.valueOf(value[0])+unit);
                setter.accept(value[0]);
            }
        });

        dec.setOnClickListener(v -> {
            if (value[0] > min) {
                value[0]-= step;
                numericState.setText(String.valueOf(value[0])+unit);
                setter.accept(value[0]);
            }
        });
    }

    // Combines multiple Switch+Label pairs for lights (e.g., indoors, outdoors)
    public void setLightPanel(
        Supplier<Boolean> getter0,Consumer<Boolean> setter0,int switchId1, int labelId1,
        Supplier<Boolean> getter1,Consumer<Boolean> setter1,int switchId2, int labelId2,
        Supplier<Boolean> getter2,Consumer<Boolean> setter2,int switchId3, int labelId3
    ) {
        setStatePanel(getter0,setter0,switchId1, labelId1);
        setStatePanel(getter1,setter1,switchId2, labelId2);
        setStatePanel(getter2,setter2,switchId3, labelId3);
    }

    // Dynamically sets up the panel components based on the title
    public void setupPanelByTitle() {
        switch (title.toLowerCase()) {
            case "éclairage":
                setLightPanel(
                    data::isIndoorsLightOn,data::setIndoorsLightOn,R.id.indoors_state_switch, R.id.indoors_state_text,
                    data::isCompoundLightOn,data::setCompoundLightOn,R.id.compound_state_switch, R.id.compound_state_text,
                    data::isOutdoorsLightOn,data::setOutdoorsLightOn,R.id.outdoors_state_switch, R.id.outdoors_state_text
                );
                break;

            case "chauffage":
                setStatePanel(data::isHeaterState,data::setHeaterState,R.id.state_switch, R.id.state_text);
                setAnalogPanel(data::getHeaterTemp,data::setHeaterTemp,R.id.analog_seekbar, R.id.analog_state,"°C");
                 break;       
            case "climatisation":
                setStatePanel(data::isAcState,data::setAcState,R.id.state_switch, R.id.state_text);
                setAnalogPanel(data::getAcTemp,data::setAcTemp,R.id.analog_seekbar, R.id.analog_state,"°F");
                break;

            case "ventilation":
                setNumericPanel(data::getFanSpeed,data::setFanSpeed,R.id.numeric_dec, R.id.numeric_inc, R.id.numeric_state, 0, 5,1,"");
                setStatePanel(data::isFanState,data::setFanState,R.id.state_switch, R.id.state_text);
                break;

            case "fenêtres":
                setNumericPanel(data::getWindowOpenPercent, data::setWindowOpenPercent,R.id.numeric_dec, R.id.numeric_inc, R.id.numeric_state, 0, 100,10,"%");
                break;

            case "garage":
                setStatePanel(data::isGarageOpen,data::setGarageOpen,R.id.state_switch, R.id.state_text);
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
        }else{
            View addable = activity.findViewById(id);
            addable.setVisibility(View.VISIBLE);
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