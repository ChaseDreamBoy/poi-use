package com.xh.dao;

import java.util.List;
import java.util.Map;

public interface ISiteInfoDao {

	List<Map<String,Object>> getDataOfExportExcel();
	
	Integer addMultSiteInfo(List<Map<String,Object>> list);
	
}
