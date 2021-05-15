package com.infy.repository;
import org.springframework.data.repository.CrudRepository;

import com.infy.entity.UserEntity;
public interface UsersRepository extends CrudRepository<UserEntity,Integer> {
	UserEntity findByContactNumber(String contactNumber);
}
