package com.goozix.androidtesttask.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

public interface AuthActivityView extends MvpView {

    @StateStrategyType(AddToEndStrategy.class)
    void enableLoginButton(boolean enabled);

    @StateStrategyType(AddToEndStrategy.class)
    void onLoginComplete();

    @StateStrategyType(SkipStrategy.class)
    void showMessage(int messResId);
}
