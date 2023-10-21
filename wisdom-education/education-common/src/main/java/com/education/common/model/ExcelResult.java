package com.education.common.model;

import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;


/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2021/3/14 15:22
 */
public class ExcelResult {

 //   private boolean success = true; // 默认excel 数据校验成功
    private String errorMsg;
    private String errorExcelUrl;
    private String errorFilePath;

    private ExcelImportResult excelImportResult;

    public void setExcelImportResult(ExcelImportResult excelImportResult) {
        this.excelImportResult = excelImportResult;
    }

    public ExcelImportResult getExcelImportResult() {
        return excelImportResult;
    }

    public static ExcelResult build() {
        return new ExcelResult();
    }

    public String getErrorFilePath() {
        return errorFilePath;
    }

    public void setErrorFilePath(String errorFilePath) {
        this.errorFilePath = errorFilePath;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorExcelUrl() {
        return errorExcelUrl;
    }

    public void setErrorExcelUrl(String errorExcelUrl) {
        this.errorExcelUrl = errorExcelUrl;
    }
}
