package com.goozix.androidtesttask.ui.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.goozix.androidtesttask.R;
import com.goozix.androidtesttask.mvp.presenter.AuthActivityPresenter;
import com.goozix.androidtesttask.mvp.view.AuthActivityView;
import com.goozix.androidtesttask.ui.basic_information.BaseInfActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AuthActivity extends MvpAppCompatActivity implements AuthActivityView, View.OnClickListener {

    @InjectPresenter
    AuthActivityPresenter mPresenter;

    @BindView(R.id.et_username_text)
    EditText mEtUsername;
    @BindView(R.id.user_name_layout)
    TextInputLayout mUserNameLayout;

    @BindView(R.id.et_password_text)
    EditText mEtPassword;
    @BindView(R.id.password_layout)
    TextInputLayout mPasswordLayout;

    @BindView(R.id.btn_login)
    Button mBtnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        ButterKnife.bind(this);
        setListeners();
    }

    void setListeners() {
        mBtnLogin.setOnClickListener(this);
        mEtUsername.addTextChangedListener(addTextWatcher(mEtUsername));
        mEtPassword.addTextChangedListener(addTextWatcher(mEtPassword));
    }

    private TextWatcher addTextWatcher(final EditText editText) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                switch (editText.getId()) {
                    case R.id.et_username_text:
                        mPresenter.usernameTextChanged(s.toString());
                        break;
                    case R.id.et_password_text:
                        mPresenter.passwordTextChanged(s.toString());
                        break;
                }
            }
        };
    }

    @Override
    public void enableLoginButton(boolean enabled) {
        mBtnLogin.setEnabled(enabled);
    }

    @Override
    public void onClick(View v) {
        mPresenter.loginClicked();
    }

    public void onLoginComplete() {
        startActivity(new Intent(this, BaseInfActivity.class));
        finish();
    }

    @Override
    public void showMessage(int messResId) {
        Toast.makeText(this, messResId, Toast.LENGTH_SHORT).show();
    }


}

