package com.pro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.pro.dao.BackendUserMapper;
import com.pro.entity.BackendUser;
@Service("backendUserServiceImpl")
public class BackendUserServiceImpl implements BackendUserService {
	@Autowired
	@Qualifier("backendUserMapper")
	private BackendUserMapper backendUserMapper;
	
	@Override
	public BackendUser adminLogin(BackendUser backendUser) {
		// TODO Auto-generated method stub
		return backendUserMapper.adminLogin(backendUser);
	}

}
