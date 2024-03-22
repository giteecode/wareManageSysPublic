package com.ruoyi.common.service;

import com.itextpdf.kernel.color.Color;
import com.ruoyi.common.bean.*;
import com.ruoyi.common.bean.typeEnum.InOrderReturnTypeEnum;
import com.ruoyi.common.bean.typeEnum.InOrderTypeEnum;
import com.ruoyi.common.bean.typeEnum.OutOrderReturnTypeEnum;
import com.ruoyi.common.bean.typeEnum.OutOrderTypeEnum;
import com.ruoyi.common.utils.Arith;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.PDFUtils;
import com.ruoyi.common.utils.QRUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ResourceUtils;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PDFService {

    /**
     * 打印物料标签
     */
    public String printLabel(MatLabelPdfData pdfData, int num) throws Exception {
        if(num < 1){
            num = 1;
        }
        Map<String,String> map = new HashMap<>();
        map.put("supplierName", pdfData.getSupplierName());
        map.put("matCode", pdfData.getMatCode());
        map.put("matName", pdfData.getMatName());
        map.put("figNum", pdfData.getFigNum());
        map.put("quantity", String.valueOf(Arith.round(pdfData.getQuantity().doubleValue(), 2)));
        map.put("unitName", pdfData.getUnitName());
        map.put("batch", pdfData.getBatch());
        map.put("orderNo", pdfData.getOrderNo());
        map.put("orderTypeLabel", pdfData.getOrderTypeLabel());
        map.put("prodTime", DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, pdfData.getProdTime()));
        List<PdfPrintData> printDataList = new ArrayList<>();
        for (int i = 0; i < num; i ++){
            Map<String, String> map2 = new HashMap();
            map2.put("qr_af_image", QRUtils.qrout("LABEL:" + pdfData.getLabelId()));
            PdfPrintData pdfPrintData = new PdfPrintData();
            pdfPrintData.setField(map);
            pdfPrintData.setImage(map2);
            pdfPrintData.setTemplatePath("pdf/label.pdf");
            printDataList.add(pdfPrintData);
        }
        return PDFUtils.pdfouts(printDataList);
    }

    /**
     * 打印物料标签（批量）
     */
    public String printLabelBatch(List<MatLabelPdfData> pdfDataList) throws Exception {
        Map<String,String> map = null;
        List<PdfPrintData> printDataList = new ArrayList<>();
        for(MatLabelPdfData pdfData : pdfDataList){
            map = new HashMap<>();
            map.put("supplierName", pdfData.getSupplierName());
            map.put("matCode", pdfData.getMatCode());
            map.put("matName", pdfData.getMatName());
            map.put("figNum", pdfData.getFigNum());
            map.put("quantity", String.valueOf(Arith.round(pdfData.getQuantity().doubleValue(), 2)));
            map.put("unitName", pdfData.getUnitName());
            map.put("batch", pdfData.getBatch());
            map.put("orderNo", pdfData.getOrderNo());
            map.put("orderTypeLabel", pdfData.getOrderTypeLabel());
            map.put("prodTime", DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, pdfData.getProdTime()));

            Map<String, String> map2 = new HashMap();
            map2.put("qr_af_image", QRUtils.qrout("LABEL:" + pdfData.getLabelId()));
            PdfPrintData pdfPrintData = new PdfPrintData();
            pdfPrintData.setField(map);
            pdfPrintData.setImage(map2);
            pdfPrintData.setTemplatePath("pdf/label.pdf");

            printDataList.add(pdfPrintData);
        }
        return PDFUtils.pdfouts(printDataList);
    }

    /**
     * 打印入库单
     */
    public String printInOrder(InOrderPdfData inOrder, List<InDetailPdfData> detailList) throws Exception {
        PdfPrintData pdfPrintData = new PdfPrintData();
        Map<String,String> map = new HashMap<>();
        map.put("orderNo", inOrder.getOrderNo());
        map.put("createTime", DateUtils.parseDateToStr("yyyy-MM-dd HH:mm", inOrder.getCreateTime()));
        map.put("createBy", inOrder.getCreateBy());
        pdfPrintData.setField(map);

        if(!CollectionUtils.isEmpty(detailList)){
            String[][] data = null;
            pdfPrintData.setTitle(new String[]{"行号","物料编码","物料名称","财务编码","图号","数量","单位","单价","批次","供应商"});
            pdfPrintData.setColum(new float[]{1,2,3,2,2,1,1,1,3,3});
            data = new String[detailList.size() + 1][10];
            int i = 1;
            for(InDetailPdfData detail : detailList){
                data[i][0] = String.valueOf(i);
                data[i][1] = detail.getMatCode();
                data[i][2] = detail.getMatName();
                data[i][3] = detail.getFdCode();
                data[i][4] = detail.getFigNum();
                data[i][5] = String.valueOf(Arith.round(detail.getQuantity().doubleValue(), 2));
                data[i][6] = detail.getUnitName();
                data[i][7] = String.valueOf(Arith.round(detail.getUnitPrice().doubleValue(), 3));
                data[i][8] = detail.getBatch();
                data[i][9] = detail.getSupplierName();
                i++;
            }
            pdfPrintData.setData(data);
            pdfPrintData.setTable("table_af_image");
        }

        Map<String, String> img = new HashMap();
        img.put("qr_af_image", QRUtils.qrout("ORDER:" + inOrder.getOrderNo()));
        pdfPrintData.setImage(img);
        if(InOrderTypeEnum.PURCHASE.getValue().equals(inOrder.getOrderType())){
            pdfPrintData.setTemplatePath("pdf/purchase_in_order.pdf");
        }
        return PDFUtils.pdfout(pdfPrintData, Color.LIGHT_GRAY, Color.BLACK);
    }

    /**
     * 打印入库退货单
     */
    public String printInOrderReturn(InReturnPdfData inReturn, List<InReturnDetailPdfData> detailList) throws Exception {
        PdfPrintData pdfPrintData = new PdfPrintData();
        Map<String,String> map = new HashMap<>();
        map.put("returnNo", inReturn.getReturnNo());
        map.put("warehouseName", inReturn.getWarehouseName());
        map.put("orderNo", inReturn.getOrderNo());
        map.put("returnReason", inReturn.getReturnReason());
        map.put("createTime", DateUtils.parseDateToStr("yyyy-MM-dd HH:mm", inReturn.getCreateTime()));
        map.put("createBy", inReturn.getCreateBy());
        pdfPrintData.setField(map);

        if(!CollectionUtils.isEmpty(detailList)){
            String[][] data = null;
            pdfPrintData.setTitle(new String[]{"行号","物料编码","物料名称","财务编码","图号","需退","已退","单位","批次","所在货位"});
            pdfPrintData.setColum(new float[]{1,2,3,2,2,1,1,1,3,2});
            data = new String[detailList.size() + 1][10];
            int i = 1;
            for(InReturnDetailPdfData detail : detailList){
                data[i][0] = String.valueOf(i);
                data[i][1] = detail.getMatCode();
                data[i][2] = detail.getMatName();
                data[i][3] = detail.getFdCode();
                data[i][4] = detail.getFigNum();
                data[i][5] = String.valueOf(Arith.round(detail.getQuantity().doubleValue(), 2));
                data[i][6] = String.valueOf(Arith.round(detail.getReturnQuantity().doubleValue(), 2));
                data[i][7] = detail.getUnitName();
                data[i][8] = detail.getBatch();
                data[i][9] = detail.getLocationCode();
                i++;
            }
            pdfPrintData.setData(data);
            pdfPrintData.setTable("table_af_image");
        }

        Map<String, String> img = new HashMap();
        img.put("qr_af_image", QRUtils.qrout("ORDER:" + inReturn.getReturnNo()));
        pdfPrintData.setImage(img);
        if(InOrderReturnTypeEnum.PURCHASE_RETURN.getValue().equals(inReturn.getReturnType())){
            pdfPrintData.setTemplatePath("pdf/purchase_in_return.pdf");
        }
        return PDFUtils.pdfout(pdfPrintData, Color.LIGHT_GRAY, Color.BLACK);
    }

    /**
     * 打印出库单
     */
    public String printOutOrder(OutOrderPdfData outOrder, List<OutDetailPdfData> detailList) throws Exception {
        PdfPrintData pdfPrintData = new PdfPrintData();
        Map<String,String> map = new HashMap<>();
        map.put("orderNo", outOrder.getOrderNo());
        map.put("orderReason", outOrder.getOrderReason());
        map.put("warehouseName", outOrder.getWarehouseName());
        map.put("warehouseKeeper", outOrder.getWarehouseKeeper());
        map.put("workshopName", outOrder.getWorkshopName());
        map.put("prodOrderNo", outOrder.getProdOrderNo());
        map.put("matCode", outOrder.getMatCode());
        map.put("matName", outOrder.getMatName());
        map.put("quantity", String.valueOf(Arith.round(outOrder.getQuantity().doubleValue(), 2)));
        map.put("createTime", DateUtils.parseDateToStr("yyyy-MM-dd HH:mm", outOrder.getCreateTime()));
        map.put("createBy", outOrder.getCreateBy());
        pdfPrintData.setField(map);

        if(!CollectionUtils.isEmpty(detailList)){
            String[][] data = null;
            pdfPrintData.setTitle(new String[]{"行号","物料编码","物料名称","图号","数量","已领","单位","推荐货位"});
            pdfPrintData.setColum(new float[]{1,2,3,2,1,1,1,1});
            data = new String[detailList.size() + 1][8];
            int i = 1;
            for(OutDetailPdfData detail : detailList){
                data[i][0] = String.valueOf(i);
                data[i][1] = detail.getMatCode();
                data[i][2] = detail.getMatName();
                data[i][3] = detail.getFigNum();
                data[i][4] = String.valueOf(Arith.round(detail.getQuantity().doubleValue(), 2));
                data[i][5] = String.valueOf(Arith.round(detail.getReceivedQuantity().doubleValue(), 2));
                data[i][6] = detail.getUnitName();
                data[i][7] = detail.getLocationCode();
                i++;
            }
            pdfPrintData.setData(data);
            pdfPrintData.setTable("table_af_image");
        }

        Map<String, String> img = new HashMap();
        img.put("qr_af_image", QRUtils.qrout("ORDER:" + outOrder.getOrderNo()));
        pdfPrintData.setImage(img);
        String orderType = outOrder.getOrderType();
        if(OutOrderTypeEnum.PRODUCTION.getValue().equals(orderType)){
            pdfPrintData.setTemplatePath("pdf/production_out_order.pdf");
        }else if(OutOrderTypeEnum.COMMON.getValue().equals(orderType)){
            pdfPrintData.setTemplatePath("pdf/common_out_order.pdf");
        }
        return PDFUtils.pdfout(pdfPrintData, Color.LIGHT_GRAY, Color.BLACK);
    }

    /**
     * 打印出库退货单
     */
    public String printOutOrderReturn(OutReturnPdfData outReturn, List<OutReturnDetailPdfData> detailList) throws Exception {
        PdfPrintData pdfPrintData = new PdfPrintData();
        Map<String,String> map = new HashMap<>();
        map.put("returnNo", outReturn.getReturnNo());
        map.put("orderNo", outReturn.getOrderNo());
        map.put("prodOrderNo", outReturn.getProdOrderNo());
        map.put("warehouseName", outReturn.getWarehouseName());
        map.put("workshopName", outReturn.getWorkshopName());
        map.put("returnReason", outReturn.getReturnReason());
        map.put("createTime", DateUtils.parseDateToStr("yyyy-MM-dd HH:mm", outReturn.getCreateTime()));
        map.put("createBy", outReturn.getCreateBy());
        pdfPrintData.setField(map);

        if(!CollectionUtils.isEmpty(detailList)){
            String[][] data = null;
            pdfPrintData.setTitle(new String[]{"行号","物料编码","物料名称","财务编码","图号","需退","已退","单位","批次","所在货位"});
            pdfPrintData.setColum(new float[]{1,2,3,2,2,1,1,1,3,2});
            data = new String[detailList.size() + 1][10];
            int i = 1;
            for(OutReturnDetailPdfData detail : detailList){
                data[i][0] = String.valueOf(i);
                data[i][1] = detail.getMatCode();
                data[i][2] = detail.getMatName();
                data[i][3] = detail.getFdCode();
                data[i][4] = detail.getFigNum();
                data[i][5] = String.valueOf(Arith.round(detail.getQuantity().doubleValue(), 2));
                data[i][6] = String.valueOf(Arith.round(detail.getReturnQuantity().doubleValue(), 2));
                data[i][7] = detail.getUnitName();
                data[i][8] = detail.getBatch();
                data[i][9] = detail.getLocationCode();
                i++;
            }
            pdfPrintData.setData(data);
            pdfPrintData.setTable("table_af_image");
        }

        Map<String, String> img = new HashMap();
        img.put("qr_af_image", QRUtils.qrout("ORDER:" + outReturn.getReturnNo()));
        pdfPrintData.setImage(img);
        if(OutOrderReturnTypeEnum.PRODUCTION_RETURN.getValue().equals(outReturn.getReturnType())){
            pdfPrintData.setTemplatePath("pdf/production_out_return.pdf");
        }else if(OutOrderReturnTypeEnum.COMMON_RETURN.getValue().equals(outReturn.getReturnType())){
            pdfPrintData.setTemplatePath("pdf/common_out_return.pdf");
        }
        return PDFUtils.pdfout(pdfPrintData, Color.LIGHT_GRAY, Color.BLACK);
    }

    /**
     * 打印调拨单
     */
    public String printAllotOrder(AllotOrderPdfData allotOrder, List<AllotDetailPdfData> detailList) throws Exception {
        PdfPrintData pdfPrintData = new PdfPrintData();
        Map<String,String> map = new HashMap<>();
        map.put("allotNo", allotOrder.getAllotNo());
        map.put("allotReason", allotOrder.getAllotReason());
        map.put("srcWarehouseName", allotOrder.getSrcWarehouseName());
        map.put("destWarehouseName", allotOrder.getDestWarehouseName());
        map.put("createTime", DateUtils.parseDateToStr("yyyy-MM-dd HH:mm", allotOrder.getCreateTime()));
        map.put("createBy", allotOrder.getCreateBy());
        pdfPrintData.setField(map);

        if(!CollectionUtils.isEmpty(detailList)){
            String[][] data = null;
            pdfPrintData.setTitle(new String[]{"行号","物料编码","物料名称","图号","发起货位","发起","接收货位","接收","单位"});
            pdfPrintData.setColum(new float[]{1,2,3,2,1,1,1,1,1});
            data = new String[detailList.size() + 1][9];
            int i = 1;
            for(AllotDetailPdfData detail : detailList){
                data[i][0] = String.valueOf(i);
                data[i][1] = detail.getMatCode();
                data[i][2] = detail.getMatName();
                data[i][3] = detail.getFigNum();
                data[i][4] = detail.getSrcLocationCode();
                data[i][5] = String.valueOf(Arith.round(detail.getQuantity().doubleValue(), 2));
                data[i][6] = detail.getDestLocationCode();
                data[i][7] = String.valueOf(Arith.round(detail.getSignQuantity().doubleValue(), 2));
                data[i][8] = detail.getUnitName();
                i++;
            }
            pdfPrintData.setData(data);
            pdfPrintData.setTable("table_af_image");
        }

        Map<String, String> img = new HashMap();
        img.put("qr_af_image", QRUtils.qrout("ORDER:" + allotOrder.getAllotNo()));
        pdfPrintData.setImage(img);
        pdfPrintData.setTemplatePath("pdf/allot_order.pdf");
        return PDFUtils.pdfout(pdfPrintData, Color.LIGHT_GRAY, Color.BLACK);
    }

    /**
     * 打印仓库
     */
    public String printWarehouse(WarehousePdfData warehouse) throws Exception {
        PdfPrintData pdfPrintData = new PdfPrintData();
        Map<String,String> map = new HashMap<>();
        map.put("warehouseCode", warehouse.getWarehouseCode());
        map.put("warehouseName", warehouse.getWarehouseName());
        pdfPrintData.setField(map);

        Map<String, String> img = new HashMap();
        img.put("qr_af_image", QRUtils.qrout("WAREHOUSE:" + warehouse.getWarehouseCode()));
        pdfPrintData.setImage(img);
        pdfPrintData.setTemplatePath("pdf/warehouse.pdf");
        return PDFUtils.pdfout(pdfPrintData, Color.LIGHT_GRAY, Color.BLACK);
    }

    /**
     * 打印货位
     */
    public String printLocation(LocationPdfData location) throws Exception {
        PdfPrintData pdfPrintData = new PdfPrintData();
        Map<String,String> map = new HashMap<>();
        map.put("locationCode", location.getLocationCode());
        map.put("locationName", location.getLocationName());
        pdfPrintData.setField(map);

        Map<String, String> img = new HashMap();
        img.put("qr_af_image", QRUtils.qrout("LOCATION:" + location.getLocationCode() + ":" + location.getWarehouseCode()));
        pdfPrintData.setImage(img);
        pdfPrintData.setTemplatePath("pdf/location.pdf");
        return PDFUtils.pdfout(pdfPrintData, Color.LIGHT_GRAY, Color.BLACK);
    }

}
