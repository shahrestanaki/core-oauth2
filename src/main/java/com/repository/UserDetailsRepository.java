package com.repository;

import com.model.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDetailsRepository extends CrudRepository<UserInfo, String> {

    UserInfo findByUserName(String userName);

    UserInfo findByUserNameAndActive(String userName, boolean active);

    List<UserInfo> findAllByActive(boolean active);

    UserInfo findById(Integer id);

    void deleteById(Integer id);

    UserInfo findByUserNameAndOwnerKey(String username, String owner);
}
