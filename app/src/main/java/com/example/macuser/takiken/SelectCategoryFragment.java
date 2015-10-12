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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelectCategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectCategoryFragment extends Fragment implements LoaderManager.LoaderCallbacks<HashMap<String, String>> {
    // プログレスダイアログ用
    ProgressDialog progressDialog = null;

    public static SelectCategoryFragment newInstance() {
        SelectCategoryFragment fragment = new SelectCategoryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public SelectCategoryFragment() {
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
        View view = inflater.inflate(R.layout.fragment_select_category, container, false);

        TextView  explanation = (TextView) view.findViewById(R.id.sc_category_txt);
        explanation.setText("カテゴリを以下から選択して下さい");

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

        Spinner spinner = (Spinner) view.findViewById(R.id.sc_category);
        // SpinnerにAdapterを設定
        spinner.setAdapter(adapter);
        /* ---------- END ドロップダウンの表示設定 ---------- */

        Button decision = (Button) view.findViewById(R.id.sc_button);
        decision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // レイアウトからSpinnerを取得
                Spinner item = (Spinner) getActivity().findViewById(R.id.sc_category);
                // 選択したアイテム取得
                String selectedCategory = (String) item.getSelectedItem();

                // 選択したアイテムの位置を取得
                String categoryPosition = String.valueOf(item.getSelectedItemPosition());// 数値から文字列にキャスト変換

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

                // ログで確認
                Log.v("spinner item", selectedCategory);
                Log.v("spinner position", categoryPosition);

                if (categoryPosition.equals("0")){
                    Toast.makeText(getActivity(), "カテゴリを選択して下さい。", Toast.LENGTH_LONG).show();
                } else {
                    /* ---------- START プログレスダイアログ ---------- */
                    progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setMessage("now loading ...");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.show();
                    /* ---------- END プログレスダイアログ ---------- */

                    /* ---------- START Loader（非同期処理）初期設定 ---------- */
                    // Loader（HttpHttpAsyncTaskLoaderクラス）に渡す引数を設定
                    Bundle inputtedData = new Bundle();
                    inputtedData.putString("category_id", categoryId);

                    // Loader（HttpHttpAsyncTaskLoaderクラス）の初期化と開始
                    getLoaderManager().initLoader(LOADER_ID, inputtedData, SelectCategoryFragment.this);
                    /* ---------- END Loader（非同期処理）初期設定 ---------- */
                }
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
                countData.put("quizCount", 1);
                countData.put("correctAnswer", 0);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                fragmentManager.beginTransaction()
                        .replace(R.id.container, CategoryQuizFragment.newInstance(data, countData))
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
