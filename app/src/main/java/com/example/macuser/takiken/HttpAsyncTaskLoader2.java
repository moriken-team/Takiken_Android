package com.example.macuser.takiken;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by macuser on 15/06/23.
 */


/* ---------- START AsyncTaskLoader（非同期処理）ロード処理 ---------- */

// Http通信でAPIを利用する際の非同期通信クラス（このクラスで非同期処理してデータを受け渡している）
// 型：ArrayList<HashMap>
public class HttpAsyncTaskLoader2 extends AsyncTaskLoader<ArrayList<HashMap>> {
    /** 引数 */
    private HashMap<String, String> requestData;
    private int id;

    /** 非同期処理での結果を格納 */
    private ArrayList<HashMap> returnData;

    public HttpAsyncTaskLoader2(Context context, HashMap<String, String> requestData, int id) {
        super(context);
        this.requestData = requestData;
        this.id = id;
    }

    @Override
    public ArrayList<HashMap> loadInBackground() {// 非同期処理を記述

        String test = String.valueOf(id);// 数値から文字列にキャスト変換
        Log.v("id", test);

        /**
         * id
         * 0：緯度経度の取得（MapsFragment）
         */
        switch (id) {
            case 0:// 緯度経度の取得
                // URL指定
                HttpClient client0 = new DefaultHttpClient();

                // パラメータの設定
                ArrayList<NameValuePair> value0 = new ArrayList<NameValuePair>();
                value0.add( new BasicNameValuePair("kentei_id", "6"));

                String responseData0 = null;

                ArrayList<HashMap> spots = new ArrayList<HashMap>();

                try {
                    String query0 = URLEncodedUtils.format(value0, "UTF-8");
                    HttpGet get0 = new HttpGet("http://sakumon.jp/app/LK_API/spots/index.json" + "?" + query0);

                    // リクエスト送信
                    HttpResponse response = client0.execute(get0);
                    // 取得
                    HttpEntity entity = response.getEntity();
                    responseData0 = EntityUtils.toString(entity, "UTF-8");

//                    jsonParceData0.put("name", responseData5);

                    // jsonパース
                    JSONObject json0 = new JSONObject(responseData0);


                    for (int i = 0; i < json0.getJSONObject("response").getJSONArray("Spots").length(); i++) {// 登録スポット数繰り返す


                        HashMap<String, String> jsonParceData0 = new HashMap<String, String>();

                        jsonParceData0.put("name", json0.getJSONObject("response").getJSONArray("Spots").getJSONObject(i).getJSONObject("Spot").getString("name"));
                        jsonParceData0.put("latitude", json0.getJSONObject("response").getJSONArray("Spots").getJSONObject(i).getJSONObject("Spot").getString("latitude"));
                        jsonParceData0.put("longitude", json0.getJSONObject("response").getJSONArray("Spots").getJSONObject(i).getJSONObject("Spot").getString("longitude"));

                        spots.add(jsonParceData0);



                        Log.v("i >>>>>>>>", spots.get(i).get("name")+"");

                        Log.v("0 >>>>>>>>", ((HashMap<String, String>) spots.get(0)).get("name"));
                        if (1 <= i)
                        Log.v("1 >>>>>>>>", ((HashMap<String, String>) spots.get(1)).get("name"));
                        if (2 <= i)
                        Log.v("2 >>>>>>>>", ((HashMap<String, String>) spots.get(2)).get("name"));
                        if (3 <= i)
                        Log.v("3 >>>>>>>>", ((HashMap<String, String>) spots.get(3)).get("name"));
                        if (4 <= i)
                        Log.v("4 >>>>>>>>", ((HashMap<String, String>) spots.get(4)).get("name"));


                    }
                } catch(IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                returnData = spots;

                break;
        }

        return returnData;
    }

    @Override
    protected void onStartLoading() {// 非同期処理の開始前
        // ActivityまたはFragment復帰時（バックキーで戻る、ホーム画面から戻る等）に再実行されるためここで非同期処理を行うかチェック

        if (returnData != null) {// 結果がnullではないは場合は既に実行済みとして非同期処理は不要
            // deliverResultで結果を送信
            deliverResult(returnData);
        }

        if (takeContentChanged() || returnData == null) {
            // 非同期処理を開始
            forceLoad();
        }
    }


    @Override
    protected void onStopLoading() {// Loader停止時の処理

        // 非同期処理のキャンセル
        cancelLoad();
    }

//    @Override
//    public void deliverResult(String data) {// 登録してあるリスナー（LoaderCallbacksを実装したクラス）に結果を送信
//
//        if (isReset()) {// Loaderがリセット状態かどうか
//            // trueの場合はLoaderがまだ一度も開始していない、resetメソッドが呼ばれている
//            return;
//        }
//
//        mData = data;
//
//        super.deliverResult(data);
//    }

    @Override
    protected void onReset() {
        // reset呼び出し時、Loader破棄時の処理
        super.onReset();

        // Loaderを停止
        onStopLoading();

        // データをクリア
        returnData = null;
    }
}
/* ---------- END AsyncTaskLoader（非同期処理）ロード処理 ---------- */