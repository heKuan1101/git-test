package com.pro.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.pro.entity.*;

public interface DataDictionaryMapper {
	
	public List<DataDictionary> getDataDictionaryList(@Param("typeCode")String typeCode)throws Exception;
}
