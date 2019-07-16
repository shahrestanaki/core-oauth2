package com.repository.entity;


import com.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@SuppressWarnings("JpaQlInspection")
@Repository
public class UserRepositoryImpl implements UserRepositoryCustom {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PersistenceContext
    private EntityManager entityManager;

    public void updateEdit(User user) {
        String complexSql = "update  users   set  name=?,  famili=?,  password=?, status=?, " +
                " rules=?, changedate=?, edituser=?,  avatar=?  " +
                " where id=?";
        jdbcTemplate.update(complexSql, new Object[]{user.getName(), user.getFamili(), user.getPassword(), user.getStatus(), user.getRules(), user.getChangedate()
                , user.getEdituser(), user.getAvatar(), user.getId()});
    }
}
