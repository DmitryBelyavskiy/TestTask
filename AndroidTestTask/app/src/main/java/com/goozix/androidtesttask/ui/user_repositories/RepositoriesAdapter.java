package com.goozix.androidtesttask.ui.user_repositories;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.goozix.androidtesttask.R;
import com.goozix.androidtesttask.mvp.model.user.Repository;
import com.goozix.androidtesttask.mvp.model.user_interface.OnUserRepositoriesListClickListener;

import java.util.ArrayList;
import java.util.List;

public class RepositoriesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Repository> mUserRepositoriesList = new ArrayList<>();
    private OnUserRepositoriesListClickListener mListener;

    RepositoriesAdapter(OnUserRepositoriesListClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repository_list, parent, false);
        return new UserViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((UserViewHolder) holder).bind(mUserRepositoriesList.get(position));
    }

    @Override
    public int getItemCount() {
        return mUserRepositoriesList.size();
    }

    public void setItemsList(@NonNull List<Repository> repositories) {
        //тут не знаю что
        mUserRepositoriesList.clear();
        mUserRepositoriesList.addAll(repositories);
        notifyDataSetChanged();

    }


    class UserViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextName;
        private TextView mTextDescription;
        private Button mButtonOpenInBrowser;
        private OnUserRepositoriesListClickListener mListener;

        UserViewHolder(View itemView, OnUserRepositoriesListClickListener listener) {
            super(itemView);
            mListener = listener;
            mTextName = itemView.findViewById(R.id.text_name_rep);
            mTextDescription = itemView.findViewById(R.id.text_description_rep);
            mButtonOpenInBrowser = itemView.findViewById(R.id.button_open_repository_in_browser);
        }

        void bind(final Repository repository) {
            mTextName.setText(repository.getName());
            mTextDescription.setText(repository.getDescription());

            //itemView.setOnClickListener(new View.OnClickListener() {
            mButtonOpenInBrowser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getAdapterPosition() == RecyclerView.NO_POSITION) {
                        return;
                    }
                    String url = repository.getHtmlUrl();
                    mListener.openUserRepositoriesInBrowser(url);
                }
            });
        }
    }


}
