package com.vandal.weathercrapper;

public class UserData {
    // Singleton instance
    private static UserData instance;

    // === Stored User Inputs ===
    private boolean lightsOn;
    private boolean indoorsLightOn;
    private boolean outdoorsLightOn;
    private boolean compoundLightOn;

    private boolean heaterState;
    private int heaterTemp;

    private boolean acState;
    private int acTemp;

    private int fanSpeed;
    private boolean fanState;

    private int windowOpenPercent;

    private boolean garageOpen;

    // === Constructor is private (singleton) ===
    private UserData() {
        //Setting default values
        lightsOn = false;
        indoorsLightOn = false;
        outdoorsLightOn = false;
        compoundLightOn = false;
        heaterState = false;
        heaterTemp = 20;
        acTempState = false;
        acTemp = 22;
        fanState = false;
        fanSpeed = 0;
        windowOpenPercent = 0;
    }

    // === Get singleton instance ===
    public static UserData getInstance() {
        if (instance == null) {
            instance = new UserData();
        }
        return instance;
    }

    // === Getters and Setters ===
    public boolean isLightsOn() {
        return lightsOn;
    }

    public void setLightsOn(boolean lightsOn) {
        this.lightsOn = lightsOn;
    }

    public boolean isIndoorsLightOn() {
        return indoorsLightOn;
    }

    public void setIndoorsLightOn(boolean indoorsLightOn) {
        this.indoorsLightOn = indoorsLightOn;
    }

    public boolean isOutdoorsLightOn() {
        return outdoorsLightOn;
    }

    public void setOutdoorsLightOn(boolean outdoorsLightOn) {
        this.outdoorsLightOn = outdoorsLightOn;
    }

    public boolean isCompoundLightOn() {
        return compoundLightOn;
    }

    public void setCompoundLightOn(boolean compoundLightOn) {
        this.compoundLightOn = compoundLightOn;
    }

    public boolean isHeaterState() {
        return heaterState;
    }

    public void setHeaterState(boolean heaterState) {
        this.heaterState = heaterState;
    }

    public int getHeaterTemp() {
        return heaterTemp;
    }

    public void setHeaterTemp(int heaterTemp) {
        this.heaterTemp = heaterTemp;
    }

    public boolean isAcState() {
        return acState;
    }

    public void setAcState(boolean acState) {
        this.acState = acState;
    }

    public int getAcTemp() {
        return acTemp;
    }

    public void setAcTemp(int acTemp) {
        this.acTemp = acTemp;
    }

    public int getFanSpeed() {
        return fanSpeed;
    }

    public void setFanSpeed(int fanSpeed) {
        this.fanSpeed = fanSpeed;
    }

    public boolean isFanState() {
        return fanState;
    }

    public void setFanState(boolean fanState) {
        this.fanState = fanState;
    }

    public int getWindowOpenPercent() {
        return windowOpenPercent;
    }

    public void setWindowOpenPercent(int windowOpenPercent) {
        this.windowOpenPercent = windowOpenPercent;
    }

    public boolean isGarageOpen() {
        return garageOpen;
    }

    public void setGarageOpen(boolean garageOpen) {
        this.garageOpen = garageOpen;
    }
}