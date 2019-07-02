package com.jzy.sup.starter.email;

import javafx.util.Pair;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * <b>功能：</b>邮件服务接口
 * 1. 发送简单邮件
 * 2. 发送带附件简单邮件
 * 3. 发送模板邮件。<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190510&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;刘宏超&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public interface EmailService {

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
    String sendSimpleMail(String sendTo, String title, String content);

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
    String sendSimpleMail(String title, String content);

    /**
     * @param sendTo              收件人地址
     * @param title               邮件标题
     * @param content             邮件内容
     * @param attachments<文件名，附件> 附件列表
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
    String sendAttachmentsMail(String sendTo, String title, String content, List<Pair<String, File>> attachments);

    /**
     * @param title               邮件标题
     * @param content             邮件内容
     * @param attachments<文件名，附件> 附件列表
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
    String sendAttachmentsMail(String title, String content, List<Pair<String, File>> attachments);

    /**
     * @param sendTo              收件人地址
     * @param title               邮件标题
     * @param content<key,内容>     邮件内容
     * @param attachments<文件名,附件> 附件列表
     * @param templateFullName    模板全名带后缀
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
    String sendTemplateMail(String sendTo, String title, Map<String, Object> content, List<Pair<String, File>> attachments, String templateFullName);

    /**
     * @param title               邮件标题
     * @param content<key,内容>     邮件内容
     * @param attachments<文件名,附件> 附件列表
     * @param templateFullName    模板全名带后缀
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
    String sendTemplateMail(String title, Map<String, Object> content, List<Pair<String, File>> attachments, String templateFullName);


    /**
     * <b>功能描述：</b>发送html类型文件<br>
     * <b>修订记录：</b><br>
     * <li>20190605&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */

    String sendMailHtml(List<String> sendTo,String title, String content) throws Exception;


}
