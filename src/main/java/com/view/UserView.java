package com.view;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Data
public class UserView implements Serializable {
    private static final long serialVersionUID = 1L;
    private String userName;
    private Boolean active;
    private Integer wrongPass;
    private Date lockDate;
    private Boolean lockStatus;
    private Date createDate;
    private Date changeDate;
    private String role;
}
