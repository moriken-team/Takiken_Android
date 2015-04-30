package com.example.macuser.takiken;


import android.app.Activity;
import android.os.Bundle;
//import android.app.Fragment;利用しない
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MakingProblemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MakingProblemFragment extends Fragment {

    public static MakingProblemFragment newInstance(int MakingProblemASection) {
        // フラグメントの作成
        MakingProblemFragment fragment = new MakingProblemFragment();
        Bundle args = new Bundle();
        // putInt（キーを指定して値を保存する。第1引数にキー、第2引数に保存する値を指定する。）
        args.putInt("MakingProblemASection", MakingProblemASection);
        fragment.setArguments(args);
        return fragment;
    }

    public MakingProblemFragment() {
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
        return inflater.inflate(R.layout.fragment_making_problem, container, false);
    }

    // FragmentがActivityに関連付けられた時に一度だけ呼ばれる。
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(getArguments().getInt("MakingProblemASection"));
    }
}
