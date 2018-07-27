package com.goozix.androidtesttask.di;

import com.goozix.androidtesttask.di.modules.ContextModule;
import com.goozix.androidtesttask.di.modules.ModelModule;
import com.goozix.androidtesttask.mvp.model.ModelImpl;
import com.goozix.androidtesttask.mvp.presenter.AuthActivityPresenter;
import com.goozix.androidtesttask.mvp.presenter.BaseInfActivityPresenter;
import com.goozix.androidtesttask.mvp.presenter.SplashActivityPresenter;
import com.goozix.androidtesttask.mvp.presenter.UpdateProfileActivityPresenter;
import com.goozix.androidtesttask.mvp.presenter.UserInformationActivityPresenter;
import com.goozix.androidtesttask.mvp.presenter.UserRepositoriesActivityPresenter;
import com.goozix.androidtesttask.mvp.presenter.UsersListFragmentPresenter;
import com.goozix.androidtesttask.mvp.presenter.YourProfileFragmentPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ContextModule.class, ModelModule.class})
public interface AppComponent {

    void inject(SplashActivityPresenter splashPresenter);

    void inject(AuthActivityPresenter authActivityPresenter);

    void inject(BaseInfActivityPresenter baseInfActivityPresenter);

    void inject(UsersListFragmentPresenter usersListFragmentPresenter);

    void inject(YourProfileFragmentPresenter yourProfileFragmentPresenter);

    void inject(UserInformationActivityPresenter userInformationActivityPresenter);

    void inject(UserRepositoriesActivityPresenter userRepositoriesActivityPresenter);

    void inject(UpdateProfileActivityPresenter updateProfileActivityPresenter);

    void inject(ModelImpl dataRepository);
}
