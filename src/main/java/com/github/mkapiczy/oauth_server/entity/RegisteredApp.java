package com.github.mkapiczy.oauth_server.entity;

import javax.persistence.*;

@Entity
public class RegisteredApp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String password;
    private String appId;
    private String appSecret;
    @OneToOne
    private Code authorizationCode;
    @OneToOne
    private Code AccessTokenCode;
    @OneToOne
    private Code refreshToken;

    public RegisteredApp() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Code getAuthorizationCode() {
        return authorizationCode;
    }

    public void setAuthorizationCode(Code authorizationCode) {
        this.authorizationCode = authorizationCode;
    }

    public Code getAccessTokenCode() {
        return AccessTokenCode;
    }

    public void setAccessTokenCode(Code accessTokenCode) {
        AccessTokenCode = accessTokenCode;
    }

    public Code getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(Code refreshToken) {
        this.refreshToken = refreshToken;
    }
}
