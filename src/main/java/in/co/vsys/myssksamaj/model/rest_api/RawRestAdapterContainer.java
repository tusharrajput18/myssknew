package in.co.vsys.myssksamaj.model.rest_api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RawRestAdapterContainer {

    private static Retrofit instance;

    public static Retrofit getInstance() {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .build();

      /*  Gson gson = new GsonBuilder()
                .setLenient()
                .create();*/

        if (instance == null) {
            instance = new Retrofit.Builder()
                    .baseUrl(APIConstants.SERVER_ENDPOINT_1)
                    .client(okHttpClient)
                    .addConverterFactory(/*GsonConverterFactory.create(gson)*/ScalarsConverterFactory.create())
//                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return instance;
    }
}