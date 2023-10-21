package com.education.common.template;

import com.jfinal.template.Engine;
import com.jfinal.template.source.ISourceFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2019/5/15 22:03
 */
public class EnjoyTemplate extends BaseTemplate {

    private Engine engine;
   /* private String template;
    private String outputDir;*/

    public EnjoyTemplate(String template, String outputDir) {
        super(template, outputDir);
        this.engine = new Engine();
        engine.setToClassPathSourceFactory();
    }

    @Override
    public void writeToFile(Map data, File outPutFile) {
        OutputStreamWriter osw = null;
        try {
            osw = new OutputStreamWriter(new FileOutputStream(outPutFile), "UTF-8");
            String content = engine.getTemplate(template).renderToString(data);
            osw.write(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (osw != null) {
                try {
                    osw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setSourceFactory(ISourceFactory sourceFactory) {
        this.engine.setSourceFactory(sourceFactory);
    }

    public Engine getEngine() {
        return engine;
    }
}
