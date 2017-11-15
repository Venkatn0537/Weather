package challenge.android.com.weather.model;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by Praneeth on 11/13/2017.
 */

/**
 * Takes City and APPID to return CityInfo Observable
 */
public interface SearchAPI {

    @Headers({"Content-Type: application/json;charset=UTF-8", "accept: application/json"/*,"Accept-Encoding: gzip, deflate"*/})
    @GET("/data/2.5/weather")
    Observable<CityInfo> getCityWeatherInfo(
            @Query("q") String search_query,
            @Query("APPID") String appId
    );
}
