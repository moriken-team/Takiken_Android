package com.example.macuser.takiken;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.text.TextUtils;
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

// Http通信でAPIを利用する際の非同期通信クラス（このクラスで非同期処理してデータを受け渡している）
// 型：HashMap<String, String>
public class HttpAsyncTaskLoader extends AsyncTaskLoader<HashMap<String, String>> {

    /** 引数 */
    private HashMap<String, String> requestData;
    private int id;

    /** 非同期処理での結果を格納 */
    private HashMap<String, String> returnData;
    private HashMap<String, HashMap> returnData2;

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
         * 0：一問一答の問題作成（MakingQandAFragment）
         * 1：選択問題の問題作成（MakingMultipleChoiceFragment）
         * 2：カテゴリ選択で問題解答（SelectCategoryFragment、CategoryQuizResultFragment）
         * 3：ランダムで問題解答（SelectRandomFragment、RandomQuizResultFragment）
         * 4：ログイン（）
         * 5：ユーザ登録（UserAddActivity）
         * 6：マップでの一問一答の問題作成（MapsMakingQandAFragment）
         */
        switch (id) {
            case 0:// 一問一答の問題作成
                HttpClient client0 = new DefaultHttpClient();
                HttpPost post0 = new HttpPost("http://sakumon.jp/app/LK_API/problems/add.json");
                // パラメータの設定
                ArrayList<NameValuePair> value0 = new ArrayList<NameValuePair>();
                value0.add( new BasicNameValuePair("kentei_id", "6"));
                value0.add( new BasicNameValuePair("user_id", "1"));
                value0.add( new BasicNameValuePair("type", "2"));
                value0.add( new BasicNameValuePair("grade", "1"));
                value0.add( new BasicNameValuePair("number", "1"));
                value0.add( new BasicNameValuePair("sentence", requestData.get("question")));
                value0.add( new BasicNameValuePair("right_answer", requestData.get("answer")));
                value0.add( new BasicNameValuePair("description", "012345"));
                value0.add( new BasicNameValuePair("public_flag", "1"));
                value0.add( new BasicNameValuePair("category_id", requestData.get("category")));
                value0.add( new BasicNameValuePair("item", "1"));

                String responseData0 = null;
                HashMap<String, String> jsonParceData0 = new HashMap<String, String>();

                try {
                    post0.setEntity(new UrlEncodedFormEntity(value0, "UTF-8"));
                    // リクエスト送信
                    HttpResponse response = client0.execute(post0);
                    // 取得
                    HttpEntity entity = response.getEntity();
                    responseData0 = EntityUtils.toString(entity, "UTF-8");

                    /* ---------- START jsonパース ---------- */
                    JSONObject json0 = new JSONObject(responseData0);

                    if (json0.isNull("response")) {
                        jsonParceData0.put("code", json0.getJSONObject("error").getString("code"));
                        jsonParceData0.put("message", json0.getJSONObject("error").getString("message"));
                    } else {
                        jsonParceData0.put("code", json0.getJSONObject("response").getString("code"));
                        jsonParceData0.put("message", json0.getJSONObject("response").getString("message"));
                    }
                    /* ---------- END jsonパース ---------- */
                } catch(IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                returnData = jsonParceData0;

                break;
            case 1:// 選択問題の問題作成
                HttpClient client1 = new DefaultHttpClient();
                HttpPost post1 = new HttpPost("http://sakumon.jp/app/LK_API/problems/add.json");
                // パラメータの設定
                ArrayList<NameValuePair> value1 = new ArrayList<NameValuePair>();
                value1.add( new BasicNameValuePair("kentei_id", "6"));
                value1.add( new BasicNameValuePair("user_id", "1"));
                value1.add( new BasicNameValuePair("type", "1"));
                value1.add( new BasicNameValuePair("grade", "1"));
                value1.add( new BasicNameValuePair("number", "1"));
                value1.add( new BasicNameValuePair("sentence", requestData.get("question")));
                value1.add( new BasicNameValuePair("right_answer", requestData.get("answer")));
                value1.add( new BasicNameValuePair("wrong_answer1", requestData.get("incorrect1")));
                value1.add( new BasicNameValuePair("wrong_answer2", requestData.get("incorrect2")));
                value1.add( new BasicNameValuePair("wrong_answer3", requestData.get("incorrect3")));
                value1.add( new BasicNameValuePair("description", "012345"));
                value1.add( new BasicNameValuePair("public_flag", "1"));
                value1.add( new BasicNameValuePair("category_id", requestData.get("category")));
                value1.add( new BasicNameValuePair("item", "1"));

                String responseData1 = null;
                HashMap<String, String> jsonParceData1 = new HashMap<String, String>();

                try {
                    post1.setEntity(new UrlEncodedFormEntity(value1, "UTF-8"));
                    // リクエスト送信
                    HttpResponse response = client1.execute(post1);
                    // 取得
                    HttpEntity entity = response.getEntity();
                    responseData1 = EntityUtils.toString(entity, "UTF-8");

                    /* ---------- START jsonパース ---------- */
                    JSONObject json1 = new JSONObject(responseData1);

                    if (json1.isNull("response")) {
                        jsonParceData1.put("code", json1.getJSONObject("error").getString("code"));
                        jsonParceData1.put("message", json1.getJSONObject("error").getString("message"));
                    } else {
                        jsonParceData1.put("code", json1.getJSONObject("response").getString("code"));
                        jsonParceData1.put("message", json1.getJSONObject("response").getString("message"));
                    }

                    /* ---------- END jsonパース ---------- */
                } catch(IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                returnData = jsonParceData1;

                break;
            case 2:// カテゴリ選択で問題解答
                // URL指定
                HttpClient client2 = new DefaultHttpClient();

                // パラメータの設定
                ArrayList<NameValuePair> value2 = new ArrayList<NameValuePair>();
                value2.add( new BasicNameValuePair("kentei_id", "4"));
                value2.add( new BasicNameValuePair("employ", "2013"));
                value2.add( new BasicNameValuePair("public_flag", "0"));
//                value2.add( new BasicNameValuePair("grade", "0"));
                value2.add( new BasicNameValuePair("category_id", requestData.get("category_id")));
                value2.add( new BasicNameValuePair("item", "1"));

                String responseData2 = null;
                HashMap<String, String> jsonParceData2 = new HashMap<String, String>();

                try {
                    String query2 = URLEncodedUtils.format(value2, "UTF-8");
                    HttpGet get2 = new HttpGet("http://sakumon.jp/app/LK_API/problems/index.json" + "?" + query2);

                    // リクエスト送信
                    HttpResponse response = client2.execute(get2);
                    // 取得
                    HttpEntity entity = response.getEntity();
                    responseData2 = EntityUtils.toString(entity, "UTF-8");

                    /* ---------- START jsonパース ---------- */
                    JSONObject json2 = new JSONObject(responseData2);

                    jsonParceData2.put("sentence", json2.getJSONObject("response").getJSONArray("Problems").getJSONObject(0).getJSONObject("Problem").getString("sentence"));
                    jsonParceData2.put("right_answer", json2.getJSONObject("response").getJSONArray("Problems").getJSONObject(0).getJSONObject("Problem").getString("right_answer"));
                    jsonParceData2.put("type", json2.getJSONObject("response").getJSONArray("Problems").getJSONObject(0).getJSONObject("Problem").getString("type"));
                    jsonParceData2.put("category_id", json2.getJSONObject("response").getJSONArray("Problems").getJSONObject(0).getJSONObject("Problem").getString("category_id"));

                    if (json2.getJSONObject("response").getJSONArray("Problems").getJSONObject(0).getJSONObject("Problem").getString("type").equals("1")) {// 四択問題の場合
                        Log.v("形式：四択問題", "誤答選択肢を追加");

                        jsonParceData2.put("wrong_answer1", json2.getJSONObject("response").getJSONArray("Problems").getJSONObject(0).getJSONObject("Problem").getString("wrong_answer1"));
                        jsonParceData2.put("wrong_answer2", json2.getJSONObject("response").getJSONArray("Problems").getJSONObject(0).getJSONObject("Problem").getString("wrong_answer2"));
                        jsonParceData2.put("wrong_answer3", json2.getJSONObject("response").getJSONArray("Problems").getJSONObject(0).getJSONObject("Problem").getString("wrong_answer3"));
                    } else {
                        Log.v("エラー", "問題形式エラー");
                    }
                    /* ---------- END jsonパース ---------- */
                } catch(IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                returnData = jsonParceData2;

                break;
            case 3:// ランダムで問題解答
                // URL指定
                HttpClient client3 = new DefaultHttpClient();

                // パラメータの設定
                ArrayList<NameValuePair> value3 = new ArrayList<NameValuePair>();
                value3.add( new BasicNameValuePair("kentei_id", "4"));
                value3.add( new BasicNameValuePair("employ", "2013"));
                value3.add( new BasicNameValuePair("public_flag", "0"));
//                value3.add( new BasicNameValuePair("grade", "0"));
//                value3.add( new BasicNameValuePair("category_id", "1"));
                value3.add( new BasicNameValuePair("item", "1"));

                String responseData3 = null;
                HashMap<String, String> jsonParceData3 = new HashMap<String, String>();

                try {
                    String query3 = URLEncodedUtils.format(value3, "UTF-8");
                    HttpGet get3 = new HttpGet("http://sakumon.jp/app/LK_API/problems/index.json" + "?" + query3);

                    // リクエスト送信
                    HttpResponse response = client3.execute(get3);
                    // 取得
                    HttpEntity entity = response.getEntity();
                    responseData3 = EntityUtils.toString(entity, "UTF-8");

                    Log.v("test---------", responseData3);


                    /* ---------- START jsonパース ---------- */
                    JSONObject json3 = new JSONObject(responseData3);



                    jsonParceData3.put("sentence", json3.getJSONObject("response").getJSONArray("Problems").getJSONObject(0).getJSONObject("Problem").getString("sentence"));
                    jsonParceData3.put("right_answer", json3.getJSONObject("response").getJSONArray("Problems").getJSONObject(0).getJSONObject("Problem").getString("right_answer"));
                    jsonParceData3.put("type", json3.getJSONObject("response").getJSONArray("Problems").getJSONObject(0).getJSONObject("Problem").getString("type"));

                    if (json3.getJSONObject("response").getJSONArray("Problems").getJSONObject(0).getJSONObject("Problem").getString("type").equals("1")) {// 四択問題の場合
                        Log.v("形式：四択問題", "誤答選択肢を追加");

                        jsonParceData3.put("wrong_answer1", json3.getJSONObject("response").getJSONArray("Problems").getJSONObject(0).getJSONObject("Problem").getString("wrong_answer1"));
                        jsonParceData3.put("wrong_answer2", json3.getJSONObject("response").getJSONArray("Problems").getJSONObject(0).getJSONObject("Problem").getString("wrong_answer2"));
                        jsonParceData3.put("wrong_answer3", json3.getJSONObject("response").getJSONArray("Problems").getJSONObject(0).getJSONObject("Problem").getString("wrong_answer3"));
                    } else {
                        Log.v("エラー", "問題形式エラー");
                    }
                    /* ---------- END jsonパース ---------- */
                } catch(IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                returnData = jsonParceData3;

                break;
            case 4:// ログイン
                HttpClient client4 = new DefaultHttpClient();
                HttpPost post4 = new HttpPost("http://sakumon.jp/app/LK_API/logins/add.json");
                // パラメータの設定
                ArrayList<NameValuePair> value4 = new ArrayList<NameValuePair>();
                value4.add( new BasicNameValuePair("username", requestData.get("username")));
                value4.add( new BasicNameValuePair("password", requestData.get("password")));

                String responseData4 = null;
                HashMap<String, String> jsonParceData4 = new HashMap<String, String>();

                try {
                    post4.setEntity(new UrlEncodedFormEntity(value4, "UTF-8"));
                    // リクエスト送信
                    HttpResponse response = client4.execute(post4);
                    // 取得
                    HttpEntity entity = response.getEntity();
                    responseData4 = EntityUtils.toString(entity, "UTF-8");

                    /* ---------- START jsonパース ---------- */
                    JSONObject json4 = new JSONObject(responseData4);

                    jsonParceData4.put("url", json4.getJSONObject("error").getString("code"));
                    /* ---------- END jsonパース ---------- */
                } catch(IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                returnData = jsonParceData4;

                break;
            case 5:// ユーザ登録
                HttpClient client5 = new DefaultHttpClient();
                HttpPost post5 = new HttpPost("http://sakumon.jp/app/LK_API/users.json");
                // パラメータの設定
                ArrayList<NameValuePair> value5 = new ArrayList<NameValuePair>();
                value5.add( new BasicNameValuePair("username", requestData.get("username")));
                value5.add( new BasicNameValuePair("email", requestData.get("email")));
                value5.add( new BasicNameValuePair("password", requestData.get("password")));

                String responseData5 = null;
                HashMap<String, String> jsonParceData5 = new HashMap<String, String>();

                try {
                    post5.setEntity(new UrlEncodedFormEntity(value5, "UTF-8"));
                    // リクエスト送信
                    HttpResponse response = client5.execute(post5);
                    // 取得
                    HttpEntity entity = response.getEntity();
                    responseData5 = EntityUtils.toString(entity, "UTF-8");

                    /* ---------- START jsonパース ---------- */
                    JSONObject json5 = new JSONObject(responseData5);

                    if (json5.isNull("response")) {
                        jsonParceData5.put("code", json5.getJSONObject("error").getString("code"));
                        jsonParceData5.put("message", json5.getJSONObject("error").getString("message"));
                    } else {
                        jsonParceData5.put("code", json5.getJSONObject("response").getString("code"));
                        jsonParceData5.put("message", json5.getJSONObject("response").getString("message"));
                    }
                    /* ---------- END jsonパース ---------- */
                } catch(IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.v("user", requestData.get("username"));
                Log.v("emai", requestData.get("email"));
                Log.v("pass", requestData.get("password"));

                Log.v("response", responseData5);

                returnData = jsonParceData5;

                break;
            case 6:// マップでの一問一答の問題作成
                HttpClient client6 = new DefaultHttpClient();
                HttpPost post6 = new HttpPost("http://sakumon.jp/app/LK_API/problems/add.json");
                // パラメータの設定
                ArrayList<NameValuePair> value6 = new ArrayList<NameValuePair>();
                value6.add( new BasicNameValuePair("kentei_id", "6"));
                value6.add( new BasicNameValuePair("user_id", "1"));
                value6.add( new BasicNameValuePair("type", "2"));
                value6.add( new BasicNameValuePair("grade", "1"));
                value6.add( new BasicNameValuePair("number", "1"));
                value6.add( new BasicNameValuePair("sentence", requestData.get("question")));
                value6.add( new BasicNameValuePair("right_answer", requestData.get("answer")));
                value6.add( new BasicNameValuePair("description", "012345"));
                value6.add( new BasicNameValuePair("public_flag", "1"));
                value6.add( new BasicNameValuePair("category_id", requestData.get("category")));
                value6.add( new BasicNameValuePair("item", "1"));
                value6.add( new BasicNameValuePair("latitude", requestData.get("latitude")));
                value6.add( new BasicNameValuePair("longitude", requestData.get("longitude")));
                value6.add( new BasicNameValuePair("spot_id", requestData.get("spotId")));

                String responseData6 = null;
                HashMap<String, String> jsonParceData6 = new HashMap<String, String>();

                try {
                    post6.setEntity(new UrlEncodedFormEntity(value6, "UTF-8"));
                    // リクエスト送信
                    HttpResponse response = client6.execute(post6);
                    // 取得
                    HttpEntity entity = response.getEntity();
                    responseData6 = EntityUtils.toString(entity, "UTF-8");

                    /* ---------- START jsonパース ---------- */
                    JSONObject json6 = new JSONObject(responseData6);

                    if (json6.isNull("response")) {
                        jsonParceData6.put("code", json6.getJSONObject("error").getString("code"));
                        jsonParceData6.put("message", json6.getJSONObject("error").getString("message"));
                    } else {
                        jsonParceData6.put("code", json6.getJSONObject("response").getString("code"));
                        jsonParceData6.put("message", json6.getJSONObject("response").getString("message"));
                    }
                    /* ---------- END jsonパース ---------- */
                } catch(IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                returnData = jsonParceData6;

                break;
            case 7:// スポット毎の問題解答
                // URL指定
                HttpClient client7 = new DefaultHttpClient();

                // パラメータの設定
                ArrayList<NameValuePair> value7 = new ArrayList<NameValuePair>();
                value7.add( new BasicNameValuePair("kentei_id", "6"));
                value7.add( new BasicNameValuePair("employ", "0"));
                value7.add( new BasicNameValuePair("public_flag", "1"));
//                value7.add( new BasicNameValuePair("grade", "0"));
                value7.add( new BasicNameValuePair("item", "1"));
                value7.add( new BasicNameValuePair("spot_id", requestData.get("spotId")));


                String responseData7 = null;
                HashMap<String, String> jsonParceData7 = new HashMap<String, String>();

                try {
                    String query7 = URLEncodedUtils.format(value7, "UTF-8");
                    HttpGet get7 = new HttpGet("http://sakumon.jp/app/LK_API/problems/index.json" + "?" + query7);

                    // リクエスト送信
                    HttpResponse response = client7.execute(get7);
                    // 取得
                    HttpEntity entity = response.getEntity();
                    responseData7 = EntityUtils.toString(entity, "UTF-8");

                    /* ---------- START jsonパース ---------- */
                    JSONObject json7 = new JSONObject(responseData7);

                    if (json7.getJSONObject("response").has("Problems")) {
                        jsonParceData7.put("access", "success");

                        jsonParceData7.put("sentence", json7.getJSONObject("response").getJSONArray("Problems").getJSONObject(0).getJSONObject("Problem").getString("sentence"));
                        jsonParceData7.put("right_answer", json7.getJSONObject("response").getJSONArray("Problems").getJSONObject(0).getJSONObject("Problem").getString("right_answer"));
                        jsonParceData7.put("type", json7.getJSONObject("response").getJSONArray("Problems").getJSONObject(0).getJSONObject("Problem").getString("type"));
                        jsonParceData7.put("spotId", json7.getJSONObject("response").getJSONArray("Problems").getJSONObject(0).getJSONObject("Problem").getString("spot_id"));

                        if (json7.getJSONObject("response").getJSONArray("Problems").getJSONObject(0).getJSONObject("Problem").getString("type").equals("1")) {// 四択問題の場合
                            Log.v("形式：四択問題", "誤答選択肢を追加");

                            jsonParceData7.put("wrong_answer1", json7.getJSONObject("response").getJSONArray("Problems").getJSONObject(0).getJSONObject("Problem").getString("wrong_answer1"));
                            jsonParceData7.put("wrong_answer2", json7.getJSONObject("response").getJSONArray("Problems").getJSONObject(0).getJSONObject("Problem").getString("wrong_answer2"));
                            jsonParceData7.put("wrong_answer3", json7.getJSONObject("response").getJSONArray("Problems").getJSONObject(0).getJSONObject("Problem").getString("wrong_answer3"));
                        } else {
                            Log.v("エラー", "問題形式エラー");
                        }
                    } else {
                        jsonParceData7.put("access", "failure");
                    }
                    /* ---------- END jsonパース ---------- */
                } catch(IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                returnData = jsonParceData7;

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
