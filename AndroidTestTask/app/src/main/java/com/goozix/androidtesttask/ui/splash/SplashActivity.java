package com.goozix.androidtesttask.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.goozix.androidtesttask.R;
import com.goozix.androidtesttask.mvp.presenter.SplashActivityPresenter;
import com.goozix.androidtesttask.mvp.view.SplashView;
import com.goozix.androidtesttask.ui.authentication.AuthActivity;
import com.goozix.androidtesttask.ui.basic_information.BaseInfActivity;

public class SplashActivity extends MvpAppCompatActivity implements SplashView {

    @InjectPresenter
    SplashActivityPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    public void openAuthScreen() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, AuthActivity.class);
                startActivity(i);
                finish();

            }
        }, 1 * 1000);
    }

    @Override
    public void skipAuthScreen() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, BaseInfActivity.class);
                startActivity(i);
                finish();
            }
        }, 1 * 1000);

    }
}
