package com.view;

import com.service.search.SearchCriteriaList;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserSearchView  extends SearchCriteriaList implements Serializable{
    private static final long serialVersionUID = 1L;
    private String userName;
    private Boolean active;
    private Integer wrongPass;
   // private Date lockDate;
    private Boolean lockStatus;
   // private Date createDate;
 //   private Date changeDate;
    private String role;
}
