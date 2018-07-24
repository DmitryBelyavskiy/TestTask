package com.goozix.androidtesttask.mvp.presenter;


import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.goozix.androidtesttask.application.MyTestApplication;
import com.goozix.androidtesttask.mvp.model.Model;
import com.goozix.androidtesttask.mvp.view.SplashView;

import javax.inject.Inject;

@InjectViewState
public class SplashActivityPresenter extends BasePresenter<SplashView> {
    @Inject
    Model mModel;

    public SplashActivityPresenter() {
        MyTestApplication.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        if(mModel.getUserTokenFromPrefs().equals("")){
            //getViewState().skipAuthScreen();
            getViewState().openAuthScreen();
        }
        else{
            getViewState().skipAuthScreen();
            //getViewState().openStartScreen();
        }
    }

}
