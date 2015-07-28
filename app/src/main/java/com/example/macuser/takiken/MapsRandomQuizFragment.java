package com.example.macuser.takiken;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapsRandomQuizFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapsRandomQuizFragment extends Fragment {
    public static MapsRandomQuizFragment newInstance(HashMap<String, String> quiz, HashMap<String, Integer> countData) {
        MapsRandomQuizFragment fragment = new MapsRandomQuizFragment();
        Bundle contents = new Bundle();

        if (quiz.get("type").equals("1")) {
            Log.v("形式：四択問題", "誤答選択肢を追加");

            contents.putString("wrong_answer1", quiz.get("wrong_answer1"));
            contents.putString("wrong_answer2", quiz.get("wrong_answer2"));
            contents.putString("wrong_answer3", quiz.get("wrong_answer3"));
        }

        contents.putString("sentence", quiz.get("sentence"));
        contents.putString("right_answer", quiz.get("right_answer"));
        contents.putString("type", quiz.get("type"));
        contents.putString("spotId", quiz.get("spotId"));
        contents.putInt("quizCount", countData.get("quizCount"));
        contents.putInt("correctAnswer", countData.get("correctAnswer"));

        fragment.setArguments(contents);
        return fragment;
    }

    public MapsRandomQuizFragment() {
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








        View view = null;


        Log.v("sentence-------", getArguments().getString("sentence"));
        Log.v("right_answer-------", getArguments().getString("right_answer"));

        if (getArguments().getString("type").equals("1")) {// 四択問題の場合
            view = inflater.inflate(R.layout.fragment_maps_random_quiz, container, false);

            Log.v("wrong_answer1-------", getArguments().getString("wrong_answer1"));
            Log.v("wrong_answer2-------", getArguments().getString("wrong_answer2"));
            Log.v("wrong_answer3-------", getArguments().getString("wrong_answer3"));

            TextView count = (TextView) view.findViewById(R.id.map_rq_count);
            count.setText(getArguments().getInt("quizCount") + "/5");

            TextView sentence = (TextView) view.findViewById(R.id.map_rq_sentence);
            sentence.setText(getArguments().getString("sentence"));

            /* ---------- START 選択肢をシャッフル ---------- */
            //配列の中身をシャッフルするためにリスト形式に変更
            ArrayList<String> choices = new ArrayList<String>();
            choices.add(getArguments().getString("right_answer"));
            choices.add(getArguments().getString("wrong_answer1"));
            choices.add(getArguments().getString("wrong_answer2"));
            choices.add(getArguments().getString("wrong_answer3"));

            Collections.shuffle(choices);// 配列の中身をシャッフル
            /* ---------- END 選択肢をシャッフル ---------- */

            // ボタンのテキストを設定
            final Button button01 = (Button) view.findViewById(R.id.map_rq_button01);
            button01.setText(choices.get(0));

            final Button button02 = (Button) view.findViewById(R.id.map_rq_button02);
            button02.setText(choices.get(1));

            final Button button03 = (Button) view.findViewById(R.id.map_rq_button03);
            button03.setText(choices.get(2));

            final Button button04 = (Button) view.findViewById(R.id.map_rq_button04);
            button04.setText(choices.get(3));

            button01.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // タンのテキストを取得
                    String text = button01.getText().toString();

                    Log.v("check", text);

                    int correctAnswer = 0;// 正答数
                    String answer = null;// 正答かどうかのフラグ
                    // 正答の判定
                    if (getArguments().getString("right_answer").equals(text)) {
                        answer = "correct";

                        Log.v("correct", "正解");

                        correctAnswer = getArguments().getInt("correctAnswer") + 1;
                    } else {
                        answer = "incorrect";

                        Log.v("incorrect", "不正解");
                        correctAnswer = getArguments().getInt("correctAnswer");
                    }

                    HashMap<String, String> resultData = new HashMap<String, String>();
                    resultData.put("answer", answer);
                    resultData.put("spotId", getArguments().getString("spotId"));

                    HashMap<String, Integer> countData = new HashMap<String, Integer>();
                    countData.put("quizCount", getArguments().getInt("quizCount"));
                    countData.put("correctAnswer", correctAnswer);

                    // CategoryQuizResultFragmentへ画面遷移
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                    fragmentManager.beginTransaction()
                            .replace(R.id.container, MapsRandomQuizResultFragment.newInstance(resultData, countData))
                            .commit();
                }
            });

            button02.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // タンのテキストを取得
                    String text = button02.getText().toString();

                    Log.v("check", text);

                    int correctAnswer = 0;// 正答数
                    String answer = null;// 正答かどうかのフラグ
                    // 正答の判定
                    if (getArguments().getString("right_answer").equals(text)) {
                        answer = "correct";

                        Log.v("correct", "正解");

                        correctAnswer = getArguments().getInt("correctAnswer") + 1;
                    } else {
                        answer = "incorrect";

                        Log.v("incorrect", "不正解");
                        correctAnswer = getArguments().getInt("correctAnswer");
                    }

                    HashMap<String, String> resultData = new HashMap<String, String>();
                    resultData.put("answer", answer);
                    resultData.put("spotId", getArguments().getString("spotId"));

                    HashMap<String, Integer> countData = new HashMap<String, Integer>();
                    countData.put("quizCount", getArguments().getInt("quizCount"));
                    countData.put("correctAnswer", correctAnswer);

                    // CategoryQuizResultFragmentへ画面遷移
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                    fragmentManager.beginTransaction()
                            .replace(R.id.container, MapsRandomQuizResultFragment.newInstance(resultData, countData))
                            .commit();
                }
            });

            button03.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // タンのテキストを取得
                    String text = button03.getText().toString();

                    Log.v("check", text);

                    int correctAnswer = 0;// 正答数
                    String answer = null;// 正答かどうかのフラグ
                    // 正答の判定
                    if (getArguments().getString("right_answer").equals(text)) {
                        answer = "correct";

                        Log.v("correct", "正解");

                        correctAnswer = getArguments().getInt("correctAnswer") + 1;
                    } else {
                        answer = "incorrect";

                        Log.v("incorrect", "不正解");
                        correctAnswer = getArguments().getInt("correctAnswer");
                    }

                    HashMap<String, String> resultData = new HashMap<String, String>();
                    resultData.put("answer", answer);
                    resultData.put("spotId", getArguments().getString("spotId"));

                    HashMap<String, Integer> countData = new HashMap<String, Integer>();
                    countData.put("quizCount", getArguments().getInt("quizCount"));
                    countData.put("correctAnswer", correctAnswer);

                    // CategoryQuizResultFragmentへ画面遷移
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                    fragmentManager.beginTransaction()
                            .replace(R.id.container, MapsRandomQuizResultFragment.newInstance(resultData, countData))
                            .commit();
                }
            });

            button04.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // タンのテキストを取得
                    String text = button04.getText().toString();

                    Log.v("check", text);

                    int correctAnswer = 0;// 正答数
                    String answer = null;// 正答かどうかのフラグ
                    // 正答の判定
                    if (getArguments().getString("right_answer").equals(text)) {
                        answer = "correct";

                        Log.v("correct", "正解");

                        correctAnswer = getArguments().getInt("correctAnswer") + 1;
                    } else {
                        answer = "incorrect";

                        Log.v("incorrect", "不正解");
                        correctAnswer = getArguments().getInt("correctAnswer");
                    }

                    HashMap<String, String> resultData = new HashMap<String, String>();
                    resultData.put("answer", answer);
                    resultData.put("spotId", getArguments().getString("spotId"));

                    HashMap<String, Integer> countData = new HashMap<String, Integer>();
                    countData.put("quizCount", getArguments().getInt("quizCount"));
                    countData.put("correctAnswer", correctAnswer);

                    // CategoryQuizResultFragmentへ画面遷移
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                    fragmentManager.beginTransaction()
                            .replace(R.id.container, MapsRandomQuizResultFragment.newInstance(resultData, countData))
                            .commit();
                }
            });
        }

        if (getArguments().getString("type").equals("2")) {// 記述形式問題の場合
            view = inflater.inflate(R.layout.fragment_maps_random_quiz_type2, container, false);

            TextView count = (TextView) view.findViewById(R.id.map_rq_type2_count);
            count.setText(getArguments().getInt("quizCount") + "/5");

            TextView sentence = (TextView) view.findViewById(R.id.map_rq_type2_sentence);
            sentence.setText(getArguments().getString("sentence"));

            Button decision = (Button) view.findViewById(R.id.map_rq_type2_button);
            decision.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText getAnswer = (EditText) getActivity().findViewById(R.id.map_rq_type2_answer);
                    String inputtedAnswer = getAnswer.getText().toString();

                    int correctAnswer = 0;// 正答数
                    String answer = null;// 正答かどうかのフラグ

                    // 正答の判定
                    if (getArguments().getString("right_answer").equals(inputtedAnswer)) {
                        answer = "correct";

                        Log.v("correct", "正解");

                        correctAnswer = getArguments().getInt("correctAnswer") + 1;
                    } else {
                        answer = "incorrect";

                        Log.v("incorrect", "不正解");
                        correctAnswer = getArguments().getInt("correctAnswer");
                    }

                    HashMap<String, String> resultData = new HashMap<String, String>();
                    resultData.put("answer", answer);
                    resultData.put("spotId", getArguments().getString("spotId"));

                    HashMap<String, Integer> countData = new HashMap<String, Integer>();
                    countData.put("quizCount", getArguments().getInt("quizCount"));
                    countData.put("correctAnswer", correctAnswer);

                    // CategoryQuizResultFragmentへ画面遷移
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                    fragmentManager.beginTransaction()
                            .replace(R.id.container, MapsRandomQuizResultFragment.newInstance(resultData, countData))
                            .commit();
                }
            });
        }

        return view;
    }


}
