package com.book.DTO;

import com.book.PO.BooksPO;
import com.book.PO.UserPO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

public class RelationDTO implements Serializable{

    private static final long serialVersionUID = 5114109097963621178L;
    private Long id;
    private Long userId;
    private Long booksId;
    private String createTime;
    private String returnTime;
    private Integer flag;
    private BooksPO booksPO;
    private UserPO userPO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBooksId() {
        return booksId;
    }

    public void setBooksId(Long booksId) {
        this.booksId = booksId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public BooksPO getBooksPO() {
        return booksPO;
    }

    public void setBooksPO(BooksPO booksPO) {
        this.booksPO = booksPO;
    }

    public UserPO getUserPO() {
        return userPO;
    }

    public void setUserPO(UserPO userPO) {
        this.userPO = userPO;
    }

    //    private BooksDTO booksDTO;
//    private UserDTO userDTO;
}
