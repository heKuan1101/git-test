package com.pro.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.pro.entity.*;

public interface AppCategoryMapper {
	///////////////
	public List<AppCategory> getAppCategoryListByParentId(@Param("parentId")Integer parentId)throws Exception;
}
