package com.book.service;

import com.book.DTO.BooksDTO;
import com.book.DTO.QueryBooksReq;
import com.book.DTO.UserDTO;

import java.util.List;

public interface LibraryService {
    //查询书
    List<BooksDTO> queryBooks(QueryBooksReq req);

    //添加用户
    Long addUser(UserDTO userDTO);

    //查询已选择的书籍
    List<BooksDTO> querySelectedBooks(List<Long> selectedBooksIdList);
}
