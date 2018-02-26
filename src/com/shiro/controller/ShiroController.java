package com.shiro.controller;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shiro.services.ShiroService;
@Controller
@RequestMapping("/shiro")
public class ShiroController {

	@Autowired
	private ShiroService shiroService;
	
	@RequestMapping("/testShiroAnnotation")
	public String testShiroAnnotation(HttpSession session){
		session.setAttribute("key", "value12345");
		shiroService.testMethod();
		return "redirect:/list.jsp";
	}
	
	@RequestMapping("/login")
	public String login(String username,String password){
		Subject subject = SecurityUtils.getSubject();
		if(!subject.isAuthenticated()){
			//把用户名密码封装成UsernamePasswordToken
			UsernamePasswordToken token=new UsernamePasswordToken(username,password);
			//remenber
			token.setRememberMe(true);
			
			try {
				subject.login(token);
			} catch (AuthenticationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("登录失败"+"  "+e.getMessage());
			}
			
		}
		return "redirect:/list.jsp";
	}
}
