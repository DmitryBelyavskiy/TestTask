package com.goozix.androidtesttask.ui.basic_information;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.goozix.androidtesttask.R;
import com.goozix.androidtesttask.mvp.model.user.User;
import com.goozix.androidtesttask.mvp.model.user_interface.OnUsersListClickListener;

import java.util.ArrayList;
import java.util.List;

public class RecyclerUsersListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<User> mUsersList = new ArrayList<>();
    private OnUsersListClickListener mListener;


    RecyclerUsersListAdapter(OnUsersListClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_list, parent, false);
        return new UserViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((UserViewHolder) holder).bind(mUsersList.get(position));
    }
    void clearUsersList() {

        mUsersList.clear();
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return mUsersList.size();
    }

    public void setItemsList(@NonNull List<User> itemsList, boolean clear) {
        if (!clear) {
            int pos=mUsersList.size();
            mUsersList.addAll(itemsList);
            notifyItemRangeInserted(pos,itemsList.size());
        } else {
            mUsersList.clear();
            mUsersList.addAll(itemsList);
            notifyDataSetChanged();
        }

    }

    class UserViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextLogin;
        private ImageView mImageAvatar;
        private OnUsersListClickListener mListener;

        UserViewHolder(View itemView, OnUsersListClickListener listener) {
            super(itemView);
            mListener = listener;
            mTextLogin = itemView.findViewById(R.id.text_login);
            mImageAvatar = itemView.findViewById(R.id.image_avatar);
        }

        void bind(final User user) {
            mTextLogin.setText(user.getLogin());
            Glide.with(itemView.getContext()).load(user.getAvatarUrl()).into(mImageAvatar);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getAdapterPosition() == RecyclerView.NO_POSITION) {
                        return;
                    }
                    mListener.openUserInformation(user);//можно передать user и потом в активити передать юзера
                    //mListener.openUserInformation(user.getLogin());//можно передать user и потом в активити передать юзера
                }
            });
        }
    }
}
