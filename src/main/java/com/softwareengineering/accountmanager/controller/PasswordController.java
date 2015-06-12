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
public class PasswordController {
    private DatabaseManager DB;
    @RequestMapping("/password_change")
    public ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        ModelAndView mv = new ModelAndView();// �����µ�ҳ��
        String account_name = (String)req.getAttribute("account_name");//�õ��û���
        String old_password = (String)req.getAttribute("old_password");//�õ��͵�����
        String new_password = (String)req.getAttribute("new_password");//�õ�������
        DB =new DatabaseManager();//���ݿ����
        boolean judge = DB.checkPassword(account_name,old_password);//�жϾ������Ƿ���ȷ
        if(judge){
            DB.changePassword(account_name,new_password);
            mv.addObject("account_name",account_name);
            mv.setViewName("password_success");
        }else{
            mv.addObject("account_name",account_name);
            mv.setViewName("password_failed");
        }
        return mv;
    }
}

