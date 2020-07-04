package com.example.temhumi.model;

public class TemHumiObject {
    // time format: "date:month:year:hour:minute:second"
    private String Time;
    private float Temperature;
    private float Humidity;

    public TemHumiObject() {
    }

    public TemHumiObject(String Time, float Temperature, float Humidity) {
        this.Time = Time;
        this.Temperature = Temperature;
        this.Humidity = Humidity;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public float getTemperature() {
        return Temperature;
    }

    public void setTemperature(float temperature) {
        Temperature = temperature;
    }

    public float getHumidity() {
        return Humidity;
    }

    public void setHumidity(float humidity) {
        Humidity = humidity;
    }

    @Override
    public String toString() {
        return "TemHumiObject{" +
                "Time='" + Time + '\'' +
                ", Temperature=" + Temperature +
                ", Humidity=" + Humidity +
                '}';
    }
}
