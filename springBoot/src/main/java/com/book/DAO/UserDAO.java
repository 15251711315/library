package com.book.DAO;

import com.book.PO.UserPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserDAO extends JpaSpecificationExecutor<UserPO>,JpaRepository<UserPO,Long>{
}
