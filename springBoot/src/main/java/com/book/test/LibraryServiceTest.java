package com.book.test;

import com.book.DAO.BooksDAO;
import com.book.DAO.UserDAO;
import com.book.DTO.UserDTO;
import com.book.service.LibraryService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LibraryServiceTest {

    @Autowired
    private LibraryService libraryService;

    @Autowired
    private BooksDAO booksDAO;

    @Autowired
    private UserDAO userDAO;

    @Test
    public void testAddUser(){
        UserDTO userDTO = new UserDTO();
        userDTO.setName("23");
        Long id = libraryService.addUser(userDTO);
        Assert.assertNotNull(id);
        userDAO.delete(id);
    }
}
