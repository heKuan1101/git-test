package com.pro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.pro.entity.BackendUser;
import com.pro.service.BackendUserService;
@Controller
@RequestMapping("manager")
@SessionAttributes("")
public class ManagerController {
	@Autowired
	@Qualifier("backendUserServiceImpl")
	private BackendUserService backendUserService;
	
	//登录跳转
	@RequestMapping("login")
	public String login(){
		return "jsp/backendlogin";
	}
	//登录
	@RequestMapping("dologin")
	public ModelAndView doLogin(BackendUser backendUser,ModelAndView modelAndView){
		BackendUser userSession=backendUserService.adminLogin(backendUser);
		//开启userSession
		if (userSession!=null) {
			modelAndView.addObject("userSession", userSession);
			modelAndView.setViewName("/jsp/backend/main");
		}else{
			//使用error
			modelAndView.addObject("error", "用户名或密码错误！");
			modelAndView.setViewName("jsp/backendlogin");
		}
		return modelAndView;	
	}
	//退出
	@RequestMapping("logout")
	public String logout(SessionStatus sessionStatus){
		//移除session
		sessionStatus.setComplete();
		return "redirect:../index.jsp";
	}
}
