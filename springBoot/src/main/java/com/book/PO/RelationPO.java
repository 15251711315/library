package com.book.PO;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Table(name="relation")
@Entity
@Getter
@Setter
public class RelationPO implements Serializable{
    private static final long serialVersionUID = -363886288824300161L;

    @Id
    @Column(name="id")
   private Long id;

    @Column(name="books_id")
   private Long booksId;

    @Column(name="user_id")
   private Long userId;

    @Column(name="create_time")
    private Date createTime;

    @Column(name="return_time")
    private Date returnTime;

    @Column(name="flag")
    private Integer flag;

    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH})
    @JoinColumn(name="books_id", referencedColumnName="id", insertable=false, updatable=false)
    private BooksPO booksPO;

    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH})
    @JoinColumn(name="user_id", referencedColumnName="id", insertable=false, updatable=false)
    private UserPO userPO;
}
