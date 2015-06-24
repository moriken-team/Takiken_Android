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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoryQuizFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryQuizFragment extends Fragment {
    public static CategoryQuizFragment newInstance(HashMap<String, String> quiz, HashMap<String, Integer> countData) {
        CategoryQuizFragment fragment = new CategoryQuizFragment();
        Bundle content = new Bundle();

        if (quiz.get("type").equals("1")) {
            Log.v("形式：四択問題", "誤答選択肢を追加");

            content.putString("wrong_answer1", quiz.get("wrong_answer1"));
            content.putString("wrong_answer2", quiz.get("wrong_answer2"));
            content.putString("wrong_answer3", quiz.get("wrong_answer3"));
        } else {
            Log.v("エラー", "問題形式エラー");
        }

        content.putString("sentence", quiz.get("sentence"));
        content.putString("right_answer", quiz.get("right_answer"));
        content.putString("type", quiz.get("type"));
        content.putString("category_id", quiz.get("category_id"));
        content.putInt("quizLoop", countData.get("quizLoop"));
        content.putInt("correctAnswer", countData.get("correctAnswer"));

        fragment.setArguments(content);
        return fragment;
    }

    public CategoryQuizFragment() {
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
        Log.v("category_id-------", getArguments().getString("category_id"));

        if (getArguments().getString("type").equals("1")) {// 四択問題の場合
            view = inflater.inflate(R.layout.fragment_category_quiz, container, false);

            Log.v("wrong_answer1-------", getArguments().getString("wrong_answer1"));
            Log.v("wrong_answer2-------", getArguments().getString("wrong_answer2"));
            Log.v("wrong_answer3-------", getArguments().getString("wrong_answer3"));

            TextView count = (TextView) view.findViewById(R.id.cq_count);
            count.setText(getArguments().getInt("quizLoop") + "/5");

            TextView sentence = (TextView) view.findViewById(R.id.cq_sentence);
            sentence.setText(getArguments().getString("sentence"));

            /* ---------- START 選択肢をシャッフル ---------- */
            //配列の中身をシャッフルするためにリスト形式に変更
            ArrayList<String> choices = new ArrayList<String>();
            choices.add(getArguments().getString("right_answer"));
            choices.add(getArguments().getString("wrong_answer1"));
            choices.add(getArguments().getString("wrong_answer2"));
            choices.add(getArguments().getString("wrong_answer3"));
            choices.add(getArguments().getString("wrong_answer3"));

            Collections.shuffle(choices);// 配列の中身をシャッフル
            /* ---------- END 選択肢をシャッフル ---------- */

            // ラジオボタンのテキストを設定
            RadioButton radio1 = (RadioButton) view.findViewById(R.id.cq_radioButton1);
            radio1.setText(choices.get(0));

            RadioButton radio2 = (RadioButton) view.findViewById(R.id.cq_radioButton2);
            radio2.setText(choices.get(1));

            RadioButton radio3 = (RadioButton) view.findViewById(R.id.cq_radioButton3);
            radio3.setText(choices.get(2));

            RadioButton radio4 = (RadioButton) view.findViewById(R.id.cq_radioButton4);
            radio4.setText(choices.get(3));

            final RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.cq_radioGroup);

            Button decision = (Button) view.findViewById(R.id.cq_button);
            decision.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int notSelected = -1;
                    int checkedId = radioGroup.getCheckedRadioButtonId();

                    if (checkedId != notSelected) {
                        // 選択されているラジオボタンの取得
                        RadioButton radioButton = (RadioButton) getActivity().findViewById(checkedId);

                        // ラジオボタンのテキストを取得
                        String text = radioButton.getText().toString();

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
                        resultData.put("category_id", getArguments().getString("category_id"));

                        HashMap<String, Integer> countData = new HashMap<String, Integer>();
                        countData.put("quizLoop", getArguments().getInt("quizLoop"));
                        countData.put("correctAnswer", correctAnswer);

                        // CategoryQuizResultFragmentへ画面遷移
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                        fragmentManager.beginTransaction()
                                .replace(R.id.container, CategoryQuizResultFragment.newInstance(resultData, countData))
                                .commit();

                    } else {
                        Log.v("check", "選択されていません");
                    }
                }
            });
        } else if (getArguments().getString("type").equals("2")) {// 記述形式問題の場合
            view = inflater.inflate(R.layout.fragment_category_quiz_type2, container, false);

            TextView count = (TextView) view.findViewById(R.id.cq_type2_count);
            count.setText(getArguments().getInt("quizLoop") + "/5");

            TextView sentence = (TextView) view.findViewById(R.id.cq_type2_sentence);
            sentence.setText(getArguments().getString("sentence"));

            Button decision = (Button) view.findViewById(R.id.cq_type2_button);
            decision.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText getAnswer = (EditText) getActivity().findViewById(R.id.cq_type2_answer);
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
                    resultData.put("category_id", getArguments().getString("category_id"));

                    HashMap<String, Integer> countData = new HashMap<String, Integer>();
                    countData.put("quizLoop", getArguments().getInt("quizLoop"));
                    countData.put("correctAnswer", correctAnswer);

                    // CategoryQuizResultFragmentへ画面遷移
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                    fragmentManager.beginTransaction()
                            .replace(R.id.container, CategoryQuizResultFragment.newInstance(resultData, countData))
                            .commit();
                }
            });
        } else {
            Log.v("エラー", "問題形式エラー");
        }

        return view;
    }
}
