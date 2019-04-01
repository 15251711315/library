package com.book.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TemplateData {
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public TemplateData(String value) {
        this.value = value;
    }

    public TemplateData() {
    }

}
