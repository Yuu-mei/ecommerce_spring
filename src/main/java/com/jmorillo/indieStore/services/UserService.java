package com.jmorillo.indieStore.services;

import java.util.List;

import com.jmorillo.indieStore.model.User;
import com.jmorillo.indieStore.model.dataTypes.UserInfo;

public interface UserService {
	List<User> obtainAllUsers();
	User obtainUserById(int user_id);
	User obtainUserByEmailPass(String email, String pwd);
	UserInfo obtainUserInfoByUsername(String username);
	void createUser(User u);
	void editUser(User u);
	void deleteUser(int user_id);
}
