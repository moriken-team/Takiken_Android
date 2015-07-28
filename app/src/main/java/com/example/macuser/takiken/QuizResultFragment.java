package com.example.macuser.takiken;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuizResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuizResultFragment extends Fragment {
    public static QuizResultFragment newInstance(Integer correctAnswer, Integer quizNumber) {
        QuizResultFragment fragment = new QuizResultFragment();
        Bundle contents = new Bundle();
        contents.putInt("correctAnswer", correctAnswer);
        contents.putInt("quizNumber", quizNumber);
        fragment.setArguments(contents);
        return fragment;
    }

    public QuizResultFragment() {
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
        View view = inflater.inflate(R.layout.fragment_quiz_result, container, false);

        TextView quizResult = (TextView) view.findViewById(R.id.qr_result);
        quizResult.setText(getArguments().getInt("quizNumber") + "問中" + getArguments().getInt("correctAnswer") + "問、正解しました！");

        Button end = (Button) view.findViewById(R.id.qr_button);
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // SelectCategoryFragmentへ画面遷移
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                fragmentManager.beginTransaction()
                        .replace(R.id.container, QuizAnswerFragment.newInstance(3))
                        .commit();
            }
        });

        return view;
    }


}
