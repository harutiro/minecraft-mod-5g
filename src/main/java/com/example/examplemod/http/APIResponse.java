package com.example.examplemod.http;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;

public class APIResponse {
    public static final String baseUrl = "https://api.prj5g-logger.sysken.net/";
    private static final Logger LOGGER = LogManager.getLogger();

    public void postPingData(int ping, String type) {
        // 保存するログの情報を作成
        long currentTime = Instant.now().getEpochSecond();
        String jsonBody = "[{\"time\": " + currentTime + ", \"ping\": " + ping + ", \"down\": 0, \"up\": 0}]";

        // APIのURLを指定
        String apiUrl = baseUrl + "api/network/" + type; // APIの実際のURLに置き換える

        try {
            // URLオブジェクトを作成
            URL url = new URL(apiUrl);

            // HttpURLConnectionを作成
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // リクエストボディを書き込む
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonBody.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // レスポンスを取得
            int responseCode = conn.getResponseCode();
            LOGGER.info("HTTPステータスコード: " + responseCode);

            // 接続を閉じる
            conn.disconnect();
        } catch (Exception e) {
            LOGGER.error("POSTリクエスト送信中にエラーが発生しました。", e);
        }
    }
}
