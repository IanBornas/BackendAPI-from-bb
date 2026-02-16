package com.gabriel.userms.service;
import com.gabriel.userms.model.User;
public interface UserService {
	User[] getAll() throws Exception;
	User get(Integer id) throws Exception;
	User create(User user) throws Exception;
	User update(User user) throws Exception;
	void delete(Integer id) throws Exception;
}
