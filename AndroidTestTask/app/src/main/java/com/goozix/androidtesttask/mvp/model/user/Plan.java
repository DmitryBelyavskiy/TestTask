package com.goozix.androidtesttask.mvp.model.user;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Plan implements Parcelable {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("space")
    @Expose
    private Integer space;
    @SerializedName("collaborators")
    @Expose
    private Integer collaborators;
    @SerializedName("private_repos")
    @Expose
    private Integer privateRepos;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSpace() {
        return space;
    }

    public void setSpace(Integer space) {
        this.space = space;
    }

    public Integer getCollaborators() {
        return collaborators;
    }

    public void setCollaborators(Integer collaborators) {
        this.collaborators = collaborators;
    }

    public Integer getPrivateRepos() {
        return privateRepos;
    }

    public void setPrivateRepos(Integer privateRepos) {
        this.privateRepos = privateRepos;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeValue(this.space);
        dest.writeValue(this.collaborators);
        dest.writeValue(this.privateRepos);
    }

    public Plan() {
    }

    protected Plan(Parcel in) {
        this.name = in.readString();
        this.space = (Integer) in.readValue(Integer.class.getClassLoader());
        this.collaborators = (Integer) in.readValue(Integer.class.getClassLoader());
        this.privateRepos = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<Plan> CREATOR = new Creator<Plan>() {
        @Override
        public Plan createFromParcel(Parcel source) {
            return new Plan(source);
        }

        @Override
        public Plan[] newArray(int size) {
            return new Plan[size];
        }
    };
}
