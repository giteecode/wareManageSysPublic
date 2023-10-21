package com.education.common.utils;

import com.education.common.constants.SystemConstants;
import com.education.common.model.ExcelModel;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * excel文件导出工具类
 *
 * @author zengjintao
 * @version 1.0
 * @create 2018/8/23 14:39
 **/
@Component
public class ExcelKit {

    private static final Logger logger = LoggerFactory.getLogger(ExcelKit.class);

    public static ResultCode exportByAjax(String fileName, HttpServletResponse response, HSSFWorkbook work) {
        try {
            response.setCharacterEncoding("utf-8");
            OutputStream outputStream = new FileOutputStream(FileUtils.getUploadPath() + fileName);
            work.write(outputStream);
            outputStream.close();
            return new ResultCode(ResultCode.SUCCESS, fileName);
        } catch (IOException e) {
            logger.error("excel导出异常.......", e);
        }
        return new ResultCode(ResultCode.FAIL, "导出失败");
    }



    /**
     * excel导出
     * @param data
     * @param column
     */
    public static HSSFWorkbook export(List<Map> data, List<String> column, int width[], String[] heads, String title) {
        HSSFWorkbook work = new HSSFWorkbook();
        HSSFSheet sheet = work.createSheet("sheet1");
        HSSFFont font = work.createFont();
        HSSFCellStyle style = work.createCellStyle();
        ExcelModel excelModel = new ExcelModel();
        excelModel.setHSSFSheet(sheet);
        excelModel.setHSSFFont(font);
        excelModel.setColumnWidth(column.size(), width);
        excelModel.setExcelHeadTitle(title);
        excelModel.setHSSFCellStyle(style);
        excelModel.createHead(heads);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, column.size() - 1));//横向：合并第一行的第0列到第6列
        for (int i = 2; i <= data.size() + 1; i++) {
            HSSFRow next = sheet.createRow(i); //内容从第三行开始创建
            for (int j = 0; j < column.size(); j++) {
                HSSFCell cellOne = next.createCell(j);
                cellOne.setCellStyle(style);
                String columnName = column.get(j);
                Object value = data.get(i - 2).get(column.get(j));
                if ("study_time".equals(columnName)) {
                    value = DateUtils.getIntervalTime((Integer)value * 1000);
                }
                // 导出图片
                if (Arrays.asList(SystemConstants.IMAGE_ALIAS).contains(columnName) && ObjectUtils.isNotEmpty(value)) {
                    String imageUrl = RequestUtils.getUploadDomain() + (String)value;
                    InputStream inputStream = RequestUtils.getInputStreamFromUrl(imageUrl);
                    HSSFPatriarch hssfPatriarch = sheet.createDrawingPatriarch();
                  //  HSSFClientAnchor anchor = new HSSFClientAnchor(100, 200, 100, 150, (short) 1, 1, (short) 2, 2);
                    HSSFClientAnchor anchor = new HSSFClientAnchor(200, 20, 800, 250, (short) 1, i, (short) 1, i);
                    hssfPatriarch.createPicture(anchor, work.addPicture(FileUtils.getByteFromInputStream(inputStream),
                            XSSFWorkbook.PICTURE_TYPE_JPEG));
                    next.setHeight((short) 1000);
                } else {
                    if (value instanceof Date) {
                         value = DateUtils.getSecondDate((Date) value);
                    }
                    cellOne.setCellValue(String.valueOf(value));
                }
            }
        }
        return work;
    }


    /**
     * 获取单元格内容数据转换
     * @param cell
     * @return
     */
    public static String getCellValue(HSSFCell cell) {
        String cellValue = null;
        if (cell != null) {
            switch (cell.getCellType()) {
                case XSSFCell.CELL_TYPE_STRING: //String类型数据
                    cellValue = cell.getStringCellValue();
                    break;
                case XSSFCell.CELL_TYPE_NUMERIC: //数值类型
                    cellValue = String.valueOf(cell.getNumericCellValue());
                    break;
                case XSSFCell.CELL_TYPE_BOOLEAN:
                    cellValue = String.valueOf(cell.getBooleanCellValue());
                    break;
                case XSSFCell.CELL_TYPE_BLANK:
                    cellValue = "";
                    break;
                default:
                    cellValue = "";
                    break;
            }
        }
        return cellValue;
    }
}
