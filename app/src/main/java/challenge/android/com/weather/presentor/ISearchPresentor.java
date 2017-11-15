package challenge.android.com.weather.presentor;

/**
 * Created by Praneeth on 11/14/2017.
 */

/**
 * Allows interaction from Activity to Presentor
 */
public interface ISearchPresentor {
    void fetchCityInfo(String city);
    String getSavedLocation();
}
