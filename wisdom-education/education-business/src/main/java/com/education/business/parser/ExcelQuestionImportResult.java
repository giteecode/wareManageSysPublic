package com.education.business.parser;

import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import cn.afterturn.easypoi.excel.entity.result.ExcelVerifyHandlerResult;
import cn.afterturn.easypoi.handler.inter.IExcelVerifyHandler;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.education.common.component.SpringBeanManager;
import com.education.common.enums.QuestionTypeEnum;
import com.education.common.model.ExcelResult;
import com.education.common.upload.FileUpload;
import com.education.common.utils.ExcelUtils;
import com.education.common.utils.FileUtils;
import com.education.common.utils.ObjectUtils;
import com.education.model.entity.QuestionInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2021/1/16 15:01
 */
public class ExcelQuestionImportResult extends QuestionImportResult {

    private final Logger logger = LoggerFactory.getLogger(ExcelQuestionImportResult.class);

    public ExcelQuestionImportResult(InputStream inputStream) {
        super(inputStream);
    }

    public ExcelQuestionImportResult(MultipartFile file, HttpServletResponse response) throws IOException {
        super(file, response);
    }

    public ExcelQuestionImportResult(MultipartFile file) throws IOException {
        super(file);
    }

    @Override
    public void readTemplate() {
        try {
            ImportParams importParams = new ImportParams();
            importParams.setNeedVerfiy(true);// 设置需要校验
            // 设置excel文件路径
            importParams.setSaveUrl(FileUtils.getUploadPath() + importParams.getSaveUrl());
            importParams.setVerifyHandler((IExcelVerifyHandler<QuestionInfo>) questionInfo -> {
                ExcelVerifyHandlerResult verifyHandlerResult = new ExcelVerifyHandlerResult();
                // 防止easy poi 解析数据出现空白行
                if (questionInfo.excelDataIsAllNull()) {
                    verifyHandlerResult.setSuccess(true);
                    return verifyHandlerResult;
                }

                // 校验试题类型是否合法
                String questionTypeName = questionInfo.getQuestionTypeName();

                // 校验试题类型是否正确
                QuestionTypeEnum questionTypeEnum = Arrays.stream(QuestionTypeEnum.values())
                        .filter(value -> value.getName().equals(questionTypeName))
                        .findAny().orElse(null);

                if (questionTypeEnum == null) {
                    verifyHandlerResult.setSuccess(false);
                    verifyHandlerResult.setMsg("试题类型不正确");
                }
                else {
                    verifyHandlerResult.setSuccess(true);
                }
                return verifyHandlerResult;
            });

            ExcelResult excelResult = ExcelUtils.importExcel(super.getInputStream(),
                    QuestionInfo.class, importParams, "/question/importExcelError/");
            ExcelImportResult result = excelResult.getExcelImportResult();
            if (StrUtil.isNotBlank(excelResult.getErrorExcelUrl())) {
                super.setErrorMsg(excelResult.getErrorMsg());
                super.setFailImportQuestionList(result.getFailList());
                super.setErrorFileUrl(excelResult.getErrorExcelUrl());
                File errorFile = new File(excelResult.getErrorFilePath());
                FileInputStream fileInputStream = new FileInputStream(errorFile);
                FileUpload fileUpload = SpringBeanManager.getBean(FileUpload.class);
                fileUpload.putObject(excelResult.getErrorExcelUrl(), fileInputStream); // 错误提示excel文件上传oss
                FileUtil.del(errorFile);
            } else if (ObjectUtils.isEmpty(result) || ObjectUtils.isEmpty(result.getList())) {
                super.setHasData(false);
                super.setErrorMsg("导入excel模板内容为空,请添加数据之后再导入");
            } else {
                super.setHasData(true);
                super.setSuccessImportQuestionList(result.getList());
            }
        } catch (Exception e) {
            logger.error("模板数据解析异常", e);
        }
    }


   /* private void downLoadErrorExcelInfo(ExcelResult excelResult) {
        //文件输入流
        FileInputStream fis = null;
        XSSFWorkbook wb = null;
        //使用XSSFWorkbook对象读取excel文件
        try {
            fis = new FileInputStream(FileUtils.getUploadPath() + excelResult.getErrorExcelUrl());
            wb = new XSSFWorkbook(fis);
            fis.close();

            HttpServletResponse response = getResponse();
            //设置contentType为vnd.ms-excel
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setCharacterEncoding("utf-8");

            response.setHeader("Content-disposition", "attachment;filename=stuTemplateExcel.xlsx");

            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }*/
}
