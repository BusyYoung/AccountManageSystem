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
public class LogupController {
    private DatabaseManager DB;
    @RequestMapping("/logup")
    public ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        ModelAndView mv = new ModelAndView();// �����µ�ҳ��
        String account_name = (String)req.getAttribute("account_name");//�õ��û���
        String password = (String)req.getAttribute("password");//�õ�����
        String pay_password = (String)req.getAttribute("pay_password");//�õ�֧������
        DB =new DatabaseManager();//���ݿ����
        Boolean exist = DB.existUser(account_name);
        if(!exist) {
            DB.addUser(account_name,password,pay_password);
            mv.addObject("account_name",account_name);
            mv.setViewName("lopup_success");
        }else{
            mv.addObject("account_name",account_name);
            mv.setViewName("logup_error");
        }
        return mv;
    }
}