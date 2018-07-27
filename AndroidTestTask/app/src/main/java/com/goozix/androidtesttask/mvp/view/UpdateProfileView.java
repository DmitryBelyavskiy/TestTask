package com.goozix.androidtesttask.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.goozix.androidtesttask.mvp.model.user.User;

public interface UpdateProfileView extends MvpView {
    @StateStrategyType(AddToEndStrategy.class)
    void showCurrentUserData(User user);

    @StateStrategyType(AddToEndStrategy.class)
    void enableUpdateUserDataButton(boolean enabled);

    @StateStrategyType(SkipStrategy.class)
    void showMessage(int messResId);

    @StateStrategyType(AddToEndStrategy.class)
    void showUpdatedProfile(User user);

    @StateStrategyType(AddToEndStrategy.class)
    void showProgressBar();

    @StateStrategyType(AddToEndStrategy.class)
    void hideProgressBar();
}
