package com.education.common.model;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

/**
 * excel实体类
 * @author zengjintao
 * @version 1.0
 * @create 2018/8/25 17:16
 **/
public class ExcelModel {

    private HSSFSheet hssfSheet;
    private HSSFFont hssfFont;
    private HSSFCellStyle hssfCellStyle;
    private String excelHeadTitle;

    public ExcelModel(){

    }

    public void setExcelHeadTitle(String excelHeadTitle) {
        this.excelHeadTitle = excelHeadTitle;
    }

    public String getExcelHeadTitle() {
        return excelHeadTitle;
    }

    public ExcelModel(HSSFSheet hssfSheet, HSSFFont hssfFont, HSSFCellStyle hssfCellStyle){
        this.hssfSheet = hssfSheet;
        this.hssfFont = hssfFont;
        this.hssfCellStyle = hssfCellStyle;
    }

    public HSSFSheet getHSSFSheet() {
        return hssfSheet;
    }

    public void setHSSFSheet(HSSFSheet hssfSheet) {
        this.hssfSheet = hssfSheet;
    }

    public HSSFCellStyle getHSSFCellStyle() {
        return hssfCellStyle;
    }

    public void setHSSFCellStyle(HSSFCellStyle style) {
        this.hssfCellStyle = style;
        hssfCellStyle.setAlignment(HorizontalAlignment.CENTER); //字体居中
        hssfCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        hssfCellStyle.setBottomBorderColor(HSSFColor.BLUE.index); //下边框颜色
        hssfCellStyle.setBorderBottom(BorderStyle.THIN);//下边框
        hssfCellStyle.setBorderLeft(BorderStyle.THIN);//左边框
        hssfCellStyle.setBorderRight(BorderStyle.THIN);//右边框
        hssfCellStyle.setBorderTop(BorderStyle.THIN);//上边框
        hssfCellStyle.setFont(geHSSFFont());//设置字体样式
    }

    /**
     * 设置单元格宽度
     * @param column
     * @param width
     * @return
     */
    public void setColumnWidth(int column, int width[]){
        if (hssfSheet  == null)
            throw new NullPointerException("sheet can not be null");
        for (int i = 0; i< column ; i++) {
            hssfSheet.setColumnWidth(i, width[i]);
        }
    }


    public void setHSSFFont(HSSFFont font) {
        this.hssfFont = font;
        hssfFont.setBoldweight((short) 100);
        hssfFont.setFontHeight((short) 300);
        hssfFont.setFontName("宋体");
        hssfFont.setColor(HSSFColor.BLACK.index); //字体颜色
        hssfFont.setFontHeightInPoints((short) 16); //设置字体大小
        hssfFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
    }

    public HSSFFont geHSSFFont() {
        return hssfFont;
    }

    public void createHead(String[] title) {
        HSSFRow titleRow = hssfSheet.createRow(0); //创建标题 第一行
        HSSFRow row = hssfSheet.createRow(1); //创建表头 第二行
        for (int i = 0; i < title.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(hssfCellStyle);
            HSSFCell titleCell = titleRow.createCell(i);
            titleCell.setCellValue(excelHeadTitle);
            titleCell.setCellStyle(hssfCellStyle);
        }
    }
}
