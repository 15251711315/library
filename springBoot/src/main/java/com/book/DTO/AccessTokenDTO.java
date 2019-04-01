package com.book.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccessTokenDTO {
    private  String access_token;
    private  String expires_in;

}
