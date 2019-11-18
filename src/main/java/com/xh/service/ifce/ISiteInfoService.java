package com.xh.service.ifce;

import java.io.OutputStream;

import org.springframework.web.multipart.MultipartFile;

import com.xh.util.ResultObjStr;

/**
 * @author xiaohe
 */
public interface ISiteInfoService {

    /**
     * 导入 Excel
     *
     * @param file 文件流
     * @param myId id
     *
     * @return 统一返回
     */
    ResultObjStr importExcel(MultipartFile file, Integer myId);

    /**
     * 导出 Excel
     *
     * @param myId        id
     * @param fileName    文件名称
     * @param excelFormat Excel 格式
     * @param fileOut     输出流
     *
     * @return 统一返回
     */
    ResultObjStr exportExcel(Integer myId, String fileName, String excelFormat, OutputStream fileOut);

}
