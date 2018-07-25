package com.goozix.androidtesttask.ui.basic_information;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.goozix.androidtesttask.ui.user_repositories.UserRepositoriesActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class YourProfileFragment extends MvpAppCompatFragment implements YourProfileFragmentView {

    private static final String NULL_FIELD = "Empty";
    private static final String LOGIN = "Login"; //вытащить в отдельный фал

    @InjectPresenter
    YourProfileFragmentPresenter presenter;

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
    Button mBtOpenRepos;
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
    YourProfileFragmentPresenter provideTransactionPresenter() {
        if (getArguments() == null) {
            return new YourProfileFragmentPresenter(null);
        } else {
            return new YourProfileFragmentPresenter((User) getArguments().getParcelable("user"));
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
        // setHasOptionsMenu(true);//для логаута
        ButterKnife.bind(this, view);
        mBtOpenRepos.setOnClickListener(oclBtnOpenRepos);
        mBtnEditProfile.setOnClickListener(oclBtnEditProfile);
    }

    @Override
    public void changeVisibilityItems() {
        mLlPrivateInfo.setVisibility(View.VISIBLE);
//        mLlGist.setVisibility(View.VISIBLE);
//        mLlPrivateTotalRepositoryCount.setVisibility(View.VISIBLE);
//        mLlOwnedPrivateRepositoriesCount.setVisibility(View.VISIBLE);
        mLlBtnEditProfile.setVisibility(View.VISIBLE);
        mBtnEditProfile.setEnabled(true);
    }

    @Override
    public void showItemUserData(User user, boolean visible) {
        //устанавливаем соответствующие значения
        Glide.with(this).load(user.getAvatarUrl()).into(mImageAva);
        mTvLogin.setText(user.getLogin());
        mTvName.setText(checkFieldForEmpty(user.getName()));
        mTvCompany.setText(checkFieldForEmpty(user.getCompany()));
        mTvEmail.setText(checkFieldForEmpty(user.getEmail()));

        if (visible) {//доп параметры для авторизованного пользователя
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
        if (str == null) {
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
            presenter.logout();
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
            presenter.buttonRepositoryListClicked();
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

    View.OnClickListener oclBtnEditProfile = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //todo: later create profile editing
            // presenter.buttonEditProfileClicked();
        }
    };
}
