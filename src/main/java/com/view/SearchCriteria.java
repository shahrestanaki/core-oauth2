package com.view;

import lombok.Data;

@Data
public class SearchCriteria {
    // @EnglishOnly
    private String key;

    //@SearchCriteriaValidate
    private String operation;

    //   @OnlyLetter
    private Object value;


    public SearchCriteria() {

    }

    public SearchCriteria(String key, String operation, Object value) {
        this.key = key;
        this.operation = operation;
        this.value = value;
    }
}