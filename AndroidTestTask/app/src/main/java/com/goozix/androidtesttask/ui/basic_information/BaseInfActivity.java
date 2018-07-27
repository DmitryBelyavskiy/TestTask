package com.goozix.androidtesttask.ui.basic_information;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.goozix.androidtesttask.R;
import com.goozix.androidtesttask.mvp.presenter.BaseInfActivityPresenter;
import com.goozix.androidtesttask.mvp.view.BaseInfActivityView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BaseInfActivity extends MvpAppCompatActivity implements BaseInfActivityView {

    @InjectPresenter
    BaseInfActivityPresenter mPresenter;

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;

    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_inf);
        ButterKnife.bind(this);
        initViewPager();
    }

    private void initViewPager() {
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), 2);
        mViewPager.setAdapter(pagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    public class PagerAdapter extends FragmentStatePagerAdapter {
        private static final int USERS_LIST_TAB = 0;
        private static final int YOUR_PROFILE_TAB = 1;
        private int mTabsCount;

        private PagerAdapter(FragmentManager fm, int tabCount) {
            super(fm);
            mTabsCount = tabCount;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case USERS_LIST_TAB:
                    return new UsersListFragment();
                case YOUR_PROFILE_TAB:
                    return new YourProfileFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return mTabsCount;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case USERS_LIST_TAB:
                    return getString(R.string.users_list);
                case YOUR_PROFILE_TAB:
                    return getString(R.string.your_profile);
                default:
                    return "";
            }
        }
    }
}
