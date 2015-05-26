package com.example.macuser.takiken;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        TextView textView = (TextView) view.findViewById(R.id.cq_sentence);
        // テキストビューのテキストを設定します
        textView.setText(getArguments().getString("sentence"));
//        // テキストビューのテキストを取得します
//        String text = textView.getText().toString();
//        Toast.makeText(this, text, Toast.LENGTH_LONG).show();

        return view;
    }


}
