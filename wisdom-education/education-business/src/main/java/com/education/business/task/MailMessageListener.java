package com.education.business.task;

import com.education.business.task.param.EmailMessageTaskParam;
import com.education.common.annotation.EventQueue;
import com.education.common.constants.LocalQueueConstants;
import com.education.common.utils.Ip2regionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.util.Date;


/**
 * @author zengjintao
 * @create_at 2021年11月19日 0019 15:11
 * @since version 1.6.6
 */
@Component
@EventQueue(name = LocalQueueConstants.EMAIL_MESSAGE)
public class MailMessageListener implements TaskListener<EmailMessageTaskParam> {

    private static final Logger logger = LoggerFactory.getLogger(MailMessageListener.class);

    @Resource
    private JavaMailSender javaMailSender;

    @Override
    public void onEmailMessage(EmailMessageTaskParam taskParam) {
        String ip = taskParam.getIp();
        String address = Ip2regionUtil.getIpProvince(ip);
        String content = "您的账号已在" + address + "登录，如非本人操作请及时修改密码";

        String mail = taskParam.getEmail();
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setText(content);
            mimeMessageHelper.setFrom("18296640717@163.com");
            mimeMessageHelper.setTo(mail);
            mimeMessageHelper.setSubject("账号异地登录通知");
            mimeMessageHelper.setSentDate(new Date());
        } catch (Exception e) {
            logger.error("邮件:{}发送异常", mail, e);
        }
    }
}
