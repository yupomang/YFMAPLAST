package com.yondervision.yfmap.handle.handleImpl.handle00057400;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi00230Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.ningbo.AppApi80007ZHResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.PropertiesReader;
import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * @author 俞文杰
 * @version V1.0
 * @date 2020/9/9 14:06
 */
public class Handle00261_00057400 implements CtrlHandleInter {
    Logger log = Logger.getLogger("YFMAP");

    @Override
    public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException, Exception {
        String PROPERTIES_FILE_NAME = "properties.properties";
        log.debug("00057400请求00261参数前："+ CommonUtil.getStringParams(form1));
        AppApi00230Form form = (AppApi00230Form)form1;
        log.debug("00057400请求00261参数："+ CommonUtil.getStringParams(form));
        String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();

        AppApi80007ZHResult app80007ZHResult = new AppApi80007ZHResult();
        if(Constants.method_BSP.equals(send)){
            String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType"+form.getCenterId()).trim();
            form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key"+form.getCenterId()).trim());
            SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date date = new Date();
            form.setSendDate(formatter1.format(date));
            form.setSendTime(formatter2.format(date));
            String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
            form.setSendSeqno(req);
            form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type"+form.getCenterId()).trim());
            form.setFlag(Channel.getZzChannel(form.getChannel()));
            form.setQdapprnum(form.getQdapprnum());
            HashMap map = BeanUtil.transBean2Map(form);
            map.put("tellcode", PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapichannel"+form.getChannel()).trim());
            String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_00261_128931.txt", map, req);
            log.debug("前置YFMAP发往中心报文："+xml);

            String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
            String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
            String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "128931");
            log.debug("前置YFMAP接收中心报文——合同变更申请前查询申请："+rexml);
            HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_00261_128931.txt", rexml, req);
            log.debug("解析报文MAP："+resultMap);

            BeanUtil.transMap2Bean(resultMap, app80007ZHResult);
            log.debug("MAP封装成BEAN："+app80007ZHResult);
            if("0".equals(app80007ZHResult.getSpt_success())){
                modelMap.clear();
                modelMap.put("recode", "999999");
                modelMap.put("spt_showmsg", app80007ZHResult.getSpt_showmsg());
                modelMap.put("spt_success", app80007ZHResult.getSpt_success());
                log.error("中心返回报文 状态recode :"+app80007ZHResult.getRecode()+"  ,  描述msg : "+app80007ZHResult.getSpt_showmsg());
                return modelMap;
            }else if ("1".equals(app80007ZHResult.getSpt_success())){
                modelMap.clear();
                modelMap.put("recode", "000000");
                modelMap.put("spt_showmsg", app80007ZHResult.getSpt_showmsg());
                modelMap.put("spt_success", app80007ZHResult.getSpt_success());
                modelMap.put("rsufilename", app80007ZHResult.getRsufilename());
                modelMap.put("spt_errormsg", app80007ZHResult.getSpt_errormsg());
                modelMap.put("spt_datacount", app80007ZHResult.getSpt_datacount());
                modelMap.put("stdata", app80007ZHResult.getStdata());
                modelMap.put("spt_ywid", app80007ZHResult.getSpt_ywid());
                return modelMap;
            }
        }
        modelMap.clear();
        modelMap.put("recode", "999999");
        modelMap.put("msg", "对不起 出现未知错误 请检查信息填写有无错误 如无错误请联系公积金中心科技处解决");
        return modelMap;
    }
}
