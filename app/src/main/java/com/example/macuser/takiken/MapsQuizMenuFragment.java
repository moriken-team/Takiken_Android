package com.example.macuser.takiken;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapsQuizMenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapsQuizMenuFragment extends Fragment {
    public static MapsQuizMenuFragment newInstance(Double latitude, Double longitude, String spotId) {
        MapsQuizMenuFragment fragment = new MapsQuizMenuFragment();
        Bundle contents = new Bundle();
        contents.putDouble("latitude", latitude);
        contents.putDouble("longitude", longitude);
        contents.putString("spotId", spotId);
        fragment.setArguments(contents);
        return fragment;
    }

    public MapsQuizMenuFragment() {
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
        View view = inflater.inflate(R.layout.fragment_maps_quiz_menu, container, false);

        FragmentTabHost host = (FragmentTabHost) view.findViewById(android.R.id.tabhost);
        host.setup(getActivity(), getChildFragmentManager(), R.id.mq_content);

        TabHost.TabSpec tabSpec1 = host.newTabSpec("tab1");
        tabSpec1.setIndicator("問題解答");
        Bundle spot = new Bundle();
        spot.putString("spotId", getArguments().getString("spotId"));
        host.addTab(tabSpec1, MapsQuizAnswerMenuFragment.class, spot);

        TabHost.TabSpec tabSpec2 = host.newTabSpec("tab2");
        tabSpec2.setIndicator("問題作成");
        Bundle positions = new Bundle();
        positions.putDouble("latitude", getArguments().getDouble("latitude"));
        positions.putDouble("longitude", getArguments().getDouble("longitude"));
        positions.putString("spotId", getArguments().getString("spotId"));
        host.addTab(tabSpec2, MapsMakingQandAFragment.class, positions);

        return view;
    }
}
