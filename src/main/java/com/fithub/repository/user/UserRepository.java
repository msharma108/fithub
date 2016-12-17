package com.fithub.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fithub.domain.User;

/**
 * Repository for User Domain
 *
 */
@Repository

public interface UserRepository extends JpaRepository<User, Integer> {

	User findOneByUserName(String userName);

}
