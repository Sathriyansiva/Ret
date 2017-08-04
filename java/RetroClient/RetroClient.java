package RetroClient;

import APIInterface.CategoryAPI;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by system9 on 6/30/2017.
 */

public class RetroClient {
    private static final String ROOT_URL = "http://gatesdelhi.brainmagicllc.com///api/LucasTvs/";
    /**
     * Get Retrofit Instance
     */
    private static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    /**
     * Get API Service
     *
     * @return API Service
     */
    public static CategoryAPI getApiService() {
        return getRetrofitInstance().create(CategoryAPI.class);
    }
}
