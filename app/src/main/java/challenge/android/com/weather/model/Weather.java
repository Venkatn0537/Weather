package challenge.android.com.weather.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Praneeth on 11/14/2017.
 */

public class Weather implements Serializable {
    //Display icon
    @SerializedName("icon")
    String icon;

    //Display Weather description
    @SerializedName("description")
    String description;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
