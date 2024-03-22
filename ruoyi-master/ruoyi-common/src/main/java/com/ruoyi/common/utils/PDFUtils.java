package com.ruoyi.common.utils;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.PdfMerger;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.ruoyi.common.bean.PdfPrintData;
import com.ruoyi.common.config.FileDirConfig;
import com.ruoyi.common.config.PDFConfig;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PDFUtils {

    /**
     * 单个pdf打印
     */
    public static String pdfout(PdfPrintData pdfPrintData) throws Exception {
        File file = new File(FileDirConfig.FILE_TEMP);
        if (!file.exists()) {
            file.mkdirs();
        }
        // 生成的新文件路径
        String newPDFPath = FileDirConfig.FILE_TEMP + IdUtils.fastUUID();
        ClassPathResource cpr = new ClassPathResource(pdfPrintData.getTemplatePath());
        PdfReader reader = new PdfReader(cpr.getInputStream());
        PdfWriter writer = new PdfWriter(new FileOutputStream(newPDFPath));
        PdfDocument pdfDoc = new PdfDocument(reader, writer);
        Document document = new Document(pdfDoc);
        pdfDoc.setDefaultPageSize(PageSize.A4);

        PDFConfig pdfConfig = SpringUtils.getBean(PDFConfig.class);
        //  /usr/share/fonts/simsun.ttc,1
        PdfFont sysFont = PdfFontFactory.createFont(pdfConfig.getPdfFontLibrary(),PdfEncodings.IDENTITY_H, false);
        try {
            document.setMargins(20,5,20,5);
            document.setFontSize(8);
            document.setHeight(pdfDoc.getDefaultPageSize().getHeight());

            PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
            form.setGenerateAppearance(true);
            //文字类的内容处理
            Map<String, PdfFormField> fields = form.getFormFields();
            Map<String, String> datemap = pdfPrintData.getField();
            if (datemap != null) {
                for (String key : datemap.keySet()) {
                    String value = datemap.get(key);
                    PdfFormField pdfFormField = fields.get(key);
                    if (pdfFormField !=null && value != null){
                        pdfFormField.setFont(sysFont);
                        pdfFormField.setValue(value);
                    }

                }
            }
            form.flattenFields();//关闭表单编辑
            //图片类的内容处理
            Map<String, String> imgmap = pdfPrintData.getImage();
            if (imgmap != null) {
                for (String key : imgmap.keySet()) {
                    String value = imgmap.get(key);
                    String imgpath = value;
                    PdfFormField pdfFormField = form.getField(key);
                    if (pdfFormField == null){
                        continue;
                    }
                    Rectangle rectangle = form.getField(key).getWidgets().get(0).getRectangle().toRectangle();
//                //根据路径读取图片
                    Image image = new Image(ImageDataFactory.create(IOUtils.toByteArray(new FileInputStream(imgpath))));
                    ;
//                //图片大小自适应
                    image.scaleToFit(rectangle.getWidth(), rectangle.getHeight());
                    image.setFixedPosition(rectangle.getX(), rectangle.getY());
//                //添加图片
                    document.add(image);
                }
            }

            //表格处理
            if (StringUtils.isNotEmpty(pdfPrintData.getTable())) {
                Rectangle rectangle = form.getField(pdfPrintData.getTable()).getWidgets().get(0).getRectangle().toRectangle();
                Table table = new Table(UnitValue.createPercentArray(pdfPrintData.getColum()));
                float pageheight = document.getPdfDocument().getPage(1).getPageSize().getHeight();
                table.setMarginTop(pageheight - rectangle.getY() -rectangle.getHeight());
                table.setMarginLeft(rectangle.getX());
                table.setMarginRight(rectangle.getX());
                //设置标题
                for (String title : pdfPrintData.getTitle()) {
                    table.addCell(new Cell().add(new Paragraph(title).setFont(sysFont)).setBackgroundColor(Color.BLUE).setFontColor(Color.WHITE)
                            .setTextAlignment(TextAlignment.CENTER));
                }
                for (String[] datas : pdfPrintData.getData()) {
                    for (String data : datas) {
                        if (data == null){
                            data = "" ;
                        }
                        table.addCell(new Cell().add(new Paragraph(data).setFont(sysFont))
                                .setTextAlignment(TextAlignment.LEFT));
                    }
                }
                table.addFooterCell(new Cell(1,pdfPrintData.getTitle().length)
                        .add(new Paragraph("- - - 此页结束 - - -").setFont(sysFont))
                        .setTextAlignment(TextAlignment.CENTER));
                document.add(table);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
        return newPDFPath;
    }

    /**
     * 单个pdf打印
     */
    public static String pdfout(PdfPrintData pdfPrintData, Color backgroundColor, Color fontColor) throws Exception {
        File file = new File(FileDirConfig.FILE_TEMP);
        if (!file.exists()) {
            file.mkdirs();
        }
        // 生成的新文件路径
        String newPDFPath = FileDirConfig.FILE_TEMP + IdUtils.fastUUID();
        ClassPathResource cpr = new ClassPathResource(pdfPrintData.getTemplatePath());
        PdfReader reader = new PdfReader(cpr.getInputStream());
        PdfWriter writer = new PdfWriter(new FileOutputStream(newPDFPath));
        PdfDocument pdfDoc = new PdfDocument(reader, writer);
        Document document = new Document(pdfDoc);
        pdfDoc.setDefaultPageSize(PageSize.A4);

        PDFConfig pdfConfig = SpringUtils.getBean(PDFConfig.class);
        //  /usr/share/fonts/simsun.ttc,1
        PdfFont sysFont = PdfFontFactory.createFont(pdfConfig.getPdfFontLibrary(),PdfEncodings.IDENTITY_H, false);
        try {
            document.setMargins(20,5,20,5);
            document.setFontSize(8);

            document.setHeight(pdfDoc.getDefaultPageSize().getHeight());

            PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
            form.setGenerateAppearance(true);
            //文字类的内容处理
            Map<String, PdfFormField> fields = form.getFormFields();
            Map<String, String> datemap = pdfPrintData.getField();
            if (datemap != null) {
                for (String key : datemap.keySet()) {
                    String value = datemap.get(key);
                    PdfFormField pdfFormField = fields.get(key);
                    if (pdfFormField !=null && value != null){
                        pdfFormField.setFont(sysFont);
                        pdfFormField.setValue(value);
                    }

                }
            }
            form.flattenFields();//关闭表单编辑
            //图片类的内容处理
            Map<String, String> imgmap = pdfPrintData.getImage();
            if (imgmap != null) {
                for (String key : imgmap.keySet()) {
                    String value = imgmap.get(key);
                    String imgpath = value;
                    PdfFormField pdfFormField = form.getField(key);
                    if (pdfFormField == null){
                        continue;
                    }
                    Rectangle rectangle = form.getField(key).getWidgets().get(0).getRectangle().toRectangle();
//                //根据路径读取图片
                    Image image = new Image(ImageDataFactory.create(IOUtils.toByteArray(new FileInputStream(imgpath))));
                    ;
//                //图片大小自适应
                    image.scaleToFit(rectangle.getWidth(), rectangle.getHeight());
                    image.setFixedPosition(rectangle.getX(), rectangle.getY());
//                //添加图片
                    document.add(image);
                }
            }

            //表格处理
            if (StringUtils.isNotEmpty(pdfPrintData.getTable())) {
                Rectangle rectangle = form.getField(pdfPrintData.getTable()).getWidgets().get(0).getRectangle().toRectangle();
                Table table = new Table(UnitValue.createPercentArray(pdfPrintData.getColum()));
                float pageheight = document.getPdfDocument().getPage(1).getPageSize().getHeight();
                table.setMarginTop(pageheight - rectangle.getY() -rectangle.getHeight());
                table.setMarginLeft(rectangle.getX());
                table.setMarginRight(rectangle.getX());
                //设置标题
                for (String title : pdfPrintData.getTitle()) {
                    table.addCell(new Cell().add(new Paragraph(title).setFont(sysFont)).setBackgroundColor(backgroundColor).setFontColor(fontColor)
                            .setTextAlignment(TextAlignment.CENTER));
                }
                for (String[] datas : pdfPrintData.getData()) {
                    for (String data : datas) {
                        if (data == null){
                            data = "" ;
                        }
                        table.addCell(new Cell().add(new Paragraph(data).setFont(sysFont))
                                .setTextAlignment(TextAlignment.LEFT));
                    }
                }
                table.addFooterCell(new Cell(1,pdfPrintData.getTitle().length)
                        .add(new Paragraph("- - - 此页结束 - - -").setFont(sysFont))
                        .setTextAlignment(TextAlignment.CENTER));
                document.add(table);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
        return newPDFPath;
    }


    /**
     * 多个pdf合并
     */
    public static String pdfouts(List<PdfPrintData> pdfPrintDataList) throws Exception {
        File file = new File(FileDirConfig.FILE_TEMP);
        if (!file.exists()) {
            file.mkdirs();
        }
        List<String> files = new ArrayList<>();
        for (PdfPrintData pdfPrintData : pdfPrintDataList) {
            files.add(pdfout(pdfPrintData));
        }
        // 生成的新文件路径
        String newPDFPath = FileDirConfig.FILE_TEMP + IdUtils.fastUUID();
        PdfWriter writer = new PdfWriter(new FileOutputStream(newPDFPath));
        PdfDocument pdfDes = new PdfDocument(writer);
        PdfMerger merger = new PdfMerger(pdfDes);
        for (String filePath : files) {
            PdfReader reader = new PdfReader(filePath);
            PdfDocument pdfResource = new PdfDocument(reader);
            merger.setCloseSourceDocuments(true).merge(pdfResource, 1, pdfResource.getNumberOfPages());
            pdfResource.close();

        }
        pdfDes.close();
        return newPDFPath;
    }

}
