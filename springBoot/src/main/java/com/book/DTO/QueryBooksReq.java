package com.book.DTO;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


public class QueryBooksReq implements Serializable{
    private static final long serialVersionUID = -3516861680386566702L;
    Long id;
    String name;
    String author;
    String descLike;
    String nameLike;

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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescLike() {
        return descLike;
    }

    public void setDescLike(String descLike) {
        this.descLike = descLike;
    }

    public String getNameLike() {
        return nameLike;
    }

    public void setNameLike(String nameLike) {
        this.nameLike = nameLike;
    }

    @Override
    public String toString() {
        return "QueryBooksReq{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", descLike='" + descLike + '\'' +
                ", nameLike='" + nameLike + '\'' +
                '}';
    }
}
