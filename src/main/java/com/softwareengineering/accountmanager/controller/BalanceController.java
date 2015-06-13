package com.softwareengineering.accountmanager.controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.softwareengineering.accountmanager.model.DatabaseManager;
import com.softwareengineering.accountmanager.model.data.CommonInformation;
import com.softwareengineering.accountmanager.model.data.SecurityInformation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.*;
@Controller
public class BalanceController {
    private DatabaseManager DB;
    @RequestMapping("/balance_update_")
    public ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        DB =new DatabaseManager();
        ModelAndView mv = new ModelAndView();
        String account_name = req.getParameter("account_name");
        String payword = req.getParameter("payword");
        Double balance = Double.parseDouble(req.getParameter("balance"));
        boolean judge = DB.checkPayPassword(account_name,payword);
        if(judge){
            DB.updateBalance(account_name,balance);
            mv.addObject("account_name",account_name);
            mv.setViewName("success");
        }else{
            mv.addObject("account_name",account_name);
            mv.setViewName("fail");
        }
        return mv;
    }

    @RequestMapping("/balance_update")
    public ModelAndView EntryBalance(HttpServletRequest req,HttpServletResponse resp)throws Exception {
        ModelAndView mv = new ModelAndView();
        String account_name = req.getParameter("account_name");
        mv.addObject("account_name",account_name);
        mv.setViewName("balance");
        return mv;
    }
}