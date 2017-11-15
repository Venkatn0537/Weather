package challenge.android.com.weather.presentor;

import android.content.Context;

import challenge.android.com.weather.model.CityInfo;
import challenge.android.com.weather.model.SearchManager;
import challenge.android.com.weather.model.SharedPreferenceHelper;
import challenge.android.com.weather.utils.Constants;
import challenge.android.com.weather.utils.Utility;
import challenge.android.com.weather.view.ISearchView;

/**
 * Created by Praneeth on 11/14/2017.
 */

/**
 * Search Presentor to handle fetch City Info from Search Manager, get Saved Location
 */

public class SearchPresentor implements ISearchPresentor {

    //View to communicate between activity and Presentor
    private ISearchView searchView;
    private Context context;

    public SearchPresentor(Context context) {
        this.context = context;
        this.searchView = (ISearchView) context;
    }

    /**
     * Interaction between Presentor and Model using RxJava Consumer
     *
     * @param city
     */
    @Override
    public void fetchCityInfo(String city) {
        //Check if user is online and fetch city info else trigger onError
        if (Utility.isOnline(context)) {
            SearchManager searchManager = new SearchManager(context);
            searchManager.getWeatherInfo(city, this::getUpdatedData, this::onError);
        } else
            onError(null);
    }

    @Override
    public String getSavedLocation() {
        //Return savedLocation from shared preference
        return SharedPreferenceHelper.with(context).getValue(Constants.LOCATION);
    }

    /**
     * Triggered form RxJava getWeatherInfo call
     * @param throwable
     */
    private void onError(Throwable throwable) {
        //Triggers view with throwable info
        searchView.onError(throwable);
    }

    /**
     * Triggered from RXJava getWeatherInfo call
     * @param cityInfo
     */
    private void getUpdatedData(CityInfo cityInfo) {
        //Triggers View with cityInfo
        searchView.getUpdatedData(cityInfo);
    }
}
