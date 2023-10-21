package com.education.common.template;


import com.education.common.utils.WordGeneratorWithFreemarker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Map;

public class FreeMarkerTemplate extends BaseTemplate {

    public FreeMarkerTemplate(String template, String outputDir) {
        super(template, outputDir);
    }

    @Override
    public void writeToFile(Map data, File outPutFile) {
        OutputStream out;
        try {
            out = new FileOutputStream(outPutFile);
            WordGeneratorWithFreemarker.createDoc(data, template, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
