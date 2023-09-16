package com.skychat.server.model;

import com.skychat.server.ServerApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;

@Entity
@Table(name = "common_user")
public class User {

    private static final Logger log = LoggerFactory.getLogger(ServerApplication.class);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer Id;
    @Column
    Integer type;
    @Column
    Integer mpr_user_id;
    @Column
    Integer wechat_user_id;
    @Column
    String nickname;
    @Column
    String realname;
    @Column
    String phone;
    @Column
    String mail;
    @Column
    String username;
    @Column
    String password;
    @Column
    Integer seed;
    @Column
    String remark;
    @Column
    String last_login_session;
    @Column
    Integer last_login_time;
    @Column
    Integer create_time;
    @Column
    Integer update_time;
    @Column
    Integer status;

    public User () {
        log.info("user model");
    }

    @Override
    public String toString() {
        return "User{" +
                "Id=" + Id +
                ", type=" + type +
                ", mpr_user_id=" + mpr_user_id +
                ", wechat_user_id=" + wechat_user_id +
                ", nickname='" + nickname + '\'' +
                ", realname='" + realname + '\'' +
                ", phone='" + phone + '\'' +
                ", mail='" + mail + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", seed=" + seed +
                ", remark='" + remark + '\'' +
                ", last_login_session='" + last_login_session + '\'' +
                ", last_login_time=" + last_login_time +
                ", create_time=" + create_time +
                ", update_time=" + update_time +
                ", status=" + status +
                '}';
    }
}
