package com.example.examplemod.http;

import com.example.examplemod.utils.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.List;

public class APIResponse {

    public static final String baseUrl = "https://api.prj5g-logger.sysken.net/";
    private static final Logger LOGGER = LogManager.getLogger();

    public void postPingData(int ping, String type) {
        Retrofit retro = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface service = retro.create(ApiInterface.class);

        ArrayList<NetworkData> networkDataList = new ArrayList<>();
        NetworkData networkData = new NetworkData(
                DateUtils.getTimeStamp(),
                ping,
                0,
                0
        );
        networkDataList.add(networkData);

        Call btc = service.postNetworkPing(type,networkDataList);

        btc.enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                if (response.isSuccessful()) {
                    // 通信結果を受け取る
                    var body = response.body();
                    LOGGER.info("response: {}", body);

                } else {
                    // 通信が成功したが、エラーcodeが返ってきた時
                    LOGGER.error("error_code: {}", response.code());
                }
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull Throwable t) {
                // 通信が失敗した時
                LOGGER.error("error: {}", t.getMessage());
            }
        });
    }
}
