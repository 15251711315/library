package com.book.DTO;

public class Excel
{
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Excel{" +
                "name='" + name + '\'' +
                '}';
    }
}
