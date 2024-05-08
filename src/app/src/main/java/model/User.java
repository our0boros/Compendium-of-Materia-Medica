package model;

import androidx.annotation.NonNull;

import java.io.Serializable;

/**
 * @author: Haochen Gong
 * @description: User类
 **/
public class User implements Comparable<User>, Serializable {
    private int user_id;
    private String username;
    private String email;
    private String password;
    private String avatar_url;

    public User(){}

    public User(int id, String name, String email, String password, String avatar){
        this.user_id = id;
        this.username = name;
        this.email = email;
        this.password = password;
        this.avatar_url = avatar;
    }

    public int getUser_id(){
        return user_id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    @Override
    public int compareTo(User user) {
        return user_id - user.user_id;
    }


    @NonNull
    @Override
    public String toString() {
        return "{UsrID: " + user_id + ", "
                + "Name: " + username + ", "
                + "Email: " + email + ", "
                + "Password: " + password + ", "
                + "AvatarUrl: " + avatar_url + "}";
    }
}