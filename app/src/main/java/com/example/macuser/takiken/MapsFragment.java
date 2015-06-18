package com.example.macuser.takiken;


import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
// 位置情報を利用するには「implements LocationListener」が必要
public class MapsFragment extends Fragment  implements LocationListener {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
//    private static View view;

    private LocationManager mLocationManager;



    public static MapsFragment newInstance(int MapsSection) {
        // フラグメントの作成
        MapsFragment fragment = new MapsFragment();
        Bundle args = new Bundle();
        // putInt（キーを指定して値を保存する。第1引数にキー、第2引数に保存する値を指定する。）
        args.putInt("MapsSection", MapsSection);
        fragment.setArguments(args);
        return fragment;
    }

    public MapsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // LocationManagerを取得
        mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_maps, container, false);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mLocationManager != null) {
            mLocationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
//                LocationManager.NETWORK_PROVIDER,
                    0,
                    0,
                    this);
        }

        setUpMapIfNeeded();
    }

    @Override
    public void onPause() {
        if (mLocationManager != null) {
            mLocationManager.removeUpdates(this);
        }

        super.onPause();
    }




    /* ---------- START LocationListenerのメソッド（位置情報） ---------- */
    @Override
    public void onLocationChanged(Location location) {
        Log.v("----------", "----------");
        Log.v("Latitude", String.valueOf(location.getLatitude()));
        Log.v("Longitude", String.valueOf(location.getLongitude()));
        Log.v("Accuracy", String.valueOf(location.getAccuracy()));
        Log.v("Altitude", String.valueOf(location.getAltitude()));
        Log.v("Time", String.valueOf(location.getTime()));
        Log.v("Speed", String.valueOf(location.getSpeed()));
        Log.v("Bearing", String.valueOf(location.getBearing()));
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        switch (i) {
            case LocationProvider.AVAILABLE:
                Log.v("Status", "AVAILABLE");
                break;
            case LocationProvider.OUT_OF_SERVICE:
                Log.v("Status", "OUT_OF_SERVICE");
                break;
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                Log.v("Status", "TEMPORARILY_UNAVAILABLE");
                break;
        }
    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
    /* ---------- END LocationListenerのメソッド（位置情報） ---------- */





    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link com.google.android.gms.maps.SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragment_map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
//        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));

        mMap.getUiSettings().setZoomControlsEnabled(true);// 拡大縮小ボタン表示

        LatLng morioka = new LatLng(39.703531, 141.152667);// 盛岡
        LatLng takizawa = new LatLng(39.734694, 141.077056);// 滝沢

        CameraPosition.Builder camerapos = new CameraPosition.Builder();// 表示位置の作成
        camerapos.target(morioka);// カメラの表示位置の指定
        camerapos.zoom(13.0f);// ズームレベル
        camerapos.bearing(0);// カメラの向きの指定(北向きなので０）
        camerapos.tilt(25.0f);// カメラの傾き設定
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(camerapos.build()));// マップの表示位置変更

        MarkerOptions options = new MarkerOptions();// ピンの設定
        options.position(morioka);// ピンの場所を指定
        options.title("盛岡");// マーカーの吹き出しの設定
        mMap.addMarker(options);// ピンの設置

        options.position(takizawa);
        options.title("滝沢");
        mMap.addMarker(options);


//        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//            @Override
//            public boolean onMarkerClick(Marker marker) {
//                // TODO Auto-generated method stub
//
//                // へ画面遷移
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//
//                fragmentManager.beginTransaction()
//                        .replace(R.id.container, QuestionsAndAnswersFragment.newInstance())
//                        .commit();
//
//                Toast.makeText(getActivity(), "マーカータップ", Toast.LENGTH_LONG).show();
//                return false;
//            }
//        });

        //吹き出しのクリックリスナー
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                // TODO Auto-generated method stub
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//
                fragmentManager.beginTransaction()
                        .replace(R.id.container, QuestionsAndAnswersFragment.newInstance())
                        .commit();
            }
        });
    }








    // FragmentがActivityに関連付けられた時に一度だけ呼ばれる。
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(getArguments().getInt("MapsSection"));
    }
}