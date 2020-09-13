package com.yondervision.yfmap.controller;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.form.AppApi00230Form;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 俞文杰
 * @version V1.0
 * @date 2020/9/9 11:31
 */
@Controller
public class AppApi00260Controller {
    Logger log = Logger.getLogger("YFMAP");

    /**
     *  宁波公积金自助机提前还贷获取利息
     * @param form
     * @param modelMap
     * @throws Exception
     */
    @RequestMapping("/appapi00261.{ext}")
    public String appapi00261(AppApi00230Form form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info(Constants.LOG_HEAD + "appapi00261 begin.");
        CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle" + form.getCenterId() + ".Handle00261_" + form.getCenterId()).newInstance();
        business.action(form, modelMap);
        log.info(Constants.LOG_HEAD + "appapi00261 end.");
        return "";
    }

    /**
     *  宁波公积金自助机提前还贷
     * @param form
     * @param modelMap
     * @throws Exception
     */
    @RequestMapping("/appapi00262.{ext}")
    public String appapi00262(AppApi00230Form form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info(Constants.LOG_HEAD + "appapi00262 begin.");
        CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle" + form.getCenterId() + ".Handle00262_" + form.getCenterId()).newInstance();
        business.action(form, modelMap);
        log.info(Constants.LOG_HEAD + "appapi00262 end.");
        return "";
    }

}
