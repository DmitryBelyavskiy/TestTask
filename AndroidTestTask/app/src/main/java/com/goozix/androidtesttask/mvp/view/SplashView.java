package com.goozix.androidtesttask.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

public interface SplashView extends MvpView {
    @StateStrategyType(SkipStrategy.class)
    void openAuthScreen();

    @StateStrategyType(SkipStrategy.class)
    void skipAuthScreen();
}
