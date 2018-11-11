package com.book.PO;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

//书本PO
@Table(name = "books")
@Entity
public class BooksPO implements Serializable {

    private static final long serialVersionUID = -6582059888944395521L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;//主键
    @Column(name = "name")
    private String name;//书名
    @Column(name = "num")
    private Integer num;//数量
    @Column(name = "author")
    private String author;//作者
    @Column(name = "images")
    private String images;//图片保存地址
    @Column(name = "des")
    private String des;//描述
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "code")
    private String code;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "BooksPO{" + "id=" + id + ", name='" + name + '\'' + ", num=" + num + ", author='" + author + '\''
            + ", images='" + images + '\'' + ", des='" + des + '\'' + ", createTime=" + createTime + ", code='" + code
            + '\'' + '}';
    }
}
