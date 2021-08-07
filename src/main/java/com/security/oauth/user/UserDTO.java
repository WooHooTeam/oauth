package com.security.oauth.user;

import javax.persistence.Column;

public class UserDTO {
    private Integer id;
    private String username;
    private int userType;
    private String koreanname;
    private String birthday;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getKoreanname() {
        return koreanname;
    }

    public void setKoreanname(String koreanname) {
        this.koreanname = koreanname;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
