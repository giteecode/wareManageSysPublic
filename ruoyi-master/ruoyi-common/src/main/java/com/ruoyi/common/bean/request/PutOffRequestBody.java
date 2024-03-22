package com.ruoyi.common.bean.request;

import lombok.Data;

import java.util.List;

@Data
public class PutOffRequestBody {

    private String warehouseCode;

    private String locationCode;

    private List<MatLabelRequestData> matLabelList;

}
