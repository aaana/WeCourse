package com.weike.java.entity.wx;

import com.weike.java.entity.User;

import javax.jws.soap.SOAPBinding;

/**
 * Created by tina on 3/14/17.
 */
public class WxUser extends User {

    private int student_id;

    public WxUser() {
    }

    public WxUser(User user) {
        super(user.getName(), user.getEmail(), "", user.getType(), user.getSchool(), user.getIntroduction(), user.getAvatar());
        this.setId(user.getId());
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }
}
