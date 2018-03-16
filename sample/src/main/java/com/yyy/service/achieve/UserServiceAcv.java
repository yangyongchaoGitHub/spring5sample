package com.yyy.service.achieve;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyy.model.dao.UserMapper;
import com.yyy.model.domain.User;
import com.yyy.service.UserService;

@Service("userService")
public class UserServiceAcv implements UserService {
	
	@Autowired
	private UserMapper userMapper;

	@Override
	public void addUser(User user) {
		userMapper.insert(user);
		
	}

	@Override
	public User getById(long id) {
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<User> getAllUser() {
		// TODO Auto-generated method stub
		return userMapper.getAllUser();
	}

	
}
