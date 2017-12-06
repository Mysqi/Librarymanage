package com.mysql.qi_fu.librarymanage.sql;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by qi_fu on 2017/4/13.
 */
@Entity
public class User {
    @Generated
    String id;
    String userName;//名字
    String userNO;//学号
    String grid;//班级
    String phone;//联系方式
    String password;//密码

    @Generated(hash = 179773968)
    public User(String id, String userName, String userNO, String grid,
            String phone, String password) {
        this.id = id;
        this.userName = userName;
        this.userNO = userNO;
        this.grid = grid;
        this.phone = phone;
        this.password = password;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNO() {
        return userNO;
    }

    public void setUserNO(String userNO) {
        this.userNO = userNO;
    }

    public String getGrid() {
        return grid;
    }

    public void setGrid(String grid) {
        this.grid = grid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
