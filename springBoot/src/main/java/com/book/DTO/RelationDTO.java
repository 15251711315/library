package com.book.DTO;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class RelationDTO implements Serializable{

    private static final long serialVersionUID = 5114109097963621178L;
    private Long id;
    private Long userId;
    private Long booksId;
    private Date createTime;
    private Date returnTime;
    private Integer flag;

    @Override
    public String toString() {
        return "RelationDTO{" + "id=" + id + ", userId=" + userId + ", booksId=" + booksId + ", createTime="
            + createTime + ", returnTime=" + returnTime + ", flag=" + flag + '}';
    }
}
