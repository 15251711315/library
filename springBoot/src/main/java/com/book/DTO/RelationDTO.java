package com.book.DTO;

import com.book.PO.BooksPO;
import com.book.PO.UserPO;
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
    private String createTime;
    private String returnTime;
    private Integer flag;
    private BooksPO booksPO;
    private UserPO userPO;
//    private BooksDTO booksDTO;
//    private UserDTO userDTO;
}
