package com.repository;

import com.model.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface UserDetailsRepository extends CrudRepository<UserInfo, String> {
    public UserInfo findByUserNameAndEnabled(String userName, short enabled);

    public List<UserInfo> findAllByEnabled(short enabled);

    public UserInfo findById(Integer id);
//
//	@Override
//	public UserInfo save(UserInfo userInfo);

    public void deleteById(Integer id);
}
