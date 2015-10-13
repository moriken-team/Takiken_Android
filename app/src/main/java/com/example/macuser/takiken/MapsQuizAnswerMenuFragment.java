package com.example.macuser.takiken;


import android.app.ProgressDialog;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapsQuizAnswerMenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapsQuizAnswerMenuFragment extends Fragment implements LoaderManager.LoaderCallbacks<HashMap<String, String>> {
    // プログレスダイアログ用
    ProgressDialog progressDialog = null;

    public static MapsQuizAnswerMenuFragment newInstance(String spotId) {
        MapsQuizAnswerMenuFragment fragment = new MapsQuizAnswerMenuFragment();
        Bundle contents = new Bundle();
        contents.putString("spotId", spotId);
        fragment.setArguments(contents);
        return fragment;
    }

    public MapsQuizAnswerMenuFragment() {
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
        View view = inflater.inflate(R.layout.fragment_maps_quiz_answer_menu, container, false);

        TextView explanation = (TextView) view.findViewById(R.id.map_qam_explanation);
        explanation.setText("ここのスポットで作成された\n問題が解答できます！");

        Log.v("-------------test",getArguments().getString("spotId"));

        Button start = (Button) view.findViewById(R.id.map_qam_button);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* ---------- START プログレスダイアログ ---------- */
                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("now loading ...");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                /* ---------- END プログレスダイアログ ---------- */

                /* ---------- START Loader（非同期処理）初期設定 ---------- */
                // Loader（HttpHttpAsyncTaskLoaderクラス）に渡す引数を設定
                Bundle inputtedData = new Bundle();
                inputtedData.putString("spotId", getArguments().getString("spotId"));

                // Loader（HttpHttpAsyncTaskLoaderクラス）の初期化と開始
                getLoaderManager().initLoader(LOADER_ID, inputtedData, MapsQuizAnswerMenuFragment.this);
                /* ---------- END Loader（非同期処理）初期設定 ---------- */
            }
        });

        return view;
    }

    /* ---------- START LoaderCallback（非同期処理）コールバック処理 ---------- */
    private static final int LOADER_ID = 7;

    @Override
    public Loader<HashMap<String, String>> onCreateLoader(int id, Bundle inputtedData) {// 非同期処理を行うLoaderを生成する
        // 非同期処理に渡すデータを設定
        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("spotId", inputtedData.getString("spotId"));

        return new HttpAsyncTaskLoader(getActivity(), requestData, id);
    }

    @Override
    public void onLoadFinished(Loader<HashMap<String, String>> loader, final HashMap<String, String> data) {// 非同期処理完了時
        // ここでView等にデータをセット
        if (progressDialog != null) {
            progressDialog.dismiss();// プログレスダイアログを終了
            progressDialog = null;
        }


        if (data.get("access").equals("success")) {// 選択したスポットに問題が登録されていたら
            // メインスレッド以外でGUI上での処理を行なう場合
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    HashMap<String, Integer> countData = new HashMap<String, Integer>();
                    countData.put("quizCount", 1);
                    countData.put("correctAnswer", 0);

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                    fragmentManager.beginTransaction()
                            .replace(R.id.container, MapsRandomQuizFragment.newInstance(data, countData))
                            .commit();
                }
            });
        } else {
            Toast.makeText(getActivity(), "このスポットには問題が登録されていないため、問題を解くことができません。", Toast.LENGTH_LONG).show();
        }

        // Loaderを停止・破棄（次回の読み込みでもう一度initLoaderをできるようにするため）
        getLoaderManager().destroyLoader(loader.getId());// loader.getId() == LOADER_ID（initLoaderの第一引数）
    }

    @Override
    public void onLoaderReset(Loader<HashMap<String, String>> loader) {// Loaderが破棄される時に呼び出し
        // Loaderが参照しているデータを削除する

    }
    /* ---------- END LoaderCallback（非同期処理）コールバック処理 ---------- */

}
