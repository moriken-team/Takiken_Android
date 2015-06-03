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
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoryQuizResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryQuizResultFragment extends Fragment implements LoaderManager.LoaderCallbacks<HashMap<String, String>> {
    public static CategoryQuizResultFragment newInstance(HashMap<String, String> selectedData) {
        CategoryQuizResultFragment fragment = new CategoryQuizResultFragment();
        Bundle content = new Bundle();
        content.putString("selected", selectedData.get("selected"));
        content.putString("answer", selectedData.get("answer"));
        content.putString("category_id", selectedData.get("category_id"));
        fragment.setArguments(content);
        return fragment;
    }

    public CategoryQuizResultFragment() {
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
        View view = inflater.inflate(R.layout.fragment_category_quiz_result, container, false);

        Log.v("selected-------", getArguments().getString("selected"));
        Log.v("answer-------", getArguments().getString("answer"));
        Log.v("category_id-------", getArguments().getString("category_id"));


        TextView textView = (TextView) view.findViewById(R.id.cqr_result);

        if (getArguments().getString("selected") == getArguments().getString("answer")) {
            // テキストビューのテキストを設定
            textView.setText("◯ 正解");

            Log.v("correct", "正解");
        } else {
            // テキストビューのテキストを設定
            textView.setText("☓ 不正解");

            Log.v("incorrect", "不正解");
        }








        Button decision = (Button) view.findViewById(R.id.cqr_button);
        decision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* ---------- START Loader（非同期処理）初期設定 ---------- */
                // Loader（HttpHttpAsyncTaskLoaderクラス）に渡す引数を設定
                Bundle inputtedData = new Bundle();
                inputtedData.putString("category_id", getArguments().getString("category_id"));

                // Loader（HttpHttpAsyncTaskLoaderクラス）の初期化と開始
                getLoaderManager().initLoader(LOADER_ID, inputtedData, CategoryQuizResultFragment.this);
                /* ---------- END Loader（非同期処理）初期設定 ---------- */
            }
        });


        return view;
    }








    /* ---------- START LoaderCallback（非同期処理）コールバック処理 ---------- */
    private static final int LOADER_ID = 2;

    @Override
    public Loader<HashMap<String, String>> onCreateLoader(int id, Bundle inputtedData) {// 非同期処理を行うLoaderを生成する
        // 非同期処理に渡すデータを設定
        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("category_id", inputtedData.getString("category_id"));

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
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                fragmentManager.beginTransaction()
                        .replace(R.id.container, CategoryQuizFragment.newInstance(data))
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
