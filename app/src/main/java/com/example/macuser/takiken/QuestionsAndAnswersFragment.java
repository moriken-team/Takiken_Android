package com.example.macuser.takiken;


import android.content.Context;
import android.os.Bundle;
//import android.app.Fragment;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuestionsAndAnswersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionsAndAnswersFragment extends Fragment implements LoaderManager.LoaderCallbacks<String> {
    public static QuestionsAndAnswersFragment newInstance() {
        QuestionsAndAnswersFragment fragment = new QuestionsAndAnswersFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public QuestionsAndAnswersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_questions_and_answers, container, false);




        //ボタンクリック：非同期処理により、入力した文字をログで表示
        Button btn = (Button)view.findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText question = (EditText) getActivity().findViewById(R.id.question);
                EditText answer = (EditText) getActivity().findViewById(R.id.answer);

//                // エディットテキストのテキストを設定します
//                editText.setText("テスト");
//                // エディットテキストのテキストを全選択します
//                editText.selectAll();
                // エディットテキストのテキストを取得します

                String[] qa_result = new String[2];

                qa_result[0] = question.getText().toString();
                qa_result[1] = answer.getText().toString();

//                Log.v("Button2", Arrays.toString(qa_result));


                /* ---------- START Loader（非同期処理）初期設定 ---------- */
                // Loader（HttpHttpAsyncTaskLoaderクラス）に渡す引数
                Bundle args = new Bundle();
                args.putString("question", qa_result[0]);
                args.putString("answer", qa_result[1]);

                // Loader（HttpHttpAsyncTaskLoaderクラス）の初期化と開始
                getLoaderManager().initLoader(LOADER_ID, args, QuestionsAndAnswersFragment.this);
                /* ---------- END Loader（非同期処理）初期設定 ---------- */
            }
        });

        return view;
    }







    /* ---------- START LoaderCallback（非同期処理）コールバック処理 ---------- */
    private static final int LOADER_ID = 0;

//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//
//        // Loaderに渡す引数
//        Bundle args = new Bundle();
//        args.putString("hoge", "hoge");
//
//        // Loader初期化と開始
//        getLoaderManager().initLoader(LOADER_ID, args, this);
//
//        /**
//         * ★Loaderの操作メソッド
//         * ○getLoaderManager().getLoader(LOADER_ID).startLoading()
//         *   Loaderの処理を開始
//         *   getLoaderManager().initLoaderが呼ばれた時と同様の処理
//         *
//         * ○getLoaderManager().getLoader(LOADER_ID).stopLoading()
//         *   Loaderの処理を停止
//         */
//    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {// 非同期処理を行うLoaderを生成する
        // getLoaderManager().initLoaderで一回のみ呼び出される

        String question = args.getString("question");
        String answer = args.getString("answer");

        return new HttpAsyncTaskLoader(getActivity(), question, answer);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {// 非同期処理完了時
        // ここでView等にデータをセット

        Log.v("API response", data);
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {// Loaderが破棄される時に呼び出し
        // Loaderが参照しているデータを削除する
    }

    /* ---------- END LoaderCallback（非同期処理）コールバック処理 ---------- */
}