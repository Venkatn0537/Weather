package challenge.android.com.weather.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Praneeth on 11/13/2017.
 */

public class CityInfo implements Serializable {

    //Returns city Id - Not used but s
    @SerializedName("id")
    int cityId;

    //Returns city name
    @SerializedName("name")
    String cityName;

    //Returns list of weather information
    @SerializedName("weather")
    ArrayList<Weather> weatherArrayList;

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public ArrayList<Weather> getWeatherArrayList() {
        return weatherArrayList;
    }

    public void setWeatherArrayList(ArrayList<Weather> weatherArrayList) {
        this.weatherArrayList = weatherArrayList;
    }
}
