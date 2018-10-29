package com.book.PO;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

//书本PO
@Table(name = "books")
@Entity
@Getter
@Setter
public class BooksPO implements Serializable{

    private static final long serialVersionUID = -6582059888944395521L;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name= "id")
    private Long id;//主键
    @Column(name= "name")
    private String name;//书名
    @Column(name= "num")
    private Integer num;//数量
    @Column(name= "author")
    private String author;//作者
    @Column(name= "images")
    private String images;//图片保存地址
    @Column(name="desc")
    private String desc;//描述
    @Column(name="create_time")
    private Date createTime;

    @Override
    public String toString() {
        return "BooksPO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", num=" + num +
                ", author='" + author + '\'' +
                ", images='" + images + '\'' +
                ", desc='" + desc + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
