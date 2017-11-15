package challenge.android.com.weather.view;

import challenge.android.com.weather.model.CityInfo;

/**
 * Created by Praneeth on 11/14/2017.
 */

/**
 * Allows interaction from Presentor to Activity
 */
public interface ISearchView {

    void getUpdatedData(CityInfo cityInfo);
    void onError(Throwable throwable);

}
