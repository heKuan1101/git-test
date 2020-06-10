package com.pro.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pro.entity.AppVersion;

public interface AppVersionMapper {
	public List<AppVersion> getAppversionList(@Param("appId")Integer appId) throws Exception;
	public int add(AppVersion appVersion) throws Exception;
	public int getVersionCountByAppId(@Param("appId")Integer appId) throws Exception;
	public AppVersion getAppVersionById(@Param("id")Integer id) throws Exception;
	public int deleteVersionByAppId(@Param("appId")Integer appId) throws Exception;
	public int modify(AppVersion appVersion) throws Exception;
	public int deleteApkFile(@Param("id")Integer id) throws Exception;
	
	
}
