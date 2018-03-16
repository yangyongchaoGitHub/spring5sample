package com.yyy.controler;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yyy.model.TestModel;
import com.yyy.model.domain.User;
import com.yyy.service.UserService;

/**
 * 
 * @author Administrator
 * 这里也可以注解为 @RestController 但是就不能返回指定的显示界面只能返回内容
 * 若用 @Controller 可以返回指定的配置界面  同时在function加上@ResponseBody就可以返回内容
 */
@Controller
public class GogoCtrl {

	private static final String defName = "no good , %s!";
	private final AtomicLong id = new AtomicLong();
	
	@Autowired
	UserService userService;
	
	@RequestMapping("/test")
	@ResponseBody
	public User test(@RequestParam(value="name", defaultValue="yyyyyyy") String name) {
		User user = new User();
		user.setUserName("first");
		userService.addUser(user);
		
		System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
		return user;
	}
	
	@RequestMapping("/getAll")
	public List<User> getAll() {
		return userService.getAllUser();
	}
	
	@RequestMapping("/page")
	public String jump() {
		return "result";
	}
}
