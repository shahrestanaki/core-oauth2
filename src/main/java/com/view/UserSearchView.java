package com.view;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
public class UserSearchView implements Serializable {
    private static final long serialVersionUID = 1L;
    private String userName;
    private boolean active;
    private Integer wrongPass;
    private Date lockDate;
    private boolean lockStatus;
    private Date createDate;
    private Date changeDate;
    private String role;
}
