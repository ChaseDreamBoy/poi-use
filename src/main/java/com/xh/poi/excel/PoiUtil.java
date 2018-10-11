package com.xh.poi.excel;

import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class PoiUtil {

	public static void main(String[] args) {

	}
	
	
	
	/**
	 *   
	 * @Title: getExcelType 
	 *
	 * @author: xiaohe
	 *
	 * @Description: 根据输入流判断excel类型
	 *
	 * @param inp
	 *            excel的输入流
	 *
	 * @return: String
	 *              excel 的类型
	 *              出现错误返回错误信息
	 *
	 */
	public static String getExcelType (InputStream inp) {
		try {
			Workbook wb = WorkbookFactory.create(inp);
			if (wb instanceof HSSFWorkbook) {
				// 2003 xls
				return "xls";
			} else if (wb instanceof XSSFWorkbook) {
				// 2007 xlsx
				return "xlsx";
			} else {
				return "unknow type";
			}
		} catch (Exception e) {
			return e.getMessage();
		}
	}

}
