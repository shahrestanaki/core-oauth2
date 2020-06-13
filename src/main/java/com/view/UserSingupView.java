package com.view;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserSingupView implements Serializable {
    private static final long serialVersionUID = 1L;
    private Date createDate;
}
