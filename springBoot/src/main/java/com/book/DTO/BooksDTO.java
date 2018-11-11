package com.book.DTO;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class BooksDTO implements Serializable {

    private static final long serialVersionUID = 7597984248210317674L;

    private Long id;//主键
    private String name;//书名
    private Integer num;//数量
    private String author;//作者
    private String images;//图片保存地址
    private String des;//描述
    private Date createTime;
    private String code;

    @Override
    public String toString() {
        return "BooksDTO{" + "id=" + id + ", name='" + name + '\'' + ", num=" + num + ", author='" + author + '\''
            + ", images='" + images + '\'' + ", des='" + des + '\'' + ", createTime=" + createTime + ", code='" + code
            + '\'' + '}';
    }

}
