package com.education.common.utils;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import cn.afterturn.easypoi.handler.inter.IExcelDataModel;
import com.education.common.model.ExcelResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

/**
 * excel 操作工具类
 * @author zengjintao
 * @version 1.0
 * @create_at 2021/3/14 15:32
 */
public class ExcelUtils {

    public static ExcelResult importExcel(InputStream inputStream,
                                      Class<?> pojoClass,
                                      ImportParams importParams,
                                      String errorExcelSavePath) throws Exception {
        ExcelImportResult importResult = ExcelImportUtil.importExcelMore(inputStream,
                pojoClass,
                importParams);

        ExcelResult excelResult = ExcelResult.build();
        // 存在数据校验失败
        if (importResult.isVerfiyFail() && ObjectUtils.isNotEmpty(importResult.getFailList())) {
            List<IExcelDataModel> failQuestionInfoList = importResult.getFailList();
            StringBuilder errorMsg = new StringBuilder("表格第");
            for (int i = 0; i < failQuestionInfoList.size(); i++ ) {
                int rowNumber = failQuestionInfoList.get(i).getRowNum() + 1;
                if (i == failQuestionInfoList.size() -1) {
                    errorMsg.append(rowNumber);
                } else {
                    errorMsg.append(rowNumber + ",");
                }
            }

            String targetPath = errorExcelSavePath; //
            String errorExcelUrl = targetPath
                    + ObjectUtils.generateUuId() + ".xlsx";
            String errorExcelPath = FileUtils.getUploadPath() + errorExcelUrl;
            File targetFile = new File(FileUtils.getUploadPath() + targetPath);
            if (!targetFile.exists()) {
                targetFile.mkdirs();
            }
            FileOutputStream fos = new FileOutputStream(errorExcelPath);
            importResult.getFailWorkbook().write(fos); // 生成错误提示excel
            excelResult.setErrorFilePath(errorExcelPath);
            excelResult.setErrorMsg(errorMsg + "行数据错误，" +
                    "请根据表格错误提示进行修改后再导入");
            excelResult.setErrorExcelUrl(errorExcelUrl);
        }

        excelResult.setExcelImportResult(importResult);
        return excelResult;
    }
}
