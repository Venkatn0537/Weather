package challenge.android.com.weather.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import challenge.android.com.weather.R;
import challenge.android.com.weather.model.CityInfo;
import challenge.android.com.weather.model.Weather;
import challenge.android.com.weather.presentor.SearchPresentor;
import challenge.android.com.weather.utils.Constants;

/**
 * Created by Praneeth on 11/13/2017.
 */

/**
 * Allows user to enter city and displays location and weather information along with
 * weather icon
 */

public class SearchActivity extends AppCompatActivity implements ISearchView {

    private final String TAG = "SearchActivity";
    SearchPresentor searchPresentor;
    //To Display City name
    TextView cityName;
    //To enter city information
    EditText etCitySearch;
    //OnClick to trigger API with city information
    ImageView ivCitySearch;
    //To Display Weather Info
    TextView tvCityWeather;
    //To display Weather Icon
    ImageView ivCityWeatherIcon;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        cityName = findViewById(R.id.tv_city_name);
        etCitySearch = findViewById(R.id.et_city_search);
        ivCitySearch = findViewById(R.id.iv_city_search);
        tvCityWeather = findViewById(R.id.tv_city_weather);
        ivCityWeatherIcon = findViewById(R.id.iv_city_weather_icon);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Create instance for Presentor
        searchPresentor = new SearchPresentor(this);
        //Get savedLocation from Shared Preference
        String savedLocation = searchPresentor.getSavedLocation();
        //If savedLocation is not empty, get Weather info for saved location
        if (!savedLocation.isEmpty()) {
            searchPresentor.fetchCityInfo(savedLocation);
        }
    }

    /**
     * Triggered from Presentor when there is any error during the call
     * @param throwable
     */
    public void onError(Throwable throwable) {
        //Log.e(TAG, throwable.toString());
        //Cannot display Weather Info - no internet, in correct city name, Server down
        Toast.makeText(this, "Cannot display city Weather info!", Toast.LENGTH_SHORT).show();
    }

    /**
     * Triggered from Presentor when API fetches CityInfo
     * @param cityInfo
     */
    public void getUpdatedData(CityInfo cityInfo) {
        //Set City name
        cityName.setText(cityInfo.getCityName());
        //Check if Weather list is empty and display weather info and weather icon
        if (cityInfo.getWeatherArrayList() != null && cityInfo.getWeatherArrayList().size() > 0) {
            Weather weather = cityInfo.getWeatherArrayList().get(0);
            tvCityWeather.setText(weather.getDescription());
            loadWeatherIcon(weather.getIcon());
        }
        //Reset Edit text with empty string
        etCitySearch.setText("");
        //Disable cursor once the information is updated
        etCitySearch.setCursorVisible(false);

    }

    /**
     * Use glide to display icon from generated image url
     * @param icon
     */
    private void loadWeatherIcon(String icon) {
        String imageURL = Constants.BASE_IMAGE_URL + icon + ".png";
        Glide
                .with(this)
                .load(imageURL)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(ivCityWeatherIcon);

    }

    @Override
    protected void onResume() {
        super.onResume();
        final String[] city = new String[1];
        etCitySearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //Assign entered text to city[0]
                city[0] = editable.toString();
            }
        });

        ivCitySearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Use entered city info to fetch information
                searchPresentor.fetchCityInfo(city[0]);
            }
        });
    }

}
