package com.jzy.sup.starter.email;

import freemarker.template.Configuration;
import freemarker.template.Template;
import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.security.Security;
import java.util.*;

/**
 * <b>功能：</b>邮件服务实现<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190510&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;刘宏超&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Slf4j
@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private EmailConfig emailConfig;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private Configuration configuration;

    private final String FAILURE = "FAILURE";
    private final String SUCCESS = "SUCCESS";

    /**
     * @param sendTo  收件人地址
     * @param title   邮件标题
     * @param content 邮件内容
     * @method-name: sendSimpleMail
     * @description: 发送简单邮件
     * @author: 刘宏超
     * @date: 2017/5/17 16:01
     * @return:
     * @version V1.0
     * update-logs:方法变更说明
     * ****************************************************
     * name:
     * date:
     * description:
     * *****************************************************
     */
    @Override
    public String sendSimpleMail(String sendTo, String title, String content) {
        String result = FAILURE;
        SimpleMailMessage message = new SimpleMailMessage();
        log.debug("发件人：" + emailConfig.getEmailFrom() + ",收件人：" + emailConfig.getEmailTo());
        message.setFrom(emailConfig.getEmailFrom());
        if (Strings.isNullOrEmpty(sendTo))
            message.setTo(emailConfig.getEmailTo().split(","));
        else
            message.setTo(sendTo);
        message.setSubject(title);
        message.setText(content);
        log.debug("开始发送消息++++++++" + message);
        mailSender.send(message);
        log.debug("发送消息结束++++++++" + message);
        result = SUCCESS;
        return result;
    }

    /**
     * <b>功能描述：</b>发送html类型文件<br>
     * <b>修订记录：</b><br>
     * <li>20190605&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */

    public String sendMailHtml(List<String> sendTo, String title, String content) {
        String result = FAILURE;
        try {
            log.debug("发件人：" + emailConfig.getEmailFrom() + "====邮件title:" + title+"===收件人："+sendTo);
            log.debug("开始发送消息++++++++" + content);
            sslSend(sendTo,title, content);
            log.debug("发送消息结束++++++++" + content);
        } catch (MessagingException e) {
            log.info("============邮件发送失败！");
            e.printStackTrace();
            return result;
        }
        result = SUCCESS;
        return result;


    }

    /**
     * <b>功能描述：</b>ssl发送邮件<br>
     * <b>修订记录：</b><br>
     * <li>20190617&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    public boolean sslSend(List<String> sendTo, String title, String content) throws MessagingException {
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        // Get a Properties object
        Properties props = new Properties();
        props.setProperty("mail.smtp.host", emailConfig.getHost());
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", emailConfig.getPort());
        props.setProperty("mail.smtp.socketFactory.port", emailConfig.getPort());
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailConfig.getEmailFrom(), emailConfig.getPassword());
            }
        });
        Message msg = new MimeMessage(session);
        // 设置发件人和收件人
        msg.setFrom(new InternetAddress(emailConfig.getEmailFrom()));
        Address to[] = new InternetAddress[sendTo.size()];
        for (int i = 0; i < sendTo.size(); i++) {
            to[i] = new InternetAddress(sendTo.get(i));
        }
        // 多个收件人地址
        msg.setRecipients(Message.RecipientType.TO, to);
        msg.setSubject(title); // 标题
        // MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
        Multipart mainPart = new MimeMultipart();
        // 创建一个包含HTML内容的MimeBodyPart
        BodyPart html = new MimeBodyPart();
        log.info("======邮件内容：" + content);
        // 设置HTML内容
        html.setContent(content, "text/html;charset=UTF-8");
        mainPart.addBodyPart(html);
        // 将MiniMultipart对象设置为邮件内容
        msg.setContent(mainPart);
        msg.setSentDate(new Date());
        Transport.send(msg);
        log.info("EmailUtil ssl协议邮件发送:" + msg.toString());
        return true;
    }


    /**
     * <b>功能描述：</b>组成邮件发送消息体<br>
     * <b>修订记录：</b><br>
     * <li>20190605&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    private Message getMessage(String title) throws Exception {
        final Properties p = System.getProperties();
        p.setProperty("mail.smtp.host", emailConfig.getHost());
        // p.setProperty("mail.smtp.port", emailConfig.getPort());
        p.setProperty("mail.smtp.auth", "true");
        p.setProperty("mail.smtp.user", emailConfig.getEmailFrom());
        p.setProperty("mail.smtp.pass", emailConfig.getPassword());

        // 根据邮件会话属性和密码验证器构造一个发送邮件的session
        Session session = Session.getInstance(p, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(p.getProperty("mail.smtp.user"), p.getProperty("mail.smtp.pass"));
            }
        });
        session.setDebug(true);
        Message message = new MimeMessage(session);
        //消息发送的主题
        message.setSubject(title);
        //接受消息的人
        message.setReplyTo(InternetAddress.parse(emailConfig.getEmailTo()));
        //消息的发送者
        message.setFrom(new InternetAddress(p.getProperty("mail.smtp.user")));
        // 创建邮件的接收者地址，并设置到邮件消息中
        //  message.setRecipient(Message.RecipientType.TO, new InternetAddress(emailConfig.getEmailTo()));

        String addresss = emailConfig.getEmailTo();
        String[] address = addresss.split(",");
        Address[] addressess = new Address[address.length];
        for (int i = 0; i < address.length; i++) {
            addressess[i] = new InternetAddress(address[i]);
        }
        message.setRecipients(Message.RecipientType.TO, addressess);
        // 消息发送的时间
        message.setSentDate(new Date());


        return message;
    }


    /**
     * @param title   邮件标题
     * @param content 邮件内容
     * @method-name: sendSimpleMail
     * @description: 发送简单邮件, 收件人默认从配置文件中获取
     * @author: 刘宏超
     * @date: 2017/5/17 16:01
     * @return:
     * @version V1.0
     * update-logs:方法变更说明
     * ****************************************************
     * name:
     * date:
     * description:
     * *****************************************************
     */
    @Override
    public String sendSimpleMail(String title, String content) {
        return sendSimpleMail(null, title, content);
    }

    /**
     * @param sendTo      收件人地址
     * @param title       邮件标题
     * @param content     邮件内容
     * @param attachments
     * @method-name:
     * @description: 发送带附件简单邮件
     * @author: 刘宏超
     * @date: 2017/5/17 16:01
     * @return:
     * @version V1.0
     * update-logs:方法变更说明
     * ****************************************************
     * name:
     * date:
     * description:
     * *****************************************************
     */
    @Override
    public String sendAttachmentsMail(String sendTo, String title, String content, List<Pair<String, File>> attachments) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        String result = FAILURE;
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(emailConfig.getEmailFrom());
            if (Strings.isNullOrEmpty(sendTo))
                helper.setTo(emailConfig.getEmailTo().split(","));
            else
                helper.setTo(sendTo);
            helper.setSubject(title);
            helper.setText(content);
            if (attachments != null && !attachments.isEmpty())
                for (Pair<String, File> pair : attachments) {
                    helper.addAttachment(pair.getKey(), new FileSystemResource(pair.getValue()));
                }
            mailSender.send(mimeMessage);
            result = SUCCESS;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * @param title       邮件标题
     * @param content     邮件内容
     * @param attachments
     * @method-name:
     * @description: 发送带附件简单邮件，收件人默认从配置文件中获取
     * @author: 刘宏超
     * @date: 2017/5/17 16:01
     * @return:
     * @version V1.0
     * update-logs:方法变更说明
     * ****************************************************
     * name:
     * date:
     * description:
     * *****************************************************
     */
    @Override
    public String sendAttachmentsMail(String title, String content, List<Pair<String, File>> attachments) {
        return sendAttachmentsMail(null, title, content, attachments);
    }

    /**
     * @param sendTo      收件人地址
     * @param title       邮件标题
     * @param content     邮件内容
     * @param attachments 附件
     * @method-name: sendTemplateMail
     * @description: 发送模板邮件
     * @author: 刘宏超
     * @date: 2017/5/17 16:03
     * @return:
     * @version V1.0
     * update-logs:方法变更说明
     * ****************************************************
     * name:
     * date:
     * description:
     * *****************************************************
     */
    @Override
    public String sendTemplateMail(String sendTo, String title, Map<String, Object> content, List<Pair<String, File>> attachments, String templateFullName) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        String result = FAILURE;
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(emailConfig.getEmailFrom());
            if (Strings.isNullOrEmpty(sendTo))
                helper.setTo(emailConfig.getEmailTo().split(","));
            else
                helper.setTo(sendTo);
            helper.setSubject(title);
            /*freeMarker template*/
            Template template = configuration.getTemplate(templateFullName);
            String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, content);
            helper.setText(text, true);
            if (attachments != null && !attachments.isEmpty())
                for (Pair<String, File> pair : attachments) {
                    helper.addAttachment(pair.getKey(), new FileSystemResource(pair.getValue()));
                }
            mailSender.send(mimeMessage);
            result = SUCCESS;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * @param title            邮件标题
     * @param content          邮件内容
     * @param attachments      附件
     * @param templateFullName 模板全名带后缀
     * @method-name: sendTemplateMail
     * @description: 发送模板邮件，收件人默认从配置文件中获取
     * @author: 刘宏超
     * @date: 2017/5/17 16:03
     * @return:
     * @version V1.0
     * update-logs:方法变更说明
     * ****************************************************
     * name:
     * date:
     * description:
     * *****************************************************
     */
    @Override
    public String sendTemplateMail(String title, Map<String, Object> content, List<Pair<String, File>> attachments, String templateFullName) {
        return sendTemplateMail(null, title, content, attachments, templateFullName);
    }
}
