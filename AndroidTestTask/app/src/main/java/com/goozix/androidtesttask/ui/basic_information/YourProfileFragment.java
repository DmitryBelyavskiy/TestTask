package com.goozix.androidtesttask.ui.basic_information;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.bumptech.glide.Glide;
import com.goozix.androidtesttask.R;
import com.goozix.androidtesttask.mvp.model.user.User;
import com.goozix.androidtesttask.mvp.presenter.YourProfileFragmentPresenter;
import com.goozix.androidtesttask.mvp.view.YourProfileFragmentView;
import com.goozix.androidtesttask.ui.authentication.AuthActivity;
import com.goozix.androidtesttask.ui.editing_profile.UpdateProfileActivity;
import com.goozix.androidtesttask.ui.user_repositories.UserRepositoriesActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;
import static com.goozix.androidtesttask.util.Constants.LOGIN;
import static com.goozix.androidtesttask.util.Constants.NULL_FIELD;
import static com.goozix.androidtesttask.util.Constants.REQUEST_CODE;
import static com.goozix.androidtesttask.util.Constants.USER;

public class YourProfileFragment extends MvpAppCompatFragment implements YourProfileFragmentView {

    @InjectPresenter
    YourProfileFragmentPresenter mPresenter;

    @BindView(R.id.image_avatar_prof)
    ImageView mImageAva;
    @BindView(R.id.text_pr_login)
    TextView mTvLogin;
    @BindView(R.id.text_pr_name)
    TextView mTvName;
    @BindView(R.id.text_pr_company)
    TextView mTvCompany;
    @BindView(R.id.text_pr_email)
    TextView mTvEmail;
    @BindView(R.id.text_pr_rep_gist_count)
    TextView mTvGist;
    @BindView(R.id.text_pr_tot_pv_rep)
    TextView mTvCountRepos;
    @BindView(R.id.text_own_pr_rep_con)
    TextView mTvCountOwnRepos;
    @BindView(R.id.bt_open_repos)
    Button mBtnOpenRepos;
    @BindView(R.id.bt_edit_profile)
    Button mBtnEditProfile;

    @BindView(R.id.ll_private_inf)
    LinearLayout mLlPrivateInfo;

    @BindView(R.id.ll_gist)
    LinearLayout mLlGist;
    @BindView(R.id.ll_pr_tot_rep)
    LinearLayout mLlPrivateTotalRepositoryCount;
    @BindView(R.id.ll_own_pr_rep_con)
    LinearLayout mLlOwnedPrivateRepositoriesCount;
    @BindView(R.id.ll_bt_ed_prof)
    LinearLayout mLlBtnEditProfile;

    @BindView(R.id.layout_your_profile)
    LinearLayout mLlYourProf;
    @BindView(R.id.loading_indicator_profile)
    ProgressBar mLoadingIndicator;

    @ProvidePresenter
    YourProfileFragmentPresenter yourProfileFragmentPresenter() {
        if (getArguments() == null) {
            return new YourProfileFragmentPresenter(null);
        } else {
            return new YourProfileFragmentPresenter((User) getArguments().getParcelable(USER));
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_your_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mBtnOpenRepos.setOnClickListener(oclBtnOpenRepos);
        mBtnEditProfile.setOnClickListener(oclBtnEditProfile);
    }

    @Override
    public void changeVisibilityItems() {
        mLlPrivateInfo.setVisibility(View.VISIBLE);
        mLlBtnEditProfile.setVisibility(View.VISIBLE);
        mBtnEditProfile.setEnabled(true);
    }

    @Override
    public void showItemUserData(User user, boolean visible) {
        Glide.with(this).load(user.getAvatarUrl()).into(mImageAva);
        mTvLogin.setText(user.getLogin());
        mTvName.setText((user.getName()));
        mTvName.setText(checkFieldForEmpty(user.getName()));
        mTvCompany.setText(checkFieldForEmpty(user.getCompany()));
        mTvEmail.setText(checkFieldForEmpty(user.getEmail()));
        if (visible) {
            mTvGist.setText(String.valueOf(user.getPrivateGists()));
            mTvCountRepos.setText(String.valueOf(user.getTotalPrivateRepos()));
            mTvCountOwnRepos.setText(String.valueOf(user.getOwnedPrivateRepos()));
            setHasOptionsMenu(true);
        }
    }

    @Override
    public void showMessage(int messResId) {
        Toast.makeText(getContext(), messResId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressBar() {
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mLoadingIndicator.setVisibility(View.GONE);
    }

    @Override
    public void showLayoutProfile() {
        mLlYourProf.setVisibility(View.VISIBLE);
    }


    private String checkFieldForEmpty(String str) {
        if (TextUtils.isEmpty(str)) {
            return NULL_FIELD;
        } else {
            return str;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.item_logout, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_logout) {
            mPresenter.logout();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void backToAuthentication() {
        startActivity(new Intent(getActivity(), AuthActivity.class));
    }

    View.OnClickListener oclBtnOpenRepos = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mPresenter.buttonRepositoryListClicked();
        }
    };

    @Override
    public void openRepositoryList(String userLogin) {
        Intent intent = new Intent(getContext(), UserRepositoriesActivity.class);
        if (userLogin != null) {
            intent.putExtra(LOGIN, userLogin);
        }
        startActivity(intent);
    }

    @Override
    public void openEditProfileScreen(User user) {
        Intent intent = new Intent(getContext(), UpdateProfileActivity.class);
        intent.putExtra(USER, user);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            User user = data.getParcelableExtra(USER);
            mPresenter.updateProfile(user);
        }
    }

    View.OnClickListener oclBtnEditProfile = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mPresenter.buttonEditProfileClicked();
        }
    };
}
