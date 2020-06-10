package com.pro.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.pro.entity.AppCategory;
import com.pro.entity.AppInfo;
import com.pro.entity.DataDictionary;
import com.pro.entity.DevUser;
import com.pro.service.AppInfoService;



import com.pro.entity.AppVersion;

import com.pro.service.*;
import tools.Constants;
import tools.PageSupport;

@Controller
@RequestMapping("ATK")
public class AppInfoController {
	@Resource
	private AppInfoService appInfoService;
	@Resource
	private AppVersionService appVersionService;
	@Resource 
	private DataDictionaryService dataDictionaryService;
	@Resource 
	private AppCategoryService appCategoryService;
	
	
	@RequestMapping(value="/list")
	public String getAppInfoList(Model model,HttpSession session,
			@RequestParam(value="querySoftwareName",required=false) String querySoftwareName,
			@RequestParam(value="queryStatus",required=false) Integer _queryStatus,
			@RequestParam(value="queryCategoryLevel1",required=false) Integer _queryCategoryLevel1,
			@RequestParam(value="queryCategoryLevel2",required=false) Integer _queryCategoryLevel2,
			@RequestParam(value="queryCategoryLevel3",required=false) Integer _queryCategoryLevel3,
			@RequestParam(value="queryFlatformId",required=false)Integer _queryFlatformId,
			@RequestParam(value="pageIndex",required=false) Integer pageIndex){	

		List<AppInfo> appInfoList = null;
		List<DataDictionary> flatFormList = null;
		//列出一级分类列表，注：二级和三级分类列表通过异步ajax获取
		List<AppCategory> categoryLevel1List = null;
		List<AppCategory> categoryLevel2List = null;
		List<AppCategory> categoryLevel3List = null;
		//页面容量
		int pageSize = 5;
		//当前页码
		Integer currentPageNo = 1;
		if(pageIndex != null){
			try{
				currentPageNo = Integer.valueOf(pageIndex);
			}catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		Integer queryStatus = null;
		if(_queryStatus != null && !_queryStatus.equals("")){
			queryStatus = _queryStatus;
		}
		Integer queryCategoryLevel1 = null;
		if(_queryCategoryLevel1 != null && !_queryCategoryLevel1.equals("")){
			queryCategoryLevel1 = _queryCategoryLevel1;
		}
		Integer queryCategoryLevel2 = null;
		if(_queryCategoryLevel2 != null && !_queryCategoryLevel2.equals("")){
			queryCategoryLevel2 = _queryCategoryLevel2;
		}
		Integer queryCategoryLevel3 = null;
		if(_queryCategoryLevel3 != null && !_queryCategoryLevel3.equals("")){
			queryCategoryLevel3 = _queryCategoryLevel3;
		}
		Integer queryFlatformId = null;
		if(_queryFlatformId != null && !_queryFlatformId.equals("")){
			queryFlatformId = _queryFlatformId;
		}
		//总数量
		int totalCount = 0;
		try {
			
			totalCount = appInfoService.getAppInfoCount(querySoftwareName, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3, queryFlatformId);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		//总页数
		PageSupport pages = new PageSupport();
		pages.setCurrentPageNo(currentPageNo);
		pages.setPageSize(pageSize);
		pages.setTotalCount(totalCount);
		int totalPageCount = pages.getTotalPageCount();
		//控制首页和尾页
		if(currentPageNo < 1){
			currentPageNo = 1;
		}else if(currentPageNo > totalPageCount){
			currentPageNo = totalPageCount;
		}
		try {
			appInfoList = appInfoService.getAppInfoList(querySoftwareName, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3, _queryFlatformId, currentPageNo, pageSize);
			//所属平台的列表
			flatFormList = this.getDataDictionaryList("APP_FLATFORM");
			categoryLevel1List = appCategoryService.getAppCategoryListByParentId(null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("appInfoList", appInfoList);
		model.addAttribute("flatFormList", flatFormList);
		model.addAttribute("categoryLevel1List", categoryLevel1List);
		model.addAttribute("pages", pages);
		model.addAttribute("querySoftwareName", querySoftwareName);
		model.addAttribute("queryCategoryLevel1", queryCategoryLevel1);
		model.addAttribute("queryCategoryLevel2", queryCategoryLevel2);
		model.addAttribute("queryCategoryLevel3", queryCategoryLevel3);
		model.addAttribute("queryFlatformId", queryFlatformId);
		//二级分类和三级分类列表---回显
		if(queryCategoryLevel2 != null && !queryCategoryLevel2.equals("")){
			categoryLevel2List = getCategoryList(queryCategoryLevel1.toString());
			model.addAttribute("categoryLevel2List", categoryLevel2List);
		}
		if(queryCategoryLevel3 != null && !queryCategoryLevel3.equals("")){
			categoryLevel3List = getCategoryList(queryCategoryLevel2.toString());
			model.addAttribute("categoryLevel3List", categoryLevel3List);
		}
		return "jsp/backend/applist";
	}
	
	//判断分级
	public List<AppCategory> getCategoryList(String pid) {
		List<AppCategory> categoryLevelList = null;
		try {
			categoryLevelList = appCategoryService.getAppCategoryListByParentId(pid==null?null:Integer.parseInt(pid));
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return categoryLevelList;
		
	}

	/**
	 * 根据parentId查询出相应的分类级别列表
	 * @param pid
	 * @return
	 */
	@RequestMapping(value="/categorylevellist.json",method=RequestMethod.GET,produces="application/json; charset=utf-8")
	@ResponseBody
	//将查询结构转换为json格式传递
	public String getAppCategoryList (@RequestParam String pid){
		if(pid.equals("")) pid = null;
		String json = JSONArray.toJSONString(getCategoryList(pid));
		return json;
	}
	//所属平台查询
	public List<DataDictionary> getDataDictionaryList(String typeCode){
		List<DataDictionary> dataDictionaryList = null;
		try {
			dataDictionaryList = dataDictionaryService.getDataDictionaryList(typeCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataDictionaryList;
}

	
	
	
	
//	
//	/**
//	 * 跳转到APP信息审核页面
//	 * @param appId
//	 * @param versionId
//	 * @param model
//	 * @return
//	 */
//	@RequestMapping(value="/check",method=RequestMethod.GET)
//	public String check(@RequestParam(value="aid",required=false) String appId,
//					   @RequestParam(value="vid",required=false) String versionId,
//					   Model model){
//		AppInfo appInfo = null;
//		AppVersion appVersion = null;
//		try {
//			appInfo = appInfoService.getAppInfo(Integer.parseInt(appId));
//			appVersion = appVersionService.getAppVersionById(Integer.parseInt(versionId));
//		}catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		model.addAttribute(appVersion);
//		model.addAttribute(appInfo);
//		return "backend/appcheck";
//	}
//	@RequestMapping(value="/checksave",method=RequestMethod.POST)
//	public String checkSave(AppInfo appInfo){
//		try {
//			if(appInfoService.updateSatus(appInfo.getStatus(),appInfo.getId())){
//				return "redirect:/manager/backend/app/list";
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return "backend/appcheck";
//	}
//	
//	
}
