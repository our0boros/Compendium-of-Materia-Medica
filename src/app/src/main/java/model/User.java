package model;

import androidx.annotation.NonNull;

/**
 * @author: Haochen Gong
 * @description: User类
 **/
public class User implements Comparable<User>{
    private final int id;
    private final String name;
    private final String email;
    private final String password;
    private final String avatar;

    public User(int id, String name, String email, String password, String avatar){
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
    }

    public int getId(){
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAvatar() {
        return avatar;
    }

    @Override
    public int compareTo(User user) {
        return id - user.id;
    }


    @NonNull
    @Override
    public String toString() {
        return "{UsrID: " + id + ", "
                + "Name: " + name + ", "
                + "Email: " + email + ", "
                + "Password: " + password + ", "
                + "AvatarUrl: " + avatar + "}";
    }
}