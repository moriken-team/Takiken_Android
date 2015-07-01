package com.example.macuser.takiken;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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


public class LoginActivity extends FragmentActivity implements LoaderManager.LoaderCallbacks<HashMap<String, String>> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);





        Button login = (Button) findViewById(R.id.login_button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText username = (EditText) findViewById(R.id.login_username);
                EditText password = (EditText) findViewById(R.id.login_password);

                String inputtedUserName = username.getText().toString();
                String inputtedPassword = password.getText().toString();

                /* ---------- START SharedPreferences（データの一時的保存） ---------- */
                // プリファレンスの準備
                // MODE_PRIVATEでこのアプリだけが使用できるように設定
                SharedPreferences pref = getSharedPreferences("user_data", Context.MODE_PRIVATE);

                // プリファレンスに書き込むためのEditorオブジェクト取得
                SharedPreferences.Editor editor = pref.edit();
                // user_id というキーで値を保存
                editor.putString("username", inputtedUserName);
                editor.putString("passname", inputtedPassword);
                // 実際にファイルに書き込み
                editor.commit();
                /* ---------- END SharedPreferences（データの一時的保存） ---------- */


                /* ---------- START Loader（非同期処理）初期設定 ---------- */
//                // Loader（HttpHttpAsyncTaskLoaderクラス）に渡す引数を設定
//                Bundle inputtedData = new Bundle();
//                inputtedData.putString("username", inputtedUserName);
//                inputtedData.putString("password", inputtedPassword);
//
//                // Loader（HttpHttpAsyncTaskLoaderクラス）の初期化と開始
//                getSupportLoaderManager().initLoader(LOADER_ID, inputtedData, LoginActivity.this);
                /* ---------- END Loader（非同期処理）初期設定 ---------- */


                Intent intent = new Intent(LoginActivity.this, MainActivity.class);// どのクラスを対象にするか
                startActivity(intent);// 画面遷移
            }
        });

        Button addUser = (Button) findViewById(R.id.new_user_button);
        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, UserAddActivity.class);// どのクラスを対象にするか
                startActivity(intent);// 画面遷移
            }
        });
    }

    /* ---------- START LoaderCallback（非同期処理）コールバック処理 ---------- */
    private static final int LOADER_ID = 4;

    @Override
    public Loader<HashMap<String, String>> onCreateLoader(int id, Bundle inputtedData) {// 非同期処理を行うLoaderを生成する
        // 非同期処理に渡すデータを設定
        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("username", inputtedData.getString("username"));
        requestData.put("password", inputtedData.getString("password"));

        return new HttpAsyncTaskLoader(this, requestData, id);
    }

    @Override
    public void onLoadFinished(Loader<HashMap<String, String>> loader, HashMap<String, String> data) {// 非同期処理完了時
        // ここでView等にデータをセット

        Log.v("API response", data.get("url"));

//        Toast.makeText(this, "問題を登録しました。", Toast.LENGTH_LONG).show();

        // Loaderを停止・破棄（次回の読み込みでもう一度initLoaderをできるようにするため）
        getLoaderManager().destroyLoader(loader.getId());// loader.getId() == LOADER_ID（initLoaderの第一引数）
    }

    @Override
    public void onLoaderReset(Loader<HashMap<String, String>> loader) {// Loaderが破棄される時に呼び出し
        // Loaderが参照しているデータを削除する

    }
    /* ---------- END LoaderCallback（非同期処理）コールバック処理 ---------- */
}
