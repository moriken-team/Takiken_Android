package com.example.macuser.takiken;

import android.app.Fragment;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashMap;


public class UserAddActivity extends FragmentActivity implements LoaderManager.LoaderCallbacks<HashMap<String, String>>  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add);

        //ボタンクリック：非同期処理により、入力した項目をPOSTで送信
        Button registration = (Button) findViewById(R.id.user_add_button);
        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                EditText username = (EditText) findViewById(R.id.add_username);
                EditText email    = (EditText) findViewById(R.id.add_email);
                EditText password = (EditText) findViewById(R.id.add_password);


                Log.v("user", username.getText().toString());
                Log.v("emai", email.getText().toString());
                Log.v("pass", password.getText().toString());


                String inputtedUserName = username.getText().toString();
                String inputtedEmail    = email.getText().toString();
                String inputtedPassword = password.getText().toString();

                /* ---------- START Loader（非同期処理）初期設定 ---------- */
                // Loader（HttpHttpAsyncTaskLoaderクラス）に渡す引数を設定
                Bundle inputtedData = new Bundle();
                Log.v("確認", "----------------------------------------");
                inputtedData.putString("username", inputtedUserName);
                inputtedData.putString("email", inputtedEmail);
                inputtedData.putString("password", inputtedPassword);

                // Loader（HttpHttpAsyncTaskLoaderクラス）の初期化と開始
                getSupportLoaderManager().initLoader(LOADER_ID, inputtedData, UserAddActivity.this);
                /* ---------- END Loader（非同期処理）初期設定 ---------- */
            }
        });
    }

    /* ---------- START LoaderCallback（非同期処理）コールバック処理 ---------- */
    private static final int LOADER_ID = 5;

    @Override
    public Loader<HashMap<String, String>> onCreateLoader(int id, Bundle inputtedData) {// 非同期処理を行うLoaderを生成する
        // 非同期処理に渡すデータを設定
        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("username", inputtedData.getString("username"));
        requestData.put("email", inputtedData.getString("email"));
        requestData.put("password", inputtedData.getString("password"));

        return new HttpAsyncTaskLoader(this, requestData, id);
    }

    @Override
    public void onLoadFinished(Loader<HashMap<String, String>> loader, HashMap<String, String> data) {// 非同期処理完了時
        // ここでView等にデータをセット

        Log.v("API response", data.get("code"));


        if (data.get("code").equals("201")) {
            Toast.makeText(this, "新規登録をしました。", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(UserAddActivity.this, LoginActivity.class);// どのクラスを対象にするか
            startActivity(intent);// 画面遷移
        } else {
            Toast.makeText(this, "未入力の項目があります。", Toast.LENGTH_LONG).show();
        }

        // Loaderを停止・破棄（次回の読み込みでもう一度initLoaderをできるようにするため）
        getSupportLoaderManager().destroyLoader(loader.getId());// loader.getId() == LOADER_ID（initLoaderの第一引数）
    }

    @Override
    public void onLoaderReset(Loader<HashMap<String, String>> loader) {// Loaderが破棄される時に呼び出し
        // Loaderが参照しているデータを削除する

    }
    /* ---------- END LoaderCallback（非同期処理）コールバック処理 ---------- */
}
