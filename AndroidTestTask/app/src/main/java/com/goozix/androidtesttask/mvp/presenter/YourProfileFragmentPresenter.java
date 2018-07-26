package com.goozix.androidtesttask.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.goozix.androidtesttask.R;
import com.goozix.androidtesttask.application.MyTestApplication;
import com.goozix.androidtesttask.mvp.model.Model;
import com.goozix.androidtesttask.mvp.model.user.User;
import com.goozix.androidtesttask.mvp.view.YourProfileFragmentView;
import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class YourProfileFragmentPresenter extends BasePresenter<YourProfileFragmentView> {

    @Inject
    Model mModel;
    private User mUser;
    private boolean isMyProfile;

    public YourProfileFragmentPresenter(User user) {
        this.mUser = user;
        /*if(mUser==null){
            isMyProfile=true;
        }*/
        isMyProfile =mUser==null;
        MyTestApplication.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        if (isMyProfile) {//mUser == null
            getViewState().changeVisibilityItems();
            //снять невидимость, будет отображаться расширенный профиль
            loadProfileData();


            //перенести вызов в loadProfileData();
           // getViewState().showItemUserData(mUser, true);//если true заполняем расширенный профиль
        } else {
            //mUserLogin =mUser.getLogin();//для отправки в репозиторий лист
            getViewState().hideProgressBar();
            getViewState().showLayoutProfile();
            getViewState().showItemUserData(mUser, isMyProfile);
        }
    }


    //разобраться подробнее
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private void loadProfileData() {

        compositeDisposable.add(
                mModel.loadProfAuthUser(mModel.getUserTokenFromPrefs())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                getViewState().showProgressBar();
                            }
                        })
                .subscribe(new Consumer<User>() {
                    @Override
                    public void accept(User user) throws Exception {
                        mUser=user;
                        getViewState().showItemUserData(mUser, isMyProfile);
                        getViewState().hideProgressBar();
                        getViewState().showLayoutProfile();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getViewState().showMessage(R.string.err_of_get_Profile_User);
                        getViewState().hideProgressBar();
                        getViewState().showLayoutProfile();
                    }
                })
        );

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    public void logout() {
        mModel.removeUserToken();
        getViewState().backToAuthentication();
    }

    public void buttonRepositoryListClicked(){

        if(isMyProfile){getViewState().openRepositoryList(null);//отправиться либо пустой либо логин
        }
        else{getViewState().openRepositoryList(mUser.getLogin());}

    }

    public void buttonEditProfileClicked(){

        getViewState().openEditProfileScreen(mUser);

    }

    public void updateProfile(User user){
        mUser=user;
        getViewState().showItemUserData(mUser, isMyProfile);
    }
}
