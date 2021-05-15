package com.infy.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infy.entity.UserEntity;
import com.infy.model.Users;
import com.infy.repository.UsersRepository;
import com.infy.utility.HashingUtility;
import com.infy.validator.UserValidator;

@Service(value = "userService")
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UsersRepository usersRepository;

	@Override
	public Users authenticateUser(String contactNumber, String password) throws Exception {

		UserValidator.validateUserForLogin(contactNumber, password);
		UserEntity userEntity=usersRepository.findByContactNumber(contactNumber);
		Users user=null;
		if (userEntity!=null) {
			user = new Users();
			user.setUserId(userEntity.getUserId());
			user.setEmailId(userEntity.getEmailId());
			user.setContactNumber(userEntity.getContactNumber());
			user.setUserName(userEntity.getUserName());
			user.setPassword(userEntity.getPassword());
		}
		if (user == null)
			throw new Exception("UserService.INVALID_CREDENTIALS");
		String passwordFromDB = user.getPassword();

		if (passwordFromDB != null) {
			String hashedPassword = HashingUtility.getHashValue(password);

			if (hashedPassword.equals(passwordFromDB)) {
				user.setPassword(null);
				return user;
			} else
				throw new Exception("UserService.INVALID_CREDENTIALS");
		} else
			throw new Exception("UserService.INVALID_CREDENTIALS");

	}

}
