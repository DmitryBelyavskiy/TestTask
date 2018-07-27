package com.goozix.androidtesttask.ui.editing_profile;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.bumptech.glide.Glide;
import com.goozix.androidtesttask.R;
import com.goozix.androidtesttask.mvp.model.user.User;
import com.goozix.androidtesttask.mvp.presenter.UpdateProfileActivityPresenter;
import com.goozix.androidtesttask.mvp.view.UpdateProfileView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UpdateProfileActivity extends MvpAppCompatActivity implements UpdateProfileView {

    private static final String USER = "user";

    @InjectPresenter
    UpdateProfileActivityPresenter presenter;

    @BindView(R.id.image_avatar_ed_prof)
    ImageView mImageAvatar;
    @BindView(R.id.et_new_name)
    EditText mEtNewName;
    @BindView(R.id.et_new_company)
    EditText mEtNewCompany;
    @BindView(R.id.et_new_email)
    EditText mEtNewEmail;

    @BindView(R.id.loading_indicator_upd_profile)
    ProgressBar mLoadingIndicator;

    private MenuItem mItemUpdate;


    @ProvidePresenter
    UpdateProfileActivityPresenter provideUpdateProfileActivityPresenter() {
        return new UpdateProfileActivityPresenter((User) getIntent().getParcelableExtra(USER));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        ButterKnife.bind(this);
        setListeners();

    }

    void setListeners() {
        mEtNewName.addTextChangedListener(addTextWatcher(mEtNewName));
        mEtNewCompany.addTextChangedListener(addTextWatcher(mEtNewCompany));
        mEtNewEmail.addTextChangedListener(addTextWatcher(mEtNewEmail));

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
                    case R.id.et_new_name:
                        presenter.usernameTextChanged(s.toString());
                        break;
                    case R.id.et_new_company:
                        presenter.companyTextChanged(s.toString());
                        break;
                    case R.id.et_new_email:
                        presenter.emailTextChanged(s.toString());
                        break;
                }
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.item_ok_update, menu);
        mItemUpdate = menu.findItem(R.id.item_ok);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_ok:
                presenter.buttonUpdateProfileClicked();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void showCurrentUserData(User user) {
        Glide.with(this).load(user.getAvatarUrl()).into(mImageAvatar);
        mEtNewName.setText(user.getName());
        mEtNewName.setHint(user.getName());

        mEtNewCompany.setText(user.getCompany());
        mEtNewCompany.setHint(user.getCompany());
        if (TextUtils.isEmpty(user.getEmail())) {

            mEtNewEmail.setHint(R.string.limited_by_privacy_settings);
            mEtNewEmail.setEnabled(false);
        } else {
            mEtNewEmail.setText(user.getEmail());
            mEtNewEmail.setHint(user.getEmail());
        }
    }

    @Override
    public void enableUpdateUserDataButton(boolean enabled) {
        if (mItemUpdate != null) {
            mItemUpdate.setEnabled(enabled);
        }
    }

    @Override
    public void showMessage(int messResId) {
        Toast.makeText(this, messResId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showUpdatedProfile(User user) {
        Intent intent = new Intent();
        intent.putExtra(USER, user);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void showProgressBar() {
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mLoadingIndicator.setVisibility(View.GONE);
    }


}
