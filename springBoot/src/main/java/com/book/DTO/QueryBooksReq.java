package com.book.DTO;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class QueryBooksReq implements Serializable{
    private static final long serialVersionUID = -3516861680386566702L;
    Long id;
    String name;
    String author;
    String descLike;
    String nameLike;

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
