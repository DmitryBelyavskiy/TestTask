package com.goozix.androidtesttask.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.goozix.androidtesttask.mvp.model.user.User;

import java.util.List;

public interface UsersListFragmentView extends MvpView {

    @StateStrategyType(AddToEndStrategy.class)
    void showProgress();

    @StateStrategyType(AddToEndStrategy.class)
    void hideProgress();

    @StateStrategyType(AddToEndStrategy.class)
    void showUserList(List<User> users, boolean clear);

    @StateStrategyType(SkipStrategy.class)
    void showMessage(int messResId);

    @StateStrategyType(SkipStrategy.class)
    void clearList();

}
