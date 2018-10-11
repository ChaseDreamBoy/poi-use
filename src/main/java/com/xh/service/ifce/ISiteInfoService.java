package com.xh.service.ifce;

import java.io.OutputStream;

import org.springframework.web.multipart.MultipartFile;

import com.xh.util.ResultObjStr;

public interface ISiteInfoService {
	
	ResultObjStr importExcel(MultipartFile file, Integer myId);
	
	ResultObjStr exportExcel(Integer myId, String fileName, String excelFormat, OutputStream fileOut);

}
