package com.xh.poi.excel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelUtil {

	private static Logger logger = LoggerFactory.getLogger(ExcelUtil.class);
	
	public static void main(String[] args) throws Exception {
		InputStream inp = new FileInputStream("G:\\Download\\Google Chrome\\xlsx.xls");
		List<List<Object>> excels = readExcel(inp);
		for (List<Object> rows : excels) {
			for (Object cell : rows) {
				System.out.println(cell.getClass().getName());
				System.out.println(cell);
			}
			System.out.println("");
		}
	}

	/**
	 * 解析excel
	 * @param inp excel InputStream.
	 * @return
	 */
	public static List<List<Object>> readExcel(InputStream inp) {
		Workbook wb = null;
		try {
			// 用这种方法创建Workbook可以同时兼容xls、xlsx两种格式
			wb = WorkbookFactory.create(inp);
			Sheet sheet = wb.getSheetAt(0);
			DataFormatter formatter = new DataFormatter();
			List<List<Object>> excels = new ArrayList<>();
			for (Row row : sheet) {
				List<Object> excelRows = new ArrayList<>();
				for (Cell cell : row) {
					// CellReference cellRef = new CellReference(row.getRowNum(),
					// cell.getColumnIndex());
					// System.out.print(cellRef.formatAsString());
					// System.out.print(" - ");
					String text = formatter.formatCellValue(cell);
					// System.out.println(text);
					excelRows.add(text);
					// switch (cell.getCellTypeEnum()) {
					// case STRING:
					//// System.out.println(cell.getRichStringCellValue().getString());
					// excelRows.add(cell.getRichStringCellValue().getString());
					// break;
					// case NUMERIC:
					// if (DateUtil.isCellDateFormatted(cell)) {
					//// System.out.println(cell.getDateCellValue());
					// excelRows.add(cell.getDateCellValue());
					// formatter.formatCellValue(cell);
					// } else {
					//// System.out.println(cell.getNumericCellValue());
					// excelRows.add(cell.getNumericCellValue());
					// }
					// break;
					// case BOOLEAN:
					//// System.out.println(cell.getBooleanCellValue());
					// excelRows.add(cell.getBooleanCellValue());
					// break;
					// case FORMULA:
					//// System.out.println(cell.getCellFormula());
					// excelRows.add(cell.getCellFormula());
					// break;
					// case BLANK:
					//// System.out.println();
					// excelRows.add("");
					// break;
					// default:
					//// System.out.println();
					// excelRows.add("");
					// break;
					// }
				}
				excels.add(excelRows);
			}
			return excels;
		} catch (Exception e) {
			logger.error("导入excel错误 : " + e.getMessage());
			return null;
		} finally {
			try {
				if (wb != null) {
					wb.close();
				}
				if (inp != null) {
					inp.close();
				}
			} catch (Exception e) {
				logger.error("导入excel关流错误 : " + e.getMessage());
			}
		}
		
		
	}

	// excelFormat >> xls 、 xlsx default:excelFormat
	public static void exportExcel(List<List<Object>> excels, String sheetname, String excelFormat,
			OutputStream fileOut) {
		Workbook wb = null;
		try {
			// 去除不允许的字符
			sheetname = WorkbookUtil.createSafeSheetName(sheetname);
			switch (excelFormat) {
			case "xls":
				wb = new HSSFWorkbook();
				break;
			case "xlsx":
				wb = new XSSFWorkbook();
				break;
			default:
				wb = new XSSFWorkbook();
				break;
			}

			// CreationHelper createHelper = wb.getCreationHelper();
			Sheet sheet = wb.createSheet();
			// Sheet sheet2 = wb.createSheet("second sheet");
			// 行是从0开始计算
			// Row row = sheet.createRow(0);
			// Cell cell = row.createCell(0);
			// cell.setCellValue(1);
			// row.createCell(1).setCellValue(1.2);
			// row.createCell(2).setCellValue(createHelper.createRichTextString("This is a
			// string"));
			// row.createCell(3).setCellValue(true);
			// // 时间格式 方法一
			// CellStyle cellStyle = wb.createCellStyle();
			// cellStyle.setDataFormat(
			// createHelper.createDataFormat().getFormat("m/d/yy h:mm"));
			// cell = row.createCell(1);
			// cell.setCellValue(new Date());
			// cell.setCellStyle(cellStyle);
			// //时间格式 方法二
			// cell = row.createCell(2);
			// cell.setCellValue(Calendar.getInstance());
			// cell.setCellStyle(cellStyle);
			// // 其他格式
			// row.createCell(0).setCellValue(1.1);
			// row.createCell(1).setCellValue(new Date());
			// row.createCell(2).setCellValue(Calendar.getInstance());
			// row.createCell(3).setCellValue("a string");
			// row.createCell(4).setCellValue(true);
			// row.createCell(5).setCellType(CellType.ERROR);

			// 使用字体
			// 创建一个新的字体并设定对应样式
			Font font = wb.createFont();
			font.setFontHeightInPoints((short) 16);
			font.setFontName("宋体");
//			font.setItalic(true);
//			font.setStrikeout(true);
			// 字体被设置为一种风格，因此创建一个新的使用
			CellStyle style = wb.createCellStyle();
			style.setFont(font);

			for (int i = 0; i < excels.size(); i++) {
				Row row = sheet.createRow(i);
				for (int j = 0; j < excels.get(i).size(); j++) {
					Cell cell = row.createCell(j);
					cell.setCellValue((String) excels.get(i).get(j));
					cell.setCellStyle(style);
				}
			}
			wb.write(fileOut);
		} catch (Exception e) {
			logger.error("导出excel util错误 : " + e.getMessage());
		} finally {
			try {
				if (wb != null) {
					wb.close();
				}
				if (fileOut != null) {
					fileOut.close();
				}
			} catch (IOException e) {
				logger.error("导出excel关流错误 : " + e.getMessage());
			}
		}

	}

}
