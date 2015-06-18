package com.example.macuser.takiken;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
//import android.app.Fragment;利用しない
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    public static HomeFragment newInstance(int HomeSection) {
        // フラグメントの作成
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        // putInt（キーを指定して値を保存する。第1引数にキー、第2引数に保存する値を指定する。）
        args.putInt("HomeSection", HomeSection);
        fragment.setArguments(args);
        return fragment;
    }

    public HomeFragment() {
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);

//        // ボタンを取得して、ClickListenerをセット
//        Button btn = (Button)v.findViewById(R.id.button);
//        Button btn2 = (Button)v.findViewById(R.id.button2);
//
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Fragmentでthisを使うときgetActivityが必要
//                Intent intent = new Intent(getActivity(), MapsActivity.class);// どのクラスを対象にするか
//                startActivity(intent);// 画面遷移
//            }
//        });
//        btn2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Fragmentでthisを使うときgetActivityが必要
//                Intent intent = new Intent(getActivity(), MapsActivity.class);// どのクラスを対象にするか
//                startActivity(intent);// 画面遷移
//            }
//        });

        FragmentTabHost host = (FragmentTabHost) view.findViewById(android.R.id.tabhost);
        host.setup(getActivity(), getChildFragmentManager(), R.id.h_content);

        TabHost.TabSpec tabSpec1 = host.newTabSpec("tab1");
        tabSpec1.setIndicator("SNS");
        host.addTab(tabSpec1, SnsFragment.class, null);

        TabHost.TabSpec tabSpec2 = host.newTabSpec("tab2");
        tabSpec2.setIndicator("プロフィール");
        host.addTab(tabSpec2, SnsFragment.class, null);

        TabHost.TabSpec tabSpec3 = host.newTabSpec("tab3");
        tabSpec3.setIndicator("ランキング");
        host.addTab(tabSpec3, SnsFragment.class, null);

        return view;
    }

    // FragmentがActivityに関連付けられた時に一度だけ呼ばれる。
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(getArguments().getInt("HomeSection"));
    }
}
