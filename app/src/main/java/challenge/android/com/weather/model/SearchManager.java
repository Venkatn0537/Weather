package challenge.android.com.weather.model;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import challenge.android.com.weather.utils.Constants;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Praneeth on 11/13/2017.
 */

public class SearchManager {

    SearchAPI searchAPI;
    Context context;

    public SearchManager(Context context) {
        this.context = context;
        Retrofit retrofit = provideRetrofit();
        searchAPI = retrofit.create(SearchAPI.class);
    }

    /**
     * Provide Retrofit Instance using GSON builder
     * @return
     */
    private Retrofit provideRetrofit() {
        Gson gson = new GsonBuilder()
                .create();
        return new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(Constants.BASE_URL)
                .client(provideOkHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    /**
     * Provide OkHttpClient instance
     * @return
     */
    private OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor())
                .readTimeout(0, TimeUnit.SECONDS)
                .build();
    }

    /**
     * Provide HttpLoggingInterceptor
     * @return
     */
    private HttpLoggingInterceptor loggingInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> Log.i("Network Module", message));
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }

    /**
     * Provides City info using Retrofit Combination with Rxjava
     * Map operator is used to save Location in Shared Preference
     * @param city
     * @param onNext
     * @param onError
     */
    public void getWeatherInfo(String city, Consumer<CityInfo> onNext, Consumer<? super Throwable> onError) {

        Observable<CityInfo> observable = searchAPI.getCityWeatherInfo(city, Constants.APP_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .map(this::saveToSharedPreference)
                .observeOn(AndroidSchedulers.mainThread());

        observable.subscribe(onNext, onError);

    }

    /**
     * Save City to SharedPreference
     * @param cityInfo
     * @return
     */
    private CityInfo saveToSharedPreference(CityInfo cityInfo) {

        SharedPreferenceHelper helper = SharedPreferenceHelper.with(context);
        helper.setValue(Constants.LOCATION,cityInfo.getCityName());

        return cityInfo;
    }
}
