package com.example.macuser.takiken;


import android.app.Activity;
import android.os.Bundle;
//import android.app.Fragment;利用しない
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuizAnswerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuizAnswerFragment extends Fragment {

    public static QuizAnswerFragment newInstance(int QuizAnswerSection) {
        // フラグメントの作成
        QuizAnswerFragment fragment = new QuizAnswerFragment();
        Bundle args = new Bundle();
        // putInt（キーを指定して値を保存する。第1引数にキー、第2引数に保存する値を指定する。）
        args.putInt("QuizAnswerSection", QuizAnswerSection);
        fragment.setArguments(args);
        return fragment;
    }

    public QuizAnswerFragment() {
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
        View view = inflater.inflate(R.layout.fragment_quiz_answer, container, false);

        FragmentTabHost host = (FragmentTabHost) view.findViewById(android.R.id.tabhost);
        host.setup(getActivity(), getChildFragmentManager(), R.id.qa_content);

        TabHost.TabSpec tabSpec1 = host.newTabSpec("tab1");
        tabSpec1.setIndicator("カテゴリ別解答");
        host.addTab(tabSpec1, SelectCategoryFragment.class, null);

        TabHost.TabSpec tabSpec2 = host.newTabSpec("tab2");
        tabSpec2.setIndicator("ランダム解答");
        host.addTab(tabSpec2, SelectRandomFragment.class, null);

        return view;
    }

    // FragmentがActivityに関連付けられた時に一度だけ呼ばれる。
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(getArguments().getInt("QuizAnswerSection"));
    }
}
