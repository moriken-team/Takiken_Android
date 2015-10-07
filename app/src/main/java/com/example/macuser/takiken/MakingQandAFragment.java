package com.example.macuser.takiken;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MakingQandAFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MakingQandAFragment extends Fragment implements LoaderManager.LoaderCallbacks<HashMap<String, String>> {
    // プログレスダイアログ用
    ProgressDialog progressDialog = null;

    public static MakingQandAFragment newInstance() {
        MakingQandAFragment fragment = new MakingQandAFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public MakingQandAFragment() {
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
        final View view = inflater.inflate(R.layout.fragment_making_q_and_a, container, false);

        /* ---------- START ドロップダウンの表示設定 ---------- */
        // Adapterの作成
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Adapterにアイテムを追加
        adapter.add("カテゴリを選択");
        adapter.add("滝沢のなりたち・概要");
        adapter.add("自然");
        adapter.add("神社・仏閣");
        adapter.add("伝統・文化財");
        adapter.add("人物");
        adapter.add("施設");
        adapter.add("都市整備");
        adapter.add("産業");
        adapter.add("イベント");
        adapter.add("生涯学習");
        adapter.add("メディア");

        Spinner spinner = (Spinner) view.findViewById(R.id.qaa_category);
        // SpinnerにAdapterを設定
        spinner.setAdapter(adapter);
        /* ---------- END ドロップダウンの表示設定 ---------- */

        //ボタンクリック：非同期処理により、入力した項目をPOSTで送信
        Button decision = (Button) view.findViewById(R.id.qaa_button);
        decision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* ---------- START アラートダイアログ ---------- */
                new AlertDialog.Builder(getActivity())
                        .setTitle("問題登録")
                        .setMessage("問題を登録しても宜しいですか？")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // レイアウトからSpinnerを取得
                                Spinner item = (Spinner) getActivity().findViewById(R.id.qaa_category);
                                // 選択したアイテム取得
                                String selectedCategory = (String) item.getSelectedItem();

                                // 選択したアイテムの位置を取得
                                String categoryPosition = String.valueOf(item.getSelectedItemPosition());// 数値から文字列にキャスト変換

                                // ログで確認
                                Log.v("spinner item", selectedCategory);
                                Log.v("spinner position", categoryPosition);

                                String categoryId = null;
                                switch (item.getSelectedItemPosition()) {// dbに登録してあるカテゴリidと対応
                                    case 1:
                                        categoryId = "15";
                                        break;
                                    case 2:
                                        categoryId = "16";
                                        break;
                                    case 3:
                                        categoryId = "17";
                                        break;
                                    case 4:
                                        categoryId = "18";
                                        break;
                                    case 5:
                                        categoryId = "19";
                                        break;
                                    case 6:
                                        categoryId = "20";
                                        break;
                                    case 7:
                                        categoryId = "21";
                                        break;
                                    case 8:
                                        categoryId = "22";
                                        break;
                                    case 9:
                                        categoryId = "23";
                                        break;
                                    case 10:
                                        categoryId = "24";
                                        break;
                                    case 11:
                                        categoryId = "25";
                                        break;
                                }

                                if (categoryPosition.equals("0")){
                                    Toast.makeText(getActivity(), "カテゴリを選択して下さい。", Toast.LENGTH_LONG).show();
                                } else {
                                    /* ---------- START プログレスダイアログ ---------- */
                                    progressDialog = new ProgressDialog(getActivity());
                                    progressDialog.setMessage("now loading ...");
                                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                    progressDialog.show();
                                    /* ---------- END プログレスダイアログ ---------- */

                                    EditText question = (EditText) getActivity().findViewById(R.id.qaa_question);
                                    EditText answer = (EditText) getActivity().findViewById(R.id.qaa_answer);

                                    String inputtedQuestion = question.getText().toString();
                                    String inputtedAnswer = answer.getText().toString();

                                    /* ---------- START Loader（非同期処理）初期設定 ---------- */
                                    // Loader（HttpHttpAsyncTaskLoaderクラス）に渡す引数を設定
                                    Bundle inputtedData = new Bundle();
                                    inputtedData.putString("category", categoryId);
                                    inputtedData.putString("question", inputtedQuestion);
                                    inputtedData.putString("answer", inputtedAnswer);

                                    // Loader（HttpHttpAsyncTaskLoaderクラス）の初期化と開始
                                    getLoaderManager().initLoader(LOADER_ID, inputtedData, MakingQandAFragment.this);
                                    /* ---------- END Loader（非同期処理）初期設定 ---------- */
                                }
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
                /* ---------- END アラートダイアログ ---------- */
            }
        });

        return view;
    }


    /* ---------- START LoaderCallback（非同期処理）コールバック処理 ---------- */
    private static final int LOADER_ID = 0;

    @Override
    public Loader<HashMap<String, String>> onCreateLoader(int id, Bundle inputtedData) {// 非同期処理を行うLoaderを生成する
        // 非同期処理に渡すデータを設定
        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("category", inputtedData.getString("category"));
        requestData.put("question", inputtedData.getString("question"));
        requestData.put("answer", inputtedData.getString("answer"));

        return new HttpAsyncTaskLoader(getActivity(), requestData, id);
    }

    @Override
    public void onLoadFinished(Loader<HashMap<String, String>> loader, HashMap<String, String> data) {// 非同期処理完了時
        // ここでView等にデータをセット
        if (progressDialog != null) {
            progressDialog.dismiss();// プログレスダイアログを終了
            progressDialog = null;
        }

        Log.v("API response", data.get("code"));
        Log.v("API response", data.get("message"));

        if (data.get("code").equals("201")) {
            Toast.makeText(getActivity(), "問題を登録しました。", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity(), "未入力の項目があります。", Toast.LENGTH_LONG).show();
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