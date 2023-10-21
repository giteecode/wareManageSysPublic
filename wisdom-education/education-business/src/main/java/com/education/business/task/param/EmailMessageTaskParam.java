package com.education.business.task.param;

/**
 * @author zjt
 * @create_at 2022年11月24日 0024 15:59
 * @since 2.0.1
 */
public class EmailMessageTaskParam extends TaskParam {

    /**
     * 邮箱号
     */
    private String email;

    /**
     * ip地址
     */
    private String ip;

    public EmailMessageTaskParam(String queueName) {
        super(queueName);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
