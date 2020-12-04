package in.co.vsys.myssksamaj.network1;

import java.util.concurrent.TimeUnit;

import in.co.vsys.myssksamaj.model.rest_api.APIConstants;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BusinessAPIClient {

    //private static Retrofit retrofit=null;

    private static Retrofit instance;

    public static Retrofit getInstance() {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .build();

        if (instance == null) {
            instance = new Retrofit.Builder()
                    .baseUrl(APIConstants.BUSINESS_SERVER)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return instance;
    }
}
