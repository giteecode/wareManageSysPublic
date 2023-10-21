package com.education.business.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.util.Date;

/**
 * @author zjt
 * @create_at 2022年2月10日 0010 14:10
 * @since 1.6.9
 */
@Service
public class EmailSender {

    private static final Logger logger = LoggerFactory.getLogger(EmailSender.class);
    @Resource
    private JavaMailSender javaMailSender;
    @Resource
    private MailProperties mailProperties;

    public void sendEmail( String subject, String content, String toEmail) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        try {
            mimeMessageHelper.setText(content);
            mimeMessageHelper.setFrom(mailProperties.getUsername());
            mimeMessageHelper.setTo(toEmail);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setSentDate(new Date());
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            logger.error("{}:邮件通知发送异常", toEmail, e);
        }
    }
}
