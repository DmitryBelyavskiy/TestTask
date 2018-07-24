package com.goozix.androidtesttask.ui.detailed_information;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.goozix.androidtesttask.R;
import com.goozix.androidtesttask.mvp.model.user.User;
import com.goozix.androidtesttask.mvp.presenter.UserInformationActivityPresenter;
import com.goozix.androidtesttask.mvp.view.UserInformationView;
import com.goozix.androidtesttask.ui.basic_information.YourProfileFragment;

public class UserInformationActivity extends MvpAppCompatActivity implements UserInformationView {

    @InjectPresenter
    UserInformationActivityPresenter presenter;

    @ProvidePresenter
    UserInformationActivityPresenter provideTransactionPresenter() {
        return new UserInformationActivityPresenter((User) getIntent().getParcelableExtra("user"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
    }

    @Override
    public void loadFragmentProf(User user) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("user", user);

        YourProfileFragment frag = new YourProfileFragment();
        frag.setArguments(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fram_fr, frag);
        ft.commit();
    }
}
