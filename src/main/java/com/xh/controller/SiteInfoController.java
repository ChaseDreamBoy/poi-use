package com.xh.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.xh.service.ifce.ISiteInfoService;
import com.xh.util.ResultObjStr;

/**
 * @author xiaohe
 */
@RestController
@RequestMapping(value = "/siteInfo")
public class SiteInfoController {

	private static Logger logger = LoggerFactory.getLogger(SiteInfoController.class);

	private ISiteInfoService siteInfoService;

	public SiteInfoController(ISiteInfoService siteInfoService) {
		this.siteInfoService = siteInfoService;
	}

	@PostMapping(value = "/importExcel")
	public String importExcel (@RequestParam("file") MultipartFile file,Integer myid) {
		try {
			return siteInfoService.importExcel(file, myid).toJson();
		} catch (Exception e) {
			logger.error("上传excel后台错误 : " + e.getMessage());
			return new ResultObjStr(ResultObjStr.ERROR, "后台错误", null).toJson();
		}
	}

	@GetMapping(value = "/exportExcel", produces = "application/force-download;charset=utf-8")
	public String exportExcel (Integer myid, String fileName, String excelFormat, HttpServletResponse response) {
		// 测试下载文件名为中文名
		// 设置下载框
		response.setContentType("application/force-download");
		try {
			fileName = fileName+"." + excelFormat;
			fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString());
			// response.addHeader("Content-Disposition","attachment;filename="+URLEncoder.encode(fileName+"." + excelFormat, "UTF-8"));// 设置下载文件名（*+fileName这个值可以定死，下载时会引用这个名字如：”aa.xml“）
			// 处理火狐和Safari浏览器 中文文件名乱码
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"; filename*=utf-8''" + fileName);
			//拿到用户选择的路径
			OutputStream out = response.getOutputStream();
			this.siteInfoService.exportExcel(myid, fileName, excelFormat, out);
		} catch (IOException e) {
			logger.error("下载excel后台错误 : " + e.getMessage());
		}
		return null;
	}

}
