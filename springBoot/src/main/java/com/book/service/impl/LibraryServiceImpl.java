package com.book.service.impl;

import com.alibaba.fastjson.JSON;
import com.book.DAO.BooksDAO;
import com.book.DAO.RelationDAO;
import com.book.DAO.UserDAO;
import com.book.DTO.*;
import com.book.PO.BooksPO;
import com.book.PO.RelationPO;
import com.book.PO.UserPO;
import com.book.service.LibraryService;
import com.book.utils.HttpGetUtil;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Service
@Slf4j
public class LibraryServiceImpl implements LibraryService {
    DateFormat df = new SimpleDateFormat("yyyyMMdd");
    @Autowired
    private BooksDAO booksDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private RelationDAO relationDAO;

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
              //      predicate.getExpressions().add(cb.notEqual(root.get("num"), 0));
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
    @Override
    public Long addBooks(BooksPO booksPO) {
        BooksPO id = booksDAO.save(booksPO);
        return id.getId();
    }

    @Override
    public String achieveOpenid (HttpServletRequest request,HttpServletResponse response) {
        try {
        response.setContentType("text/html");
            request.setCharacterEncoding("UTF-8");

        response.setCharacterEncoding("UTF-8");
        String code = request.getParameter("code");//获取code
        Map params = new HashMap();
        params.put("secret", "fe88670a5b342218dc6e1a69475574d6");
        params.put("appid", "wx97bb89632b1624c8");
        params.put("grant_type", "authorization_code");
        params.put("js_code", code);
        String result = HttpGetUtil.httpRequestToString(
            "https://api.weixin.qq.com/sns/jscode2session", params);

        OpenIdDTO openidDTO = JSON.parseObject(result, OpenIdDTO.class);
        String openid = openidDTO.getOpenid();
        return openid;
    } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
    }
    return null;
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
    public UserDTO queryUserInfo(String openid){
        UserPO userPO = userDAO.findByOpenid(openid);
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(userPO,userDTO);
        return userDTO;
    }

    @Override
    public Map<String,List<RelationDTO>> queryReadBooks(Long userId) {
        Map<String,List<RelationDTO>> result = new HashMap<>();
        List<RelationDTO> readingList = new ArrayList<>();
        List<RelationDTO> returnedList = new ArrayList<>();
        List<RelationPO> relationPOList = relationDAO.queryByUserId(userId);
        for(RelationPO relationPO :relationPOList){
            RelationDTO relationDTO = new RelationDTO();
            if(1==relationPO.getFlag()){
                BeanUtils.copyProperties(relationPO,relationDTO);
                relationDTO.setCreateTime(df.format(relationPO.getCreateTime()));
                relationDTO.setReturnTime(df.format(relationPO.getReturnTime()));
                returnedList.add(relationDTO);
            }
            if(0==relationPO.getFlag()){
                BeanUtils.copyProperties(relationPO,relationDTO);
                relationDTO.setCreateTime(df.format(relationPO.getCreateTime()));
                readingList.add(relationDTO);
            }
        }
        result.put("reading",readingList);
        result.put("returned",returnedList);
        return result;
    }
}
