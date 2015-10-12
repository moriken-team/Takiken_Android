package com.example.macuser.takiken;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* ログイン機能（API未完成のためコメントアウト）
        // SharedPreferences取得
        SharedPreferences pref = getSharedPreferences("user_data", Context.MODE_PRIVATE);
        // 設定値の取得
        String username = pref.getString("username", "1");

        Toast.makeText(this, username, Toast.LENGTH_LONG).show();

        Log.v("ログインチェック", username);

        if (!username.equals("name")) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);// どのクラスを対象にするか
            startActivity(intent);// 画面遷移
        }
        */

        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    // サイドバーのタッチ位置を定数に代入

    // HOME画面が未実装のためコメントアウト
    // public final static int Home = 0;

    public final static int ProblemAnswer = 0;
    public final static int MakingProblem = 1;
    public final static int Maps = 2;

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();

        // HOME画面が未実装のためコメントアウト
        //  if(position == Home) {// HOME
        //      fragmentManager.beginTransaction()
        //              .replace(R.id.container, HomeFragment.newInstance(position + 1))
        //              .commit();
        //  }

        if(position == ProblemAnswer) {// 問題解答
            fragmentManager.beginTransaction()
                    .replace(R.id.container, QuizAnswerFragment.newInstance(position + 1))
                    .commit();
        }else if(position == MakingProblem) {// 問題作成
            fragmentManager.beginTransaction()
                    .replace(R.id.container, MakingQuizFragment.newInstance(position + 1))
                    .commit();
//        }else if(position == Maps) {// たきざわMAP
//            Intent intent = new Intent(MainActivity.this, MapsActivity.class);// どのクラスを対象にするか
//            startActivity(intent);// 画面遷移
        }else if(position == Maps) {// MAP
            fragmentManager.beginTransaction()
                    .replace(R.id.container, MapsFragment.newInstance(position + 1))
                    .commit();
        }else{
            fragmentManager.beginTransaction()
                    .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                    .commit();
        }
    }

    public void onSectionAttached(int number) {
        switch (number - 1) {

            // HOME画面が未実装のためコメントアウト
            // case Home:
            //    mTitle = getString(R.string.title_home);
            //    break;

            case ProblemAnswer:
                mTitle = getString(R.string.title_problem_answer);
                break;
            case MakingProblem:
                mTitle = getString(R.string.title_making_problem);
                break;
            case Maps:
                mTitle = getString(R.string.title_map);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // ログアウト
        if (id == R.id.logout) {
            // プリファレンスの準備
            // MODE_PRIVATEでこのアプリだけが使用できるように設定
            SharedPreferences pref = getSharedPreferences("user_data", Context.MODE_PRIVATE);

            // プリファレンスに書き込むためのEditorオブジェクト取得
            SharedPreferences.Editor editor = pref.edit();
            // 削除
            editor.remove("username");
            editor.remove("password");
            // 削除の反映
            editor.commit();

            Toast.makeText(this, "ログアウトしました", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(MainActivity.this, LoginActivity.class);// どのクラスを対象にするか
            startActivity(intent);// 画面遷移

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        // FragmentがActivityに関連付けられた時に一度だけ呼ばれる。
        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
