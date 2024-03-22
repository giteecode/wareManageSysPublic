package com.ruoyi.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * pdf 字体库路径
 */
@Configuration
public class PDFConfig {

    @Value("${pdf.pdf_font_library}")
    private String pdf_font_library;

    public String getPdfFontLibrary(){
        return pdf_font_library;
    }

}
