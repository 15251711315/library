package com.book.PO;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user")
@Getter
@Setter
public class UserPO implements Serializable{
    private static final long serialVersionUID = 3806992168627446535L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="nick_Name")
    private  String nickName;

    @Column(name="openid")
    private String openid;

    @Column(name="avatar_Url")
    private String avatarUrl;

    @Override
    public String toString() {
        return "UserPO{" + "id=" + id + ", name='" + name + '\'' + ", nickName='" + nickName + '\'' + ", openid='"
            + openid + '\'' + ", avatarUrl='" + avatarUrl + '\'' + '}';
    }
}
