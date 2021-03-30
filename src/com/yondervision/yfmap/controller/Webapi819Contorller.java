package com.yondervision.yfmap.controller;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.form.AppApi80016Form;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
public class Webapi819Contorller {
    Logger log = Logger.getLogger("YFMAP");
    /**
     *  住房公积金个人账户基本信息查询打印接口(省平台)
     * @param form
     * @param modelMap
     * @throws Exception
     */
    @RequestMapping("/webapi80019.{ext}")
    public String webapi80019(AppApi80016Form form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info(Constants.LOG_HEAD+"webapi80019 begin.");

        log.info("year1====="+request.getParameter("seqno")+"+++++++++++++"+request.getAttribute("seqno"));
        //form.setSpt_ywid(request.getParameter("spt_ywid"));
        log.info("year1+spt_ywid==="+form.getSpt_ywid()+"-----"+form.getYear1());
        CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle80016_"+form.getCenterId()).newInstance();
        business.action(form, modelMap);
        log.info(Constants.LOG_HEAD+"appApi03824 end.");
        return "/index";
    }
}
