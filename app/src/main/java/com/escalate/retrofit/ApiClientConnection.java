package com.escalate.retrofit;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by mobulous06 on 19/7/17.
 */

public class ApiClientConnection {

    //ServiceProgressDialog serviceProgressDialog;
    private static ApiClientConnection connect;
    private ApiInterface clientService;
//    public static final String Base_URL = "https://mobulous.app/escalate/api/";
//    public static final String Base_URL = "http://52.62.194.237/escalate/api/";
   // public static final String Base_URL = "http://ec2-54-194-198-217.eu-west-1.compute.amazonaws.com/escalate/api/";
    public static final String Base_URL = "http://escalateapp.co.uk/escalate/api/";
    private static ApiClientConnection apiClientConnection =null;
    private static ApiInterface apiInterface =null,distanceMatrixInterface=null;

    public static ApiClientConnection getInstance()
    {
        if(apiClientConnection == null)
        {
            apiClientConnection = new ApiClientConnection();
        }
        return apiClientConnection;
    }

    public ApiInterface createApiInterface()
    {
        if(apiInterface == null)
        {

            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder()
                    .connectTimeout(80, TimeUnit.SECONDS)
                    .readTimeout(80, TimeUnit.SECONDS);

            httpBuilder.addInterceptor(loggingInterceptor);
//            Gson gson = new GsonBuilder()
//                    .setLenient()
//                    .create();
            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(Base_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpBuilder.build());

            Retrofit retrofit = builder.build();
            apiInterface = retrofit.create(ApiInterface.class);

        }
        return apiInterface;
    }

    public ApiInterface createMapApiInterface() {
        if(distanceMatrixInterface==null){
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder()
                    .connectTimeout(80, TimeUnit.SECONDS)
                    .readTimeout(80, TimeUnit.SECONDS);

            httpBuilder.addInterceptor(loggingInterceptor);
//            Gson gson = new GsonBuilder()
//                    .setLenient()
//                    .create();
            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl("https://maps.googleapis.com/")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .client(httpBuilder.build());

            Retrofit retrofit = builder.build();
            distanceMatrixInterface = retrofit.create(ApiInterface.class);

        }
        return distanceMatrixInterface;
    }
}
