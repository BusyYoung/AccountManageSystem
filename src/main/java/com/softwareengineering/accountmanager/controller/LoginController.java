package com.softwareengineering.accountmanager.controller;

/**
 * Created by ���� on 2015/6/12.
 */
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.softwareengineering.accountmanager.model.DatabaseManager;
import com.softwareengineering.accountmanager.model.data.CommonInformation;
import com.softwareengineering.accountmanager.model.data.SecurityInformation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.*;
@Controller
public class LoginController {
    private DatabaseManager DB;
    @RequestMapping("/login")
    public ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        ModelAndView mv = new ModelAndView();// �����µ�ҳ��
        String account_name = (String)req.getAttribute("account_name");//�õ��û���
        String password = (String)req.getAttribute("password");//�õ�����
        DB =new DatabaseManager();//���ݿ����
        boolean judgeAccount;//�ж�������û����Ƿ����
        judgeAccount = DB.checkPassword(account_name, password);
        if (judgeAccount) {
            CommonInformation s = DB.queryCommonInformation(account_name);
            mv.addObject("account_name",account_name);
            mv.addObject("information",s);
            mv.addObject("url",req.getRequestURL());
            mv.setViewName("main");
            return mv;
        }
        else{
            mv.addObject("account_name",account_name);
            mv.setViewName("login error");
            return  mv;
        }
    }
}