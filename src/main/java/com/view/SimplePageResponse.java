package com.view;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SimplePageResponse<T> {
    @JsonProperty("content")
    private List<T> content;
    @JsonProperty("count")
    private long count;

    public List<T> getContent() {
        return this.content;
    }

    public SimplePageResponse() {
    }

    public SimplePageResponse(List<T> content, long count) {
        this.content = content;
        this.count = count;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public long getCount() {
        return this.count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}