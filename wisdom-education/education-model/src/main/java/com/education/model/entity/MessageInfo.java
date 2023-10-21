package com.education.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 消息通知表
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/12/13 14:40
 */
@TableName("message_info")
public class MessageInfo extends BaseEntity<MessageInfo> {

    @TableField("student_id")
    private Integer studentId;

    @TableField("read_flag")
    private Integer readFlag;

    private String content;

    @TableField("message_type")
    private Integer messageType;

    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getReadFlag() {
        return readFlag;
    }

    public void setReadFlag(Integer readFlag) {
        this.readFlag = readFlag;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
