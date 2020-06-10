package com.pro.service;

import java.util.List;

import javax.annotation.Resource;

import com.pro.dao.AppInfoMapper;
import com.pro.dao.AppVersionMapper;
import com.pro.entity.AppVersion;

public class AppVersionServiceImpl implements AppVersionService{
	@Resource
	private AppVersionMapper mapper;
	@Resource
	private AppInfoMapper appInfoMapper;
	
	
	// 根据appId查询相应的app版本列表
	@Override
	public List<AppVersion> getAppVersionList(Integer appId) throws Exception {
		// TODO Auto-generated method stub
		return mapper.getAppversionList(appId);
	}

	@Override
	public boolean appsysadd(AppVersion appVersion) throws Exception {
		// TODO Auto-generated method stub
		boolean flag = false;
		Integer versionId = null;
		if(mapper.add(appVersion) > 0){
			versionId = appVersion.getId();
			flag = true;
		}
		if(appInfoMapper.updateVersionId(versionId, appVersion.getAppId()) > 0 && flag){
			flag = true;
		}
		return flag;
	}

	@Override
	public AppVersion getAppVersionById(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return mapper.getAppVersionById(id);
	}

	@Override
	public boolean modify(AppVersion appVersion) throws Exception {
		// TODO Auto-generated method stub
		boolean flag = false;
		if(mapper.modify(appVersion) > 0){
			flag = true;
		}
		return flag;
	}


	@Override
	public boolean deleteApkFile(Integer id) throws Exception {
		// TODO Auto-generated method stub
		boolean flag = false;
		if(mapper.deleteApkFile(id) > 0){
			flag = true;
		}
		return flag;
	}

}
