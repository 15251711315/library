package com.book.DAO;

import com.book.PO.BooksPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface BooksDAO extends JpaRepository<BooksPO, Long>, JpaSpecificationExecutor<BooksPO> {
    List<BooksPO> findByNameLike(String name);

    List<BooksPO> findByIdIn(List<Long> selectedBooksIdList);

    List<BooksPO> findById(Long id);
}
