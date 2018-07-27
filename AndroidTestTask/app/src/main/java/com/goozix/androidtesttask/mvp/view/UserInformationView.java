package com.goozix.androidtesttask.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.goozix.androidtesttask.mvp.model.user.User;

public interface UserInformationView extends MvpView {
    @StateStrategyType(AddToEndStrategy.class)
    void loadFragmentProf(User user);
}
