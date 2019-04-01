package com.book.DAO;

import com.book.PO.BooksPO;
import com.book.PO.RelationPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RelationDAO extends JpaRepository<RelationPO, Long>, JpaSpecificationExecutor<RelationPO> {

    List<RelationPO> findByUserId(@Param("userId") Long userId);

    List<RelationPO> findByFlag(@Param("flag") Integer flag);

    List<RelationPO> findById(@Param("id") Long id);
}
