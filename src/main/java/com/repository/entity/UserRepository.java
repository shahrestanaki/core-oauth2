package com.repository.entity;

import com.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository(value = "UserRepository")
public interface UserRepository extends JpaRepository<User, Integer>, UserRepositoryCustom {

    @Transactional
    @Query(value = "select u FROM User u where u.username = ?1")
    User getUserByUsername(String username);

    @Transactional
    @Query(value = "select u FROM User u where u.id = ?1")
    User loadUserById(long userId);
    //Optional<User> findByName(String name);

    /*@Transactional
    @Modifying
    @Query(value = "UPDATE User u set status =0 , lockdate = SYSDATE() where u.id = :userId",
            nativeQuery = true)
    void lockUser(@Param("userId") Long userId);*/
}
