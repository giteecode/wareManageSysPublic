package com.ruoyi.common.bean;

import java.util.Map;

public class PdfPrintData {

    public String templatePath;//模板文件地址

    public Map<String, String> field; //表单

    public Map<String, String> image;//图片

    public String table;//table 的所有域

    public float[] colum;//table表格的宽度比

    public String[] title;//表格标题

    public String[][] data;//表格数据


    public Map<String, String> getField() {
        return field;
    }

    public void setField(Map<String, String> field) {
        this.field = field;
    }

    public Map<String, String> getImage() {
        return image;
    }

    public void setImage(Map<String, String> image) {
        this.image = image;
    }

    public String[] getTitle() {
        return title;
    }

    public void setTitle(String[] title) {
        this.title = title;
    }

    public String[][] getData() {
        return data;
    }

    public void setData(String[][] data) {
        this.data = data;
    }


    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public float[] getColum() {
        return colum;
    }

    public void setColum(float[] colum) {
        this.colum = colum;
    }
}
