package model;

public class currentcondition {

    private int weatherid;
    private String condition;
    private String description;
    private String icon;
    private float pressure;
    private float humidity;
    private float maxtemp;
    private float mintemp;
    private double temperature;

    public int getWeatherid() {
        return weatherid;
    }

    public void setWeatherid(int weatherid) {
        this.weatherid = weatherid;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public float getMaxtemp() {
        return maxtemp;
    }

    public void setMaxtemp(float maxtemp) {
        this.maxtemp = maxtemp;
    }

    public float getMintemp() {
        return mintemp;
    }

    public void setMintemp(float mintemp) {
        this.mintemp = mintemp;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
}
