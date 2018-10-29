package com.book.service.impl;

import com.book.DAO.BooksDAO;
import com.book.DAO.UserDAO;
import com.book.DTO.BooksDTO;
import com.book.DTO.QueryBooksReq;
import com.book.DTO.UserDTO;
import com.book.PO.BooksPO;
import com.book.PO.UserPO;
import com.book.service.LibraryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Service
@Slf4j
public class LibraryServiceImpl implements LibraryService {

    @Autowired
    private BooksDAO booksDAO;

    @Autowired
    private UserDAO userDAO;

    @Override
    public List<BooksDTO> queryBooks(QueryBooksReq req) {
        List<BooksPO> booksPOList = new ArrayList<>();
        List<BooksDTO> booksDTOList = null;
        try {
            Specification<BooksPO> specification = new Specification<BooksPO>() {
                @Override
                public Predicate toPredicate(Root<BooksPO> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                    Predicate predicate = cb.conjunction();
                    if (req.getId() != null) {
                        predicate.getExpressions().add(cb.equal(root.get("id"), req.getId()));
                    }
                    if (StringUtils.isNotBlank(req.getAuthor())) {
                        predicate.getExpressions().add(cb.equal(root.get("author"), req.getAuthor()));
                    }
                    if (StringUtils.isNotBlank(req.getDescLike())) {
                        predicate.getExpressions().add(cb.like(root.get("desc"), "%" + req.getDescLike() + "%"));
                    }
                    if (StringUtils.isNotBlank(req.getNameLike())) {
                        predicate.getExpressions().add(cb.like(root.get("name"), "%" + req.getNameLike() + "%"));
                    }
                    if (StringUtils.isNotBlank(req.getName())) {
                        predicate.getExpressions().add(cb.equal(root.get("name"), req.getName()));
                    }
                    return predicate;
                }
            };
            Sort.Order sort = new Sort.Order(Sort.Direction.DESC, "createTime");
            booksPOList = booksDAO.findAll(specification);
            booksDTOList = booksPOList.stream().map(booksPO -> {
                BooksDTO booksDTO = new BooksDTO();
                BeanUtils.copyProperties(booksPO, booksDTO);
                return booksDTO;
            }).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            log.debug("queryBooks excute failed:{},req:{}", e, req);
        }
        return booksDTOList;
    }

    @Override
    public Long addUser(UserDTO userDTO) {
        UserPO result = new UserPO();
        try {
            UserPO userPO = new UserPO();
            BeanUtils.copyProperties(userDTO, userPO);
            result = userDAO.save(userPO);
            if (result == null) {
                return null;
            }

        } catch (BeansException e) {
            e.printStackTrace();
            log.debug("addUser excute failed:{},req:{}", e, userDTO);
        }
        return result.getId();
    }

    @Override
    public List<BooksDTO> querySelectedBooks(List<Long> selectedBooksIdList) {
        List<BooksPO> booksPOList = new ArrayList<>();
        List<BooksDTO> booksDTOList = null;
        booksPOList = booksDAO.findByIdIn(selectedBooksIdList);
        booksDTOList = booksPOList.stream().map(booksPO -> {
            BooksDTO booksDTO = new BooksDTO();
            BeanUtils.copyProperties(booksPO, booksDTO);
            return booksDTO;
        }).collect(Collectors.toList());
        return booksDTOList;
    }

}
