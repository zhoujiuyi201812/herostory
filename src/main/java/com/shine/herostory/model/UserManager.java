package com.shine.herostory.model;

import com.shine.herostory.entity.User;

import java.util.*;

/**
 * @program: herostory
 * @description: 用户管理
 * @author: yczjy
 * @create: 2021-01-07 16:10
 **/
public final class UserManager {

    private UserManager() {
    }

    static private Map<Integer, User> userInfos = new HashMap<>();

    public static void addUser(User user) {
        if (null != user) {
            userInfos.put(user.getUserId(), user);
        }
    }

    public static void removeUser(int userId) {
        userInfos.remove(userId);
    }


    public static Collection<User> listUser() {
        return userInfos.values();
    }

}
