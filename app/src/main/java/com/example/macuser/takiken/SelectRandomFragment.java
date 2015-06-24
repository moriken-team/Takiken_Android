package com.example.macuser.takiken;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelectRandomFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectRandomFragment extends Fragment implements LoaderManager.LoaderCallbacks<HashMap<String, String>> {
    public static SelectRandomFragment newInstance() {
        SelectRandomFragment fragment = new SelectRandomFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public SelectRandomFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_select_random, container, false);

        /* ---------- START ドロップダウンの表示設定 ---------- */
        // Adapterの作成
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Adapterにアイテムを追加
        adapter.add("5問");
        adapter.add("10問");
        adapter.add("20問");

        Spinner spinner = (Spinner) view.findViewById(R.id.sr_count);
        // SpinnerにAdapterを設定
        spinner.setAdapter(adapter);
        /* ---------- END ドロップダウンの表示設定 ---------- */

        Button decision = (Button) view.findViewById(R.id.sr_button);
        decision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // レイアウトからSpinnerを取得
                Spinner item = (Spinner) getActivity().findViewById(R.id.sr_count);
                // 選択したアイテム取得
                String selectedCount = (String) item.getSelectedItem();


                // 選択したアイテムの位置を取得
                String countPosition = String.valueOf(item.getSelectedItemPosition());// 数値から文字列にキャスト変換

                // ログで確認
                Log.v("spinner item", selectedCount);
                Log.v("spinner position", countPosition);




                /* ---------- START Loader（非同期処理）初期設定 ---------- */
                // Loader（HttpHttpAsyncTaskLoaderクラス）に渡す引数を設定
                Bundle data = new Bundle();
                data.putString("data", "data");

                // Loader（HttpHttpAsyncTaskLoaderクラス）の初期化と開始
                getLoaderManager().initLoader(LOADER_ID, data, SelectRandomFragment.this);
                /* ---------- END Loader（非同期処理）初期設定 ---------- */
            }
        });

        return view;
    }

    /* ---------- START LoaderCallback（非同期処理）コールバック処理 ---------- */
    private static final int LOADER_ID = 3;

    @Override
    public Loader<HashMap<String, String>> onCreateLoader(int id, Bundle data) {// 非同期処理を行うLoaderを生成する
        // 非同期処理に渡すデータを設定
        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("data", data.getString("data"));

        return new HttpAsyncTaskLoader(getActivity(), requestData, id);
    }

    @Override
    public void onLoadFinished(Loader<HashMap<String, String>> loader, final HashMap<String, String> data) {// 非同期処理完了時
        // ここでView等にデータをセット

        Log.v("API response", data.get("sentence"));

        // メインスレッド以外でGUI上での処理を行なう場合
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                HashMap<String, Integer> countData = new HashMap<String, Integer>();
                countData.put("quizLoop", 1);
                countData.put("correctAnswer", 0);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                fragmentManager.beginTransaction()
                        .replace(R.id.container, RandomQuizFragment.newInstance(data, countData))
                        .commit();
            }
        });

        // Loaderを停止・破棄（次回の読み込みでもう一度initLoaderをできるようにするため）
        getLoaderManager().destroyLoader(loader.getId());// loader.getId() == LOADER_ID（initLoaderの第一引数）
    }

    @Override
    public void onLoaderReset(Loader<HashMap<String, String>> loader) {// Loaderが破棄される時に呼び出し
        // Loaderが参照しているデータを削除する

    }
    /* ---------- END LoaderCallback（非同期処理）コールバック処理 ---------- */
}
