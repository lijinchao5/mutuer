package com.xuanli.oepcms.entity;

public class UserMobileEntity extends UserEntity {
    private Long userId;

    private String appId;

    private String appTokenId;

    private Integer by1;

    private String by2;

    private String by3;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppTokenId() {
        return appTokenId;
    }

    public void setAppTokenId(String appTokenId) {
        this.appTokenId = appTokenId;
    }

    public Integer getBy1() {
        return by1;
    }

    public void setBy1(Integer by1) {
        this.by1 = by1;
    }

    public String getBy2() {
        return by2;
    }

    public void setBy2(String by2) {
        this.by2 = by2;
    }

    public String getBy3() {
        return by3;
    }

    public void setBy3(String by3) {
        this.by3 = by3;
    }
}