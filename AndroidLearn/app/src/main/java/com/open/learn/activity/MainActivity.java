package com.open.learn.activity;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MenuItem;

import com.open.learn.R;
import com.open.learn.adapter.HomePagerAdapter;
import com.open.learn.base.BaseActivity;
import com.open.learn.fragment.UserFragmentManager;
import com.open.learn.utils.BottomNavigationViewHelper;

public class MainActivity extends BaseActivity {
    private final static String TAG = "MainActivity";

    // bottom navigation
    private BottomNavigationView bottomNavigationView;
    private MenuItem currentItem; // current menu item

    // adapter
    private ViewPager mViewPager;
    private final static boolean allowScroll = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initData() {
        UserFragmentManager.INSTANCE.init();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);

        // bottom navigation
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> dealBottomItemSelected(item.getItemId()));
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        initViewPager();
    }

    private void initViewPager() {
        // init home adapter
        HomePagerAdapter mPagerAdapter = new HomePagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (currentItem != null) {
                    currentItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                currentItem = bottomNavigationView.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // disable viewpager scroll
        mViewPager.setOnTouchListener((v, event) -> !allowScroll);
    }

    @Override
    protected void loadData() {
        currentItem = bottomNavigationView.getMenu().getItem(0);
    }

    private boolean dealBottomItemSelected(int itemId) {
        // current id pretreatment
        if (itemId == currentItem.getItemId()) {
            Log.d(TAG, "onNavigationItemSelected not changed");
            return false;
        }
        // reset current item
        currentItem = bottomNavigationView.getMenu().findItem(itemId);
        // set viewpager item
        switch (itemId) {
            case R.id.navigation_fist:
                mViewPager.setCurrentItem(0);
                return true;
            case R.id.navigation_second:
                mViewPager.setCurrentItem(1);
                return true;
            case R.id.navigation_third:
                mViewPager.setCurrentItem(2);
                return true;
            case R.id.navigation_fourth:
                mViewPager.setCurrentItem(3);
                return true;
        }

        return false;
    }

}
