package com.pro.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.pro.dao.AppInfoMapper;
import com.pro.entity.AppInfo;


@Service("AppInfoServiceImpl")
public class AppInfoServiceImpl implements AppInfoService {
	
	@Resource
	private AppInfoMapper mapper;
	
	
	@Autowired
	@Qualifier("appInfoMapper")
	private AppInfoMapper appInfoMapper;

	@Override
	public List<AppInfo> getAppInfoList(String querySoftwareName, Integer queryCategoryLevel1,
			Integer queryCategoryLevel2, Integer queryCategoryLevel3, Integer queryFlatformId, Integer currentPageNo,
			Integer pageSize) throws Exception {
		// TODO Auto-generated method stub
		return appInfoMapper.getAppInfoList(querySoftwareName, 1, queryCategoryLevel1, queryCategoryLevel2, 
				                 queryCategoryLevel3, queryFlatformId, null, (currentPageNo-1)*pageSize, pageSize);
	}

	@Override
	public int getAppInfoCount(String querySoftwareName, Integer queryCategoryLevel1, Integer queryCategoryLevel2,
			Integer queryCategoryLevel3, Integer queryFlatformId) throws Exception {
		// TODO Auto-generated method stub
		return appInfoMapper.getAppInfoCount(querySoftwareName, 1, queryCategoryLevel1, queryCategoryLevel2, 
									queryCategoryLevel3, queryFlatformId, null);
	}

	@Override
	public boolean updateSatus(Integer status, Integer id) throws Exception {
		boolean flag = false;
		if(mapper.updateSatus(status, id) > 0 ){
			flag = true;
		}
		return flag;
	}

	@Override
	public AppInfo getAppInfo(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return mapper.getAppInfo(id, null);
	}

//	@Override
//	public boolean add(AppInfo appInfo) throws Exception {
//		boolean flag = false;
//		if(mapper.add(appInfo) > 0){
//			flag = true;
//		}
//		return flag;
//	}
//
//	@Override
//	public boolean modify(AppInfo appInfo) throws Exception {
//		boolean flag = false;
//		if(mapper.modify(appInfo) > 0){
//			flag = true;
//		}
//		return flag;
//	}
//
//	@Override
//	public boolean deleteAppInfoById(Integer delId) throws Exception {
//		boolean flag = false;
//		if(mapper.deleteAppInfoById(delId) > 0){
//			flag = true;
//		}
//		return flag;
//	}


	

}
