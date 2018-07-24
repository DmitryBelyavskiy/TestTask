package com.goozix.androidtesttask.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.goozix.androidtesttask.mvp.model.user.User;

public interface YourProfileFragmentView extends MvpView {
    @StateStrategyType(AddToEndStrategy.class)
    void changeVisibilityItems();
    @StateStrategyType(AddToEndStrategy.class)
    void showItemUserData(User user, boolean visible);
    @StateStrategyType(AddToEndStrategy.class)
    void showMessage(int messResId);
    @StateStrategyType(AddToEndStrategy.class)
    void showProgressBar();
    @StateStrategyType(AddToEndStrategy.class)
    void hideProgressBar();
    @StateStrategyType(AddToEndStrategy.class)
    void showLayoutProfile();
    @StateStrategyType(AddToEndStrategy.class)
    void backToAuthentication();
}
