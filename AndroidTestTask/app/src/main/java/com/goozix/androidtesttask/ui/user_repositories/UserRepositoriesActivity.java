package com.goozix.androidtesttask.ui.user_repositories;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.goozix.androidtesttask.R;
import com.goozix.androidtesttask.mvp.model.user.Repository;
import com.goozix.androidtesttask.mvp.model.user_interface.OnUserRepositoriesListClickListener;
import com.goozix.androidtesttask.mvp.presenter.UserRepositoriesActivityPresenter;
import com.goozix.androidtesttask.mvp.view.UserRepositoriesView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;
import static com.goozix.androidtesttask.util.Constants.LOGIN;

public class UserRepositoriesActivity extends MvpAppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,
        UserRepositoriesView, OnUserRepositoriesListClickListener {

    @InjectPresenter
    UserRepositoriesActivityPresenter mPresenter;

    @BindView(R.id.recycler_user_repositories)
    RecyclerView mRecycler;
    @BindView(R.id.swipe_refresh_layout_repositories_list)
    SwipeRefreshLayout mSwipeRefresh;

    private RepositoriesAdapter mAdapter;

    @ProvidePresenter
    UserRepositoriesActivityPresenter provideUserRepositoriesActivityPresenter() {//изменить в названии в других использованиях
        return new UserRepositoriesActivityPresenter(
                getIntent() != null ?
                        getIntent().getStringExtra(LOGIN) : null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_repositories);
        ButterKnife.bind(this);
        initRecycleView();
        initSwipeRefreshLayout();
    }

    @Override
    public void onRefresh() {
        mPresenter.refreshCalled();
    }

    private void initRecycleView() {
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, VERTICAL);
        mAdapter = new RepositoriesAdapter(this);
        mRecycler.setAdapter(mAdapter);
        mRecycler.addItemDecoration(itemDecoration);
    }

    private void initSwipeRefreshLayout() {
        mSwipeRefresh.setOnRefreshListener(this);
        mSwipeRefresh.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
    }

    @Override
    public void showProgress() {
        mSwipeRefresh.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        mSwipeRefresh.setRefreshing(false);
    }

    @Override
    public void showUserRepositoriesList(List<Repository> repositories) {
        mAdapter.setItemsList(repositories);
    }

    @Override
    public void openUserRepositoriesInBrowser(String url) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    @Override
    public void showMessage(int messResId) {
        Toast.makeText(this, messResId, Toast.LENGTH_SHORT).show();
    }

}
