package com.goozix.androidtesttask.ui.basic_information;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.goozix.androidtesttask.R;
import com.goozix.androidtesttask.mvp.model.user.User;
import com.goozix.androidtesttask.mvp.model.user_interface.OnUsersListClickListener;
import com.goozix.androidtesttask.mvp.presenter.UsersListFragmentPresenter;
import com.goozix.androidtesttask.mvp.view.UsersListFragmentView;
import com.goozix.androidtesttask.ui.detailed_information.UserInformationActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;

public class UsersListFragment extends MvpAppCompatFragment implements UsersListFragmentView,
        SwipeRefreshLayout.OnRefreshListener, OnUsersListClickListener {

    private static final String USER = "user";


    @InjectPresenter
    UsersListFragmentPresenter presenter;

    @BindView(R.id.recycler_users_list)
    RecyclerView mRecycler;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefresh;


    SearchView mSearch;
    private int mFoundUsersPageCount;
    private String mFoundLogin;

    private LinearLayoutManager mLayoutManager;
    private RecyclerUsersListAdapter mAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_users_list, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initRecycler();
        initSwipeRefreshLayout();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search_by_login, menu);
        mSearch = (SearchView) menu.findItem(R.id.search_item).getActionView();
        initSearchView();

    }

    @Override
    public void onRefresh() {
        presenter.refreshCalled();
    }

    private void initRecycler() {
        mLayoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration itemDecoration=new DividerItemDecoration(getContext(),VERTICAL );
        mAdapter = new RecyclerUsersListAdapter(this);
        mRecycler.setLayoutManager(mLayoutManager);
        mRecycler.setAdapter(mAdapter);
        mRecycler.addOnScrollListener(new RecyclerAllUsersScrollingListener());
        mRecycler.addItemDecoration(itemDecoration);
    }

    @Override
    public void openUserInformation(User user) {
        Intent intent=new Intent(getContext(),UserInformationActivity.class);
        intent.putExtra(USER,user);
        startActivity(intent);
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
    public void showUserList(List<User> users, boolean clear) {
        mAdapter.setItemsList(users, clear);
    }

    private void initSearchView() {

        mSearch.setQueryHint(getString(R.string.search_by_login));
        mSearch.setOnQueryTextListener(new OnSearchLoginTextListener());
    }

    private class RecyclerAllUsersScrollingListener extends RecyclerView.OnScrollListener {

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            int visibleItemCount = mLayoutManager.getChildCount();
            int totalItemCount = mLayoutManager.getItemCount();
            int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();


            presenter.onScrolled(visibleItemCount, totalItemCount, firstVisibleItemPosition);
        }
    }

    @Override
    public void showMessage(int messResId) {
        Toast.makeText(getContext(), messResId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void clearList() {
        mAdapter.clearUsersList();
    }


    private class OnSearchLoginTextListener implements SearchView.OnQueryTextListener {

        @Override
        public boolean onQueryTextSubmit(String querylogin) {
            presenter.textSubmitted(querylogin);
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            if(TextUtils.isEmpty(newText)){
                presenter.textSubmitted(newText);
            }
            return false;
        }

    }
}
