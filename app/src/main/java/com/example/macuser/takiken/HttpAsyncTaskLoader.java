package com.example.macuser.takiken;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by macuser on 15/05/13.
 */


/* ---------- START AsyncTaskLoader（非同期処理）ロード処理 ---------- */

//Http通信でAPIを利用する際の非同期通信クラス（このクラスで非同期処理してデータを受け渡している）
public class HttpAsyncTaskLoader extends AsyncTaskLoader<String> {

    /** 引数 */
    private String mArg;
    private String mArg2;

    /** 非同期処理での結果を格納 */
    private String mData;

    public HttpAsyncTaskLoader(Context context, String arg, String arg2) {
        super(context);
        this.mArg = arg;
        this.mArg2 = arg2;
    }

    @Override
    public String loadInBackground() {// 非同期処理を記述


//        try {
//            // 適当に時間がかかる処理
//            Thread.sleep(3000);
//            mData = "hoge";
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }




        // URL指定
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost("http://sakumon.jp/app/LK_API/problems/add.json");
        // BODYに登録、設定
        ArrayList<NameValuePair> value = new ArrayList<NameValuePair>();
        value.add( new BasicNameValuePair("kentei_id", "1"));
        value.add( new BasicNameValuePair("user_id", "1"));
        value.add( new BasicNameValuePair("type", "2"));
        value.add( new BasicNameValuePair("grade", "1"));
        value.add( new BasicNameValuePair("number", "1"));
        value.add( new BasicNameValuePair("sentence", mArg));
        value.add( new BasicNameValuePair("right_answer", mArg2));
        value.add( new BasicNameValuePair("description", "012345"));
        value.add( new BasicNameValuePair("public_flag", "1"));
        value.add( new BasicNameValuePair("category_id", "1"));
        value.add( new BasicNameValuePair("item", "1"));

        String body = null;

        try {
            post.setEntity(new UrlEncodedFormEntity(value));
            // リクエスト送信
            HttpResponse response = client.execute(post);
            // 取得
            HttpEntity entity = response.getEntity();
            body = EntityUtils.toString(entity, "UTF-8");

        } catch(IOException e) {
            e.printStackTrace();
        }

        Log.v("mArg", mArg);
        Log.v("mArg2", mArg2);

//        return mData;
//        mArg = "成功";
//          mData = mArg;
//        return mData;
//
       return body;
    }

    @Override
    protected void onStartLoading() {// 非同期処理の開始前
        // ActivityまたはFragment復帰時（バックキーで戻る、ホーム画面から戻る等）に再実行されるためここで非同期処理を行うかチェック

        if (mData != null) {// 結果がnullではないは場合は既に実行済みとして非同期処理は不要
            // deliverResultで結果を送信
            deliverResult(mData);
        }

        if (takeContentChanged() || mData == null) {
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
        mData = null;
    }
}
/* ---------- END AsyncTaskLoader（非同期処理）ロード処理 ---------- */

