package com.goozix.androidtesttask.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.goozix.androidtesttask.mvp.model.user.Repository;

import java.util.List;

public interface UserRepositoriesView extends MvpView {

    @StateStrategyType(AddToEndStrategy.class)
    void showUserRepositoriesList(List<Repository> Repositories);

    @StateStrategyType(SkipStrategy.class)
    void showMessage(int messResId);

    @StateStrategyType(AddToEndStrategy.class)
    void showProgress();

    @StateStrategyType(AddToEndStrategy.class)
    void hideProgress();

}
