package com.github.mkapiczy.oauth_server.entity.dto;

public class ResourceResponse {
    private String appOwner;
    private String email;


    public ResourceResponse(String appOwner, String email) {
        this.appOwner = appOwner;
        this.email = email;
    }

    public String getAppOwner() {
        return appOwner;
    }

    public void setAppOwner(String appOwner) {
        this.appOwner = appOwner;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
