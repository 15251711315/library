package com.book.test;

import com.book.DTO.Excel;
import com.book.DTO.UserDTO;
import com.book.utils.DealExcel;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

public class ExcelTest {
    public static void main(String[] args) throws Exception{
        DealExcel d =  new DealExcel();
        InputStream io = new FileInputStream("C:\\Users\\战神包\\Desktop\\1.xlsx");
        List<Object> list =  d.importDataFromExcel(new Excel(),io,"1.xlsx");
        for(Object o:list){
            System.out.println(o);
        }
    }

}
