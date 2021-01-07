package com.shine.herostory.entity;


/**
 * @program: herostory
 * @description: 用户ID
 * @author: yczjy
 * @create: 2021-01-07 10:12
 **/

public class User {
    private Integer userId;

    private String heroAvatar;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getHeroAvatar() {
        return heroAvatar;
    }

    public void setHeroAvatar(String heroAvatar) {
        this.heroAvatar = heroAvatar;
    }
}
