package network;

import com.squareup.okhttp.OkHttpClient;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;
import utils.Constants;

import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

/**
 * Created by Maaz on 5/13/2015.
 */
public class RestClient {

    private static ApiInterface mApiInterface;

    public RestClient(){

    }

    static{
        setUpRestClient();
    }

    public static void setUpRestClient() {

        RequestInterceptor interceptor = new RequestInterceptor(){

            @Override
            public void intercept(RequestInterceptor.RequestFacade request) {

                request.addHeader("Content-Type","application/json");
                request.addHeader("Accept","application/json");
//                request.addHeader(Constants.PARAM_AUTHORIZATION,DevicePreferences.getInstance().getClientKey());
                request.addHeader("Authorization","7ea442c9-2fca-405f-94f6-4ebb94484f01");
            }
        };

        OkHttpClient okHttpClient=new OkHttpClient();

        okHttpClient.setReadTimeout(Constants.TIMEOUT, TimeUnit.MILLISECONDS);
        okHttpClient.setConnectTimeout(Constants.TIMEOUT, TimeUnit.MILLISECONDS);



        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Constants.BASE_URL_PROD)
                .setRequestInterceptor(interceptor)
                .setLogLevel(RestAdapter.LogLevel.FULL)
               // .setConverter(new EmptyConverter())
                .setClient(new OkClient(okHttpClient))
                .build();

        mApiInterface = restAdapter.create(ApiInterface.class);

    }

    public static ApiInterface getAdapter(){

        return mApiInterface;
    }

}
