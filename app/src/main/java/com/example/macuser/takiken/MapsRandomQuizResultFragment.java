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
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapsRandomQuizResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapsRandomQuizResultFragment extends Fragment implements LoaderManager.LoaderCallbacks<HashMap<String, String>> {
    // プログレスダイアログ用
    ProgressDialog progressDialog = null;

    public static MapsRandomQuizResultFragment newInstance(HashMap<String, String> resultData, HashMap<String, Integer> countData) {
        MapsRandomQuizResultFragment fragment = new MapsRandomQuizResultFragment();
        Bundle contents = new Bundle();
        contents.putString("answer", resultData.get("answer"));
        contents.putString("spotId", resultData.get("spotId"));
        contents.putInt("quizCount", countData.get("quizCount"));
        contents.putInt("correctAnswer", countData.get("correctAnswer"));
        fragment.setArguments(contents);
        return fragment;
    }

    public MapsRandomQuizResultFragment() {
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
        View view = inflater.inflate(R.layout.fragment_maps_random_quiz_result, container, false);

        TextView count = (TextView) view.findViewById(R.id.map_rqr_count);
        count.setText(getArguments().getInt("quizCount") + "/5");

        TextView result = (TextView) view.findViewById(R.id.map_rqr_result);

        if (getArguments().getString("answer") == "correct") {
            // テキストビューのテキストを設定
            result.setText("正解！");
            ImageView maru = (ImageView) view.findViewById(R.id.map_rqr_maru_batu);
            maru.setImageResource(R.drawable.maru);
        } else {
            // テキストビューのテキストを設定
            result.setText("不正解…");
            ImageView batu = (ImageView) view.findViewById(R.id.map_rqr_maru_batu);
            batu.setImageResource(R.drawable.batu);
        }

        String test = String.valueOf(getArguments().getInt("correctAnswer"));// 数値から文字列にキャスト変換
        Log.v("---正答数---", test);

        Button decision = (Button) view.findViewById(R.id.map_rqr_button);
        decision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getArguments().getInt("quizCount") < 5) {
                    /* ---------- START プログレスダイアログ ---------- */
                    progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setMessage("now loading ...");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.show();
                    /* ---------- END プログレスダイアログ ---------- */

                    /* ---------- START Loader（非同期処理）初期設定 ---------- */
                    // Loader（HttpHttpAsyncTaskLoaderクラス）に渡す引数を設定
                    Bundle receivedData = new Bundle();
                    receivedData.putString("spotId", getArguments().getString("spotId"));

                    // Loader（HttpHttpAsyncTaskLoaderクラス）の初期化と開始
                    getLoaderManager().initLoader(LOADER_ID, receivedData, MapsRandomQuizResultFragment.this);
                /* ---------- END Loader（非同期処理）初期設定 ---------- */
                } else {
                    //SelectCategoryFragmentへ画面遷移
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                    fragmentManager.beginTransaction()
                            .replace(R.id.container, MapsQuizResultFragment.newInstance(getArguments().getInt("correctAnswer"), 5))
                            .commit();
                }

            }
        });

        return view;
    }

    /* ---------- START LoaderCallback（非同期処理）コールバック処理 ---------- */
    private static final int LOADER_ID = 7;

    @Override
    public Loader<HashMap<String, String>> onCreateLoader(int id, Bundle receivedData) {// 非同期処理を行うLoaderを生成する
        // 非同期処理に渡すデータを設定
        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("spotId", receivedData.getString("spotId"));

        return new HttpAsyncTaskLoader(getActivity(), requestData, id);
    }

    @Override
    public void onLoadFinished(Loader<HashMap<String, String>> loader, final HashMap<String, String> data) {// 非同期処理完了時
        // ここでView等にデータをセット
        if (progressDialog != null) {
            progressDialog.dismiss();// プログレスダイアログを終了
            progressDialog = null;
        }

        Log.v("API response", data.get("sentence"));

        // メインスレッド以外でGUI上での処理を行なう場合
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                HashMap<String, Integer> countData = new HashMap<String, Integer>();
                countData.put("quizCount", getArguments().getInt("quizCount") + 1);
                countData.put("correctAnswer", getArguments().getInt("correctAnswer"));

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                fragmentManager.beginTransaction()
                        .replace(R.id.container, MapsRandomQuizFragment.newInstance(data, countData))
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
