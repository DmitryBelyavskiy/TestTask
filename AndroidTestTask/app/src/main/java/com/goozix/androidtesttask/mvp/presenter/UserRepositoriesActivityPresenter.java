package com.goozix.androidtesttask.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.goozix.androidtesttask.R;
import com.goozix.androidtesttask.application.MyTestApplication;
import com.goozix.androidtesttask.mvp.model.Model;
import com.goozix.androidtesttask.mvp.model.user.Repository;
import com.goozix.androidtesttask.mvp.view.UserRepositoriesView;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class UserRepositoriesActivityPresenter extends BasePresenter<UserRepositoriesView> {
    @Inject
    Model mModel;
    private String mLogin;

    public UserRepositoriesActivityPresenter(String loginUser) {
        MyTestApplication.getAppComponent().inject(this);
        this.mLogin = loginUser;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadRepositoriesList();
    }

    private void loadRepositoriesList() {
        unsubscribeOnDestroy(
                mModel.getListRepositories(mLogin)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                getViewState().showProgress();

                            }
                        })
                        .subscribe(new Consumer<List<Repository>>() {
                            @Override
                            public void accept(List<Repository> repositories) throws Exception {
                                getViewState().showUserRepositoriesList(repositories);
                                getViewState().hideProgress();

                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                getViewState().showMessage(R.string.err_of_user_repositories_list);
                                getViewState().hideProgress();
                            }
                        })
        );
    }

    public void refreshCalled() {
        loadRepositoriesList();
    }
}
