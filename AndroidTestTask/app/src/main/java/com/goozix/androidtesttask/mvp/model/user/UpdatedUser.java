package com.goozix.androidtesttask.mvp.model.user;

public class UpdatedUser {


    private String name;
    private String company;
    private String email;

    public UpdatedUser(String name, String company, String email) {
        this.name = name;
        this.company = company;
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setCompany(String company) {
        this.company = company;
    }
    public String getCompany() {
        return company;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
