package com.book.controller;

import com.alibaba.fastjson.JSON;
import com.book.DAO.UserDAO;
import com.book.DTO.*;
import com.book.PO.BooksPO;
import com.book.service.LibraryService;
import com.book.utils.DealExcel;
import com.book.utils.HttpGetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

@Controller
@RequestMapping(value = "/books")
public class BooksController {

    @Autowired
    private LibraryService libraryService;

    //查询书
    @ResponseBody
    @RequestMapping(value = "/queryBooks")
    private String queryBooks(String nameLike) {
        QueryBooksReq queryBooksReq = new QueryBooksReq();
        List<BooksDTO> booksDTOList = new ArrayList<>();
        if (nameLike == null) {
            nameLike = "";
        }
        queryBooksReq.setNameLike(nameLike);
        booksDTOList = libraryService.queryBooks(queryBooksReq);
        return JSON.toJSONString(booksDTOList);
    }

    //查询已借图书
    @ResponseBody
    @RequestMapping(value = "/querySelectedBooks")
    public String querySelectedBooks(String selectedBooksIds) {
        List<BooksDTO> booksDTOList = new ArrayList<>();
        List<Long> selectedBooksIdList = new ArrayList<>();
        selectedBooksIdList = JSON.parseArray(selectedBooksIds, Long.class);
        booksDTOList = libraryService.querySelectedBooks(selectedBooksIdList);
        return JSON.toJSONString(booksDTOList);
    }

    @RequestMapping(value = "/addBooks1")
    public String addBooks1(BooksPO booksPO) {
//        booksPO.setImages("https://www.baohaiya.top/bookPics/no.jpg");
//        booksPO.setAuthor("");
//        booksPO.setCreateTime(new Date());
//        booksPO.setDes("");
//        long id = libraryService.addBooks(booksPO);
        DealExcel d =  new DealExcel();
        InputStream io = null;
        try {
            io = new FileInputStream("C:\\Users\\战神包\\Desktop\\1.xlsx");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        List<Object> list =  d.importDataFromExcel(new Excel(),io,"1.xlsx");
        for(Object o:list){
            System.out.println(o);
        }
        return "addBooks";
    }

    @RequestMapping(value = "/addBooks")
    public String addBooks(BooksPO booksPO) {
        return "addBooks";
    }

    @RequestMapping(value = "/achieveOpenid")
    @ResponseBody
    public String achieveOpenid(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String openid = libraryService.achieveOpenid(request,response);
        return openid;
    }

    @RequestMapping(value="/addUser")
    @ResponseBody
    public String  addUser(UserDTO userDTO){
       Long id = libraryService.addUser(userDTO);
       if(id!=null)
       return "SUCCESS";
       return null;
    }

    @RequestMapping(value="queryUserInfo")
    @ResponseBody
    public String queryUserInfo(String openid){
        UserDTO userDTO = libraryService.queryUserInfo(openid);
        return JSON.toJSONString(userDTO);
    }

//    @RequestMapping(value="")

}
