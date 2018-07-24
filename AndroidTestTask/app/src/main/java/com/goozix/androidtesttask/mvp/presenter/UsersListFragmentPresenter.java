package com.goozix.androidtesttask.mvp.presenter;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.goozix.androidtesttask.R;
import com.goozix.androidtesttask.application.MyTestApplication;
import com.goozix.androidtesttask.mvp.model.Model;
import com.goozix.androidtesttask.mvp.model.user.User;
import com.goozix.androidtesttask.mvp.view.UsersListFragmentView;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class UsersListFragmentPresenter extends BasePresenter<UsersListFragmentView> {

    private static final int PAGE_SIZE = 30;

    @Inject
    Model mModel;

    private int lastUserId;
    private boolean mIsLoading;
    private boolean mIsLastPage;
    private String mQuery;
    private int mPageCount;

    public UsersListFragmentPresenter() {
        MyTestApplication.getAppComponent().inject(this);
        mQuery ="";
        mPageCount=1;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    private CompositeDisposable compositeDisposable = new CompositeDisposable();


    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getUsersList(true);

    }

    private void getUsersList(final boolean showProgress) {
        compositeDisposable.add(
                mModel.getListUs(lastUserId, mQuery, mPageCount)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                mIsLoading = true;
                                if (showProgress) {

                                    getViewState().showProgress();
                                }
                            }
                        })
                        .subscribe(new Consumer<List<User>>() {
                            @Override
                            public void accept(List<User> users) throws Exception {
                                getViewState().showUserList(users, showProgress);
                                getViewState().hideProgress();
                                if(users.size()>0){
                                    lastUserId = users.get(users.size() - 1).getId();
                                }
                                if (users.size() % PAGE_SIZE != 0) {
                                    mIsLastPage = true;
                                }
                                mIsLoading = false;
                                mPageCount++;
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                getViewState().showMessage(R.string.err_of_getListUs);
                                getViewState().hideProgress();
                                mIsLoading = false;
                            }
                        })
        );
    }


    public void refreshCalled() {
        lastUserId = 0;
        mPageCount=1;
        mIsLastPage = false;
        getUsersList(true);
    }

    public void onScrolled(int visibleItemCount, int totalItemCount, int firstVisibleItemPosition) {


        if (!mIsLoading && !mIsLastPage) {
            if (firstVisibleItemPosition >= (totalItemCount - PAGE_SIZE / 2) && firstVisibleItemPosition >= 0) {


                getUsersList(false);

            }
        }
    }

    public void textSubmitted(@NonNull String query) {
        mQuery =query;
        mPageCount=1;
        lastUserId=0;
        getViewState().clearList();
        getUsersList(true);
    }

}
