package com.xh.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.xh.dao.ISiteInfoDao;
import com.xh.poi.excel.ExcelUtil;
import com.xh.service.ifce.ISiteInfoService;
import com.xh.util.ResultObjStr;

@Service
public class SiteInfoServiceImpl implements ISiteInfoService {

	private static Logger logger = LoggerFactory.getLogger(SiteInfoServiceImpl.class);

	@Autowired
	private ISiteInfoDao siteInfoDao;

	@Override
	public ResultObjStr importExcel(MultipartFile file, Integer myId) {
		List<Map<String, Object>> excelsMap = new ArrayList<>();
		InputStream in = null;
		try {
			in = file.getInputStream();
			List<List<Object>> excels = ExcelUtil.readExcel(in);
			for (int i = 0; i < excels.size(); i++) {
				Map<String, Object> map = new HashMap<>();
				for (int j = 0; j < excels.get(i).size(); j++) {
					switch (j) {
					case 0:
						map.put("title", excels.get(i).get(j));
						break;
					case 1:
						map.put("context", excels.get(i).get(j));
						break;
					case 2:
						map.put("create_time", excels.get(i).get(j));
						break;
					case 3:
						map.put("url", excels.get(i).get(j));
						break;
					default:
						break;
					}

				}
				excelsMap.add(map);
			}
			this.siteInfoDao.addMultSiteInfo(excelsMap);
			return new ResultObjStr(ResultObjStr.SUCCESS, "上传成功", null); 
		} catch (IOException e) {
			logger.error("导入 siteInfo excel 错误 : " + e.getMessage());
			return new ResultObjStr(ResultObjStr.ERROR,"上传错误",null);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				logger.error("导入 siteInfo excel 关流错误 : " + e.getMessage());
				return new ResultObjStr(ResultObjStr.ERROR,"上传错误",null);
			}
		}
	}

	@Override
	public ResultObjStr exportExcel(Integer myId, String fileName, String excelFormat, OutputStream fileOut) {
		List<Map<String, Object>> allSiteInfos = this.siteInfoDao.getDataOfExportExcel();
		List<List<Object>> excels = new ArrayList<>();
		for (Map<String, Object> map : allSiteInfos) {
			List<Object> row = new ArrayList<>();
			String title = map.get("title").toString();
			String context = map.get("context").toString();
			String createTime = map.get("create_time").toString();
			String url = map.get("url").toString();
			if (context.length() > 3200) {
				context = context.substring(0,3200);
			}
			row.add(title);
			row.add(context);
			row.add(createTime);
			row.add(url);
			excels.add(row);
		}
		ExcelUtil.exportExcel(excels, fileName, excelFormat, fileOut);
		return new ResultObjStr(ResultObjStr.SUCCESS, "下载成功", null);
	}

}
