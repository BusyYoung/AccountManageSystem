package com.softwareengineering.accountmanager.controller;

import com.softwareengineering.accountmanager.model.DatabaseManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Properties;

/**
 * Created by ���� on 2015/6/20.
 */
@Controller
public class SendForgetController {
    private DatabaseManager DB;
    @RequestMapping("/forget_mail")
    public ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        DB =new DatabaseManager();
        ModelAndView mv = new ModelAndView();
        String account_name = req.getParameter("account_name");
        Boolean judge = DB.existUser(account_name);
        if(!judge) {
            mv.setViewName("forget_error");
        }else{
            String registerId = "" + Math.random() * Math.random();
            DB.prepareResetPassword(account_name,registerId);
            String from = "3120100759@zju.edu.cn";
            // �������Ǵӱ����������͵����ʼ�
            String host = "smtp.zju.edu.cn";
            // ��ȡϵͳ������
            Properties properties = System.getProperties();
            // �����ʼ�������
            properties.setProperty("mail.transport.protocol", "smtp");
            properties.setProperty("mail.smtp.auth", "true");
            properties.setProperty("mail.smtp.host", host);
            properties.setProperty("mail.user", "3120100759");
            properties.setProperty("mail.password", "060979");
            String url = "http://localhost:8080/forget_mail_back?registerId=" + registerId + "&account_name="+account_name;
            Session session = Session.getInstance(properties,
                    //������֤��Ϣ���ڲ���
                    new Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication("3120100759@zju.edu.cn", "060979");
                        }
                    }
            );
            session.setDebug(true);
            try{
                // ����һ��Ĭ�ϵ� MimeMessage ����
                MimeMessage message = new MimeMessage(session);
                // ���� From: header field of the header.
                message.setFrom(new InternetAddress(from));
                // ���� To: header field of the header.
                message.addRecipient(Message.RecipientType.TO,
                        new InternetAddress(account_name));
                // ���� Subject: header field
                message.setSubject("identity for logup");
                message.setSentDate(new Date());
                // ��������ʵ����Ϣ
                message.setContent("<p>click<a href=" + url + ">" + url + "</a>to sign up</p>", "text/html");
                // ������Ϣ
                Transport.send(message);
            }catch (MessagingException mex) {
                mex.printStackTrace();
            }
            mv.setViewName("check_mail_forget");
        }
        return mv;
    }
}