package com.example.macuser.takiken;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoryQuizFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryQuizFragment extends Fragment {
    public static CategoryQuizFragment newInstance(HashMap<String, String> quiz) {
        CategoryQuizFragment fragment = new CategoryQuizFragment();
        Bundle content = new Bundle();
        content.putString("sentence", quiz.get("sentence"));
        content.putString("right_answer", quiz.get("right_answer"));
        content.putString("wrong_answer1", quiz.get("wrong_answer1"));
        content.putString("wrong_answer2", quiz.get("wrong_answer2"));
        content.putString("wrong_answer3", quiz.get("wrong_answer3"));
        content.putString("category_id", quiz.get("category_id"));
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
        View view = inflater.inflate(R.layout.fragment_category_quiz, container, false);

        Log.v("sentence-------", getArguments().getString("sentence"));
        Log.v("right_answer-------", getArguments().getString("right_answer"));
        Log.v("wrong_answer1-------", getArguments().getString("wrong_answer1"));
        Log.v("wrong_answer2-------", getArguments().getString("wrong_answer2"));
        Log.v("wrong_answer3-------", getArguments().getString("wrong_answer3"));
        Log.v("category_id-------", getArguments().getString("category_id"));

        TextView textView = (TextView) view.findViewById(R.id.cq_sentence);
        // テキストビューのテキストを設定
        textView.setText(getArguments().getString("sentence"));



        // ラジオボタンのテキストを設定
        RadioButton radio1 = (RadioButton) view.findViewById(R.id.cq_radioButton1);
        radio1.setText(getArguments().getString("right_answer"));

        RadioButton radio2 = (RadioButton) view.findViewById(R.id.cq_radioButton2);
        radio2.setText(getArguments().getString("wrong_answer1"));

        RadioButton radio3 = (RadioButton) view.findViewById(R.id.cq_radioButton3);
        radio3.setText(getArguments().getString("wrong_answer2"));

        RadioButton radio4 = (RadioButton) view.findViewById(R.id.cq_radioButton4);
        radio4.setText(getArguments().getString("wrong_answer3"));




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




                    HashMap<String, String> selectedData = new HashMap<String, String>();
                    selectedData.put("selected", text);
                    selectedData.put("answer", getArguments().getString("right_answer"));
                    selectedData.put("category_id", getArguments().getString("category_id"));


                    // 画面遷移
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                    fragmentManager.beginTransaction()
                            .replace(R.id.container, CategoryQuizResultFragment.newInstance(selectedData))
                            .commit();

                } else {
                    Log.v("check", "test2");
                }
            }
        });

        return view;
    }


}
