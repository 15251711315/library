package com.book.controller;

import com.alibaba.fastjson.JSON;
import com.book.DTO.BooksDTO;
import com.book.DTO.Excel;
import com.book.DTO.QueryBooksReq;
import com.book.PO.BooksPO;
import com.book.service.LibraryService;
import com.book.utils.DealExcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
//        booksPO.setAuthor("佚名");
//        booksPO.setCreateTime(new Date());
//        booksPO.setDes("");
//        long id = libraryService.addBooks(booksPO);
        DealExcel d =  new DealExcel();
        InputStream io = new FileInputStream("C:\\Users\\战神包\\Desktop\\1.xlsx");
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
}
