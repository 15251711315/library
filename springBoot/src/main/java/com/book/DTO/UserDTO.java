package com.book.DTO;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class UserDTO implements Serializable{
    private static final long serialVersionUID = 3578367579326548623L;

    private Long id;

    private String name;

//    private String flag;//权限

    private  String nickName;

    private String openid;

    private String avatarUrl;

//    private  String province;
//
//    private String city;

    @Override
    public String toString() {
        return "UserDTO{" + "id=" + id + ", name='" + name + '\'' + ", nickName='" + nickName + '\'' + ", openid='"
            + openid + '\'' + ", avatarUrl='" + avatarUrl + '\'' + '}';
    }
}
