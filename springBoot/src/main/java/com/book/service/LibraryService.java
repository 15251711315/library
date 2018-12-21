package com.book.service;

import com.book.DTO.BooksDTO;
import com.book.DTO.QueryBooksReq;
import com.book.DTO.RelationDTO;
import com.book.DTO.UserDTO;
import com.book.PO.BooksPO;
import com.book.PO.RelationPO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface LibraryService {
    //查询书
    List<BooksDTO> queryBooks(QueryBooksReq req);

    //查询已选择的书籍
    List<BooksDTO> querySelectedBooks(List<Long> selectedBooksIdList);

    Long addBooks(BooksPO booksPO);

    //获取openid
    String achieveOpenid(HttpServletRequest request,HttpServletResponse response);

    //登录注册
    Long addUser(UserDTO userDTO);

    public UserDTO queryUserInfo(String openid);

    //查询已借书
    public Map<String,List<RelationDTO>> queryReadBooks(Long userId);
}
