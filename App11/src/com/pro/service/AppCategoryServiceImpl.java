package com.pro.service;

import java.util.List;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pro.dao.AppCategoryMapper;
import com.pro.entity.AppCategory;
@Service
public class AppCategoryServiceImpl implements AppCategoryService {

	@Resource
	private AppCategoryMapper mapper;
	
	@Override
	public List<AppCategory> getAppCategoryListByParentId(Integer parentId)
			throws Exception {
		// TODO Auto-generated method stub
		return mapper.getAppCategoryListByParentId(parentId);
	}

}
