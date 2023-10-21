package com.education.common.upload;


import java.util.Date;

/**
 * @author zengjintao
 * @create_at 2021/10/31 15:53
 * @since version 1.0.3
 */
public class UploadResult {

    private Date creationDate;
    private String fileUrl;

    public UploadResult(String fileUrl) {
        this.fileUrl = fileUrl;
        this.creationDate = new Date();
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public UploadResult() {

    }
}
