package com.ruoyi.web.utils;

import com.ruoyi.base.domain.BaseMat;
import com.ruoyi.base.domain.BaseMatBom;
import com.ruoyi.base.service.IBaseMatBomService;
import com.ruoyi.base.service.IBaseMatService;
import com.ruoyi.common.bean.MatBomTree;
import com.ruoyi.common.utils.Arith;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.stock.domain.StockOutDetail;
import com.ruoyi.system.service.ISysDictDataService;
import org.apache.commons.collections4.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MatBomTreeUtil {

    private static final String dictType = "base_mat_unit";

    public static List<StockOutDetail> getStockOutDetailList(List<BaseMatBom> baseMatBomList, IBaseMatBomService baseMatBomService,
            IBaseMatService baseMatService, ISysDictDataService sysDictDataService){
        List<StockOutDetail> resultList = new ArrayList<>();
        List<MatBomTree> matBomTreeList = getTreeList(baseMatBomList, baseMatBomService);
        if(CollectionUtils.isNotEmpty(matBomTreeList)){
            StockOutDetail detail = null;
            for(MatBomTree matBomTree : matBomTreeList){
                detail = new StockOutDetail();
                //物料编码
                String matCode = matBomTree.getChildMatCode();
                detail.setMatCode(matCode);
                //物料名称
                BaseMat baseMat = baseMatService.selectBaseMatByMatCode(matCode);
                detail.setMatName(baseMat != null ? baseMat.getMatName() : "");
                //财务编码
                detail.setFdCode(baseMat.getFdCode());
                //图号
                detail.setFigNum(baseMat.getFigNum());
                //物料组
                detail.setMatGroup(baseMat.getMatGroup());
                //物料分类
                detail.setMatClass(baseMat.getMatClass());
                //单位
                detail.setUnitCode(baseMat.getUnitCode());
                detail.setUnitName(sysDictDataService.selectDictLabel(dictType, detail.getUnitCode()));
                //数量
                detail.setQuantity(matBomTree.getChildMatNum());
                resultList.add(detail);
            }
        }
        return resultList;
    }

    public static List<MatBomTree> getTreeList(List<BaseMatBom> baseMatBomList, IBaseMatBomService baseMatBomService){
        List<MatBomTree> resultList = new ArrayList<>();
        MatBomTree matBomTree = null;
        for (BaseMatBom item : baseMatBomList) {
            matBomTree = new MatBomTree();
            BeanUtils.copyBeanProp(matBomTree, item);
            if("Y".equals(item.getIsFictitious())){
                matBomTree.setChildren(iterateChildren(resultList, item.getChildMatCode(), baseMatBomService));
            } else {
                checkList(resultList, matBomTree);
            }
        }
        return  resultList;
    }

    /**
     * 递归子信息
     */
    private static List<MatBomTree> iterateChildren(List<MatBomTree> matBomTreeList, String fatherMatCode, IBaseMatBomService baseMatBomService){
        List<MatBomTree> resultList = new ArrayList<>();
        List<BaseMatBom> bomList = baseMatBomService.selectBaseMatBomByFatherMatCode(fatherMatCode);
        MatBomTree matBomTree = null;
        for (BaseMatBom item : bomList) {
            matBomTree = new MatBomTree();
            BeanUtils.copyBeanProp(matBomTree, item);
            if("Y".equals(item.getIsFictitious())){
                matBomTree.setChildren(iterateChildren(matBomTreeList, item.getChildMatCode(), baseMatBomService));
            } else {
                checkList(resultList, matBomTree);
            }
            resultList.add(matBomTree);
        }
        return resultList;
    }

    private static void checkList(List<MatBomTree> matBomTreeList, MatBomTree matBomTree){
        boolean check = false;
        String childMatCode = matBomTree.getChildMatCode();
        for(MatBomTree item : matBomTreeList){
            if(childMatCode.equals(item.getChildMatCode())){
                item.setChildMatNum(item.getChildMatNum().add(matBomTree.getChildMatNum()));
                check = true;
                break;
            }
        }
        if(!check){
            matBomTreeList.add(matBomTree);
        }
    }

}
