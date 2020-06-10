package com.pro.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pro.entity.DataDictionary;

public interface DataDictionaryService {
	public List<DataDictionary> getDataDictionaryList(String typeCode)throws Exception;
}
