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
 * Created by macuser on 15/05/13.
 */


/* ---------- START AsyncTaskLoader（非同期処理）ロード処理 ---------- */

//Http通信でAPIを利用する際の非同期通信クラス（このクラスで非同期処理してデータを受け渡している）
public class HttpAsyncTaskLoader extends AsyncTaskLoader<HashMap<String, String>> {

    /** 引数 */
    private HashMap<String, String> requestData;
    private int id;

    /** 非同期処理での結果を格納 */
    private HashMap<String, String> returnData;

    public HttpAsyncTaskLoader(Context context, HashMap<String, String> requestData, int id) {
        super(context);
        this.requestData = requestData;
        this.id = id;
    }

    @Override
    public HashMap<String, String> loadInBackground() {// 非同期処理を記述

        String test = String.valueOf(id);// 数値から文字列にキャスト変換
        Log.v("id", test);

        /**
         * id
         * 0：一問一答の問題作成（QuestionsAndAnswersFragment）
         * 1：選択問題の問題作成（MultipleChoiceFragment）
         * 2：カテゴリ選択で問題解答（SelectCategoryFragment）
         */
        switch (id){
            case 0:// 一問一答の問題作成
                HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost("http://sakumon.jp/app/LK_API/problems/add.json");
                // パラメータの設定
                ArrayList<NameValuePair> value = new ArrayList<NameValuePair>();
                value.add( new BasicNameValuePair("kentei_id", "1"));
                value.add( new BasicNameValuePair("user_id", "1"));
                value.add( new BasicNameValuePair("type", "2"));
                value.add( new BasicNameValuePair("grade", "1"));
                value.add( new BasicNameValuePair("number", "1"));
                value.add( new BasicNameValuePair("sentence", requestData.get("question")));
                value.add( new BasicNameValuePair("right_answer", requestData.get("answer")));
                value.add( new BasicNameValuePair("description", "012345"));
                value.add( new BasicNameValuePair("public_flag", "1"));
                value.add( new BasicNameValuePair("category_id", requestData.get("category")));
                value.add( new BasicNameValuePair("item", "1"));

                String responseData = null;

                try {
                    post.setEntity(new UrlEncodedFormEntity(value, "UTF-8"));
                    // リクエスト送信
                    HttpResponse response = client.execute(post);
                    // 取得
                    HttpEntity entity = response.getEntity();
                    responseData = EntityUtils.toString(entity, "UTF-8");
                } catch(IOException e) {
                    e.printStackTrace();
                }

                Log.v("mArg", requestData.get("category"));
                Log.v("mArg", requestData.get("question"));
                Log.v("mArg", requestData.get("answer"));

//                returnData = responseData;

                break;
            case 1:// 選択問題の問題作成
                HttpClient client2 = new DefaultHttpClient();
                HttpPost post2 = new HttpPost("http://sakumon.jp/app/LK_API/problems/add.json");
                // パラメータの設定
                ArrayList<NameValuePair> value2 = new ArrayList<NameValuePair>();
                value2.add( new BasicNameValuePair("kentei_id", "1"));
                value2.add( new BasicNameValuePair("user_id", "1"));
                value2.add( new BasicNameValuePair("type", "1"));
                value2.add( new BasicNameValuePair("grade", "1"));
                value2.add( new BasicNameValuePair("number", "1"));
                value2.add( new BasicNameValuePair("sentence", requestData.get("question")));
                value2.add( new BasicNameValuePair("right_answer", requestData.get("answer")));
                value2.add( new BasicNameValuePair("wrong_answer1", requestData.get("incorrect1")));
                value2.add( new BasicNameValuePair("wrong_answer2", requestData.get("incorrect2")));
                value2.add( new BasicNameValuePair("wrong_answer3", requestData.get("incorrect3")));
                value2.add( new BasicNameValuePair("description", "012345"));
                value2.add( new BasicNameValuePair("public_flag", "1"));
                value2.add( new BasicNameValuePair("category_id", requestData.get("category")));
                value2.add( new BasicNameValuePair("item", "1"));

                String responseData2 = null;

                try {
                    post2.setEntity(new UrlEncodedFormEntity(value2, "UTF-8"));
                    // リクエスト送信
                    HttpResponse response = client2.execute(post2);
                    // 取得
                    HttpEntity entity = response.getEntity();
                    responseData2 = EntityUtils.toString(entity, "UTF-8");
                } catch(IOException e) {
                    e.printStackTrace();
                }

//                returnData = responseData2;

                Log.v("mArg", requestData.get("category"));
                Log.v("mArg", requestData.get("question"));
                Log.v("mArg", requestData.get("answer"));
                Log.v("mArg", requestData.get("incorrect1"));
                Log.v("mArg", requestData.get("incorrect2"));
                Log.v("mArg", requestData.get("incorrect3"));

                break;
            case 2:// カテゴリ選択で問題解答
                // URL指定
                HttpClient client3 = new DefaultHttpClient();

                // パラメータの設定
                ArrayList<NameValuePair> value3 = new ArrayList<NameValuePair>();
                value3.add( new BasicNameValuePair("kentei_id", "1"));
                value3.add( new BasicNameValuePair("employ", "2012"));
                value3.add( new BasicNameValuePair("grade", "3"));
                value3.add( new BasicNameValuePair("category_id", "1"));
                value3.add( new BasicNameValuePair("item", "1"));

                String responseData3 = null;
                HashMap<String, String> jsonParceData3 = new HashMap<String, String>();

                try {
                    String query = URLEncodedUtils.format(value3, "UTF-8");
                    HttpGet get = new HttpGet("http://sakumon.jp/app/LK_API/problems/index.json" + "?" + query);

                    // リクエスト送信
                    HttpResponse response = client3.execute(get);
                    // 取得
                    HttpEntity entity = response.getEntity();
                    responseData3 = EntityUtils.toString(entity, "UTF-8");


                    // jsonパース
                    JSONObject json3 = new JSONObject(responseData3);

                    jsonParceData3.put("sentence", json3.getJSONObject("response").getJSONArray("Problems").getJSONObject(0).getJSONObject("Problem").getString("sentence"));
                    jsonParceData3.put("right_answer", json3.getJSONObject("response").getJSONArray("Problems").getJSONObject(0).getJSONObject("Problem").getString("right_answer"));
                    jsonParceData3.put("wrong_answer1", json3.getJSONObject("response").getJSONArray("Problems").getJSONObject(0).getJSONObject("Problem").getString("wrong_answer1"));
                    jsonParceData3.put("wrong_answer2", json3.getJSONObject("response").getJSONArray("Problems").getJSONObject(0).getJSONObject("Problem").getString("wrong_answer2"));
                    jsonParceData3.put("wrong_answer3", json3.getJSONObject("response").getJSONArray("Problems").getJSONObject(0).getJSONObject("Problem").getString("wrong_answer3"));

                    Log.v("sentence >>>>>>>>", jsonParceData3.get("sentence"));
                    Log.v("right_answer >>>>>>>>", jsonParceData3.get("right_answer"));
                    Log.v("wrong_answer1 >>>>>>>>", jsonParceData3.get("wrong_answer1"));
                    Log.v("wrong_answer2 >>>>>>>>", jsonParceData3.get("wrong_answer2"));
                    Log.v("wrong_answer3 >>>>>>>>", jsonParceData3.get("wrong_answer3"));




                } catch(IOException e) {
                    e.printStackTrace();
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }

                returnData = jsonParceData3;


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

