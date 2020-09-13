package com.yondervision.yfmap.handle.handleImpl.handle00066800;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi020Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.zhuzhou.AppApi02303ZHResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JsonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle02303_00066800  implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		AppApi020Form form = (AppApi020Form)form1;
		String PROPERTIES_FILE_NAME = "properties.properties";
		log.debug("00066800请求02303参数："+CommonUtil.getStringParams(form));
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002Send"+form.getCenterId()).trim();
		AppApi02303ZHResult app02303ZHResult = new AppApi02303ZHResult();
		if(Constants.method_BSP.equals(send)){
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002Key"+form.getCenterId()).trim());
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002Type"+form.getCenterId()).trim());			
			
			if(!form.getChannel().trim().equals("40"))
			{
				form.setChannelSeq(form.getSendSeqno());
				form.setTellCode("9999");
				form.setBrcCode("00066888");
//				form.setTellCode(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001TellCode"+form.getCenterId()).trim());
//				form.setBrcCode(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001BrcCode"+form.getCenterId()).trim());
				form.setTranDate(form.getSendDate());
			}
			form.setFlag(Channel.getZzChannel(form.getChannel()));
//110018交易start
			HashMap map = BeanUtil.transBean2Map(form);
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_YDZRJSHDY1.txt", map, req);
			//String xml = MessageCtrMain.encapsulatedPackets(msgType, "E:/mcq/workspaceEclipse/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00073300/BSP_REQ_YDZRJSHDY1.txt", map, req);
			log.debug("前置YFMAP发往中心报文："+xml);
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "110018");
			
			/*String rexml ="<AuthCode1>1</><AuthCode2>1</><AuthCode3>1</><AuthFlag>1</><BrcCode>1</><BusiSeq>1</><ChannelSeq>1</><ChkCode>1</><MTimeStamp>1</><NoteMsg>1</><RspCode>000000</><RspMsg>OK</><STimeStamp>1</><TellCode>1</><TranChannel>1</><TranCode>192704</><TranDate>1</><TranIP>1</><TranSeq>1</>"
					+ "<zhiye>1</><zjhm>2</><zhiwu1>3</><zhiwu>4</><zjlx>5</><zhichen1>6</><zhichen>7</><yzbm>8</><xueli>9</><xingming>10</><xingbie>11</>"
					+ "<workid>12</><sjhm>13</><jtzz>14</><jtysr>15</><indisoicode>16</><hyzk>17</><gddhhm>18</><email>19</><csny>20</><crelevel>21</><attworkdate>22</>";
			*/
			log.debug("前置YFMAP接收中心报文——个人转移姓名查询："+rexml);
			
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_YDZRJSHDY1.txt", rexml, req);
			//HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "E:/mcq/workspaceEclipse/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00073300/BSP_REP_YDZRJSHDY1.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);
			
			BeanUtil.transMap2Bean(resultMap, app02303ZHResult);
			log.debug("MAP封装成BEAN："+app02303ZHResult);
			if(!"0".equals(app02303ZHResult.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app02303ZHResult.getRecode());
				modelMap.put("msg", app02303ZHResult.getMsg());
				log.error("中心返回报文 状态recode :"+app02303ZHResult.getRecode()+"  ,  描述msg : "+app02303ZHResult.getMsg());
				return modelMap;
			}
//110018交易end
//119001交易start
			String xml1 = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_DWXXGGCX.txt", map, req);
			//String xml1 = MessageCtrMain.encapsulatedPackets(msgType, "E:/mcq/workspaceEclipse/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00073300/BSP_REQ_DWXXGGCX.txt", map, req);
			log.debug("前置YFMAP发往中心报文："+xml1);			
			String rexml1 = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml1, "119001");
			
			/*String rexml1 ="<AuthCode1>1</><AuthCode2>1</><AuthCode3>1</><AuthFlag>1</><BrcCode>1</><BusiSeq>1</><ChannelSeq>1</><ChkCode>1</><MTimeStamp>1</><NoteMsg>1</><RspCode>000000</><RspMsg>OK</><STimeStamp>1</><TellCode>1</><TranChannel>1</><TranCode>192704</><TranDate>1</><TranIP>1</><TranSeq>1</>"
				+"<grjcbl>1</><higpropflag>2</><hproppayamt>3</><hproppaynum>4</><indihigprop>5</><indiprop>6</><indispeprop>7</><fundsouflag>8</><insurancenum>9</><intaccu>10</><isserdispach>11</><jbrgddhhm>12</><jbrsjhm>13</><jbrxm>14</><accbankcode>15</><accinstcode>16</><agentop>17</><allexchgflag>18</><appronum>19</>"
				+"<autpayamt>20</><basebankaccname>21</><basebankaccnum>22</><basenum>23</><baseuppflag>24</><begintdate>25</><begpayym>26</><begym>27</><bfchgindiprop>28</><bfchgunitprop>29</><calintflag>30</><centerid>31</><chgaddpayamt>32</>"
				+"<chgdecpayamt>33</><clsaccdate>34</><cmaddpayamt>35</><cmaddpaynum>36</><cmdecpayamt>37</><cmdecpaynum>38</><cmfinpayamt>39</><cmfinpaynum>40</><cmselpayamt>41</><cmselpaynum>42</><cocietycode>43</><cstcollflag>44</><cstcollno>45</><cydpsum>46</><dwdz>47</><dwfcrs>48</>"
				+"<dwfrdbsjhm>49</><dwfrdbxm>50</><dwfrdbzjhm>51</><dwfrdbzjlx>52</><dwfxr>53</><finalflag>54</><finlpaym>55</><finpaymap>56</><frzamt>57</><frzflag>58</><dwjcbl>59</><dwjcje>60</><dwjcrs>61</><dwjjlx>62</><dwlsgx>63</><dwmc>64</><dwslrq>65</><dwsshy>66</>"
				+"<dwyb>67</><dwzhye>68</><dwzhzt>69</><endym>70</><entflag>71</><exttranslvl>72</><jzny>73</><lastbal>74</><lasttransdate>75</><lmfinpayamt>76</><lmfinpaynum>77</><lmselpayamt>78</><lmselpaynum>79</><lpaym>80</><mngdept>81</><mngdepter>82</>"
				+"<mngdepterphone>83</><morecheflag>84</><norproptype>85</><nproppayamt>86</><nproppaynum>87</><onym>88</><opnaccdate>89</><orgtype>90</><payeebankaccnum>91</><payeebankname>92</><payeeunitname>93</><postpayflag>94</><prebal>95</><prodcode>96</>"
				+"<propflag>97</><propreduendym>98</><rounddigit>99</><roundmode>100</><sellpaym>101</><selpaymap>102</><sincolflag>103</><sproppayamt>104</><sproppaynum>105</><stpayamt>106</><styhdm>107</><styhmc>108</><subcode>109</>"
				+"<supunitaccnum>110</><supunittype>111</><unitacctype>112</><unitastprop>113</><unithigprop>114</><unitkind>115</><unitprefrzamt>116</><unitpresamt>117</><unitprop>118</><unitspeprop>119</><upreusebal>120</><workernum>121</><zzjgdm>122</><dwxhrs>123</><dwzh>124</><unitpreaccnum>125</>";
			*/
			log.debug("前置YFMAP接收中心报文——个人公积金信息查询："+rexml1);
			HashMap resultMap1 = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_DWXXGGCX.txt", rexml1, req);
			//HashMap resultMap1 = MessageCtrMain.analysisPacket(msgType, "E:/mcq/workspaceEclipse/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00073300/BSP_REP_DWXXGGCX.txt", rexml1, req);
			log.debug("解析报文MAP："+resultMap1);
			
			BeanUtil.transMap2Bean(resultMap1, app02303ZHResult);
			log.debug("MAP封装成BEAN："+app02303ZHResult);
			if(!"0".equals(app02303ZHResult.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app02303ZHResult.getRecode());
				modelMap.put("msg", app02303ZHResult.getMsg());
				log.error("中心返回报文 状态recode :"+app02303ZHResult.getRecode()+"  ,  描述msg : "+app02303ZHResult.getMsg());
				return modelMap;
			}
//119001交易end
//137093交易start
			String xml2 = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_YDZRJSHDY2.txt", map, req);
			//String xml2 = MessageCtrMain.encapsulatedPackets(msgType, "E:/mcq/workspaceEclipse/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00073300/BSP_REQ_YDZRJSHDY2.txt", map, req);
			log.debug("前置YFMAP发往中心报文："+xml2);			
			String rexml2 = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml2, "137093");
			
			/*String rexml2 ="<AuthCode1>1</><AuthCode2>1</><AuthCode3>1</><AuthFlag>1</><BrcCode>1</><BusiSeq>1</><ChannelSeq>1</><ChkCode>1</><MTimeStamp>1</><NoteMsg>1</><RspCode>000000</><RspMsg>OK</><STimeStamp>1</><TellCode>1</><TranChannel>1</><TranCode>192704</><TranDate>1</><TranIP>1</><TranSeq>1</>"
				+"<yhzhhm>123</><yhzhmc>456</><yhdm>789</>";
			*/
			log.debug("前置YFMAP接收中心报文——个人公积金信息查询："+rexml2);
			HashMap resultMap2 = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_YDZRJSHDY2.txt", rexml2, req);
			//HashMap resultMap2 = MessageCtrMain.analysisPacket(msgType, "E:/mcq/workspaceEclipse/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00073300/BSP_REP_YDZRJSHDY2.txt", rexml2, req);
			log.debug("解析报文MAP："+resultMap2);
			
			BeanUtil.transMap2Bean(resultMap2, app02303ZHResult);
			log.debug("MAP封装成BEAN："+app02303ZHResult);
			if(!"0".equals(app02303ZHResult.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app02303ZHResult.getRecode());
				modelMap.put("msg", app02303ZHResult.getMsg());
				log.error("中心返回报文 状态recode :"+app02303ZHResult.getRecode()+"  ,  描述msg : "+app02303ZHResult.getMsg());
				return modelMap;
			}
//137093交易end
//110059交易start
			map.put("agentinstcode", resultMap1.get("accinstcode"));
			map.put("agentop", resultMap1.get("agentop"));
			map.put("bankcode", resultMap2.get("yhdm"));
			map.put("dwmc", resultMap1.get("dwmc"));
			map.put("sjhm", resultMap.get("sjhm"));
			map.put("xingming", resultMap.get("xingming"));
			map.put("yhzhhm", resultMap2.get("yhzhhm"));
			map.put("yhzhmc", resultMap2.get("yhzhmc"));
			map.put("zjhm", resultMap.get("zjhm"));
			String xml3 = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_YDZRJSHDY3.txt", map, req);
			///String xml3 = MessageCtrMain.encapsulatedPackets(msgType, "E:/mcq/workspaceEclipse/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00073300/BSP_REQ_YDZRJSHDY3.txt", map, req);
			log.debug("前置YFMAP发往中心报文："+xml3);			
			String rexml3 = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml3, "110059");
			
			//String rexml3 ="<AuthCode1>1</><AuthCode2>1</><AuthCode3>1</><AuthFlag>1</><BrcCode>1</><BusiSeq>1</><ChannelSeq>1</><ChkCode>1</><MTimeStamp>1</><NoteMsg>1</><RspCode>000000</><RspMsg>OK</><STimeStamp>1</><TellCode>1</><TranChannel>1</><TranCode>192704</><TranDate>1</><TranIP>1</><TranSeq>1</>";
			
			log.debug("前置YFMAP接收中心报文——个人公积金信息查询："+rexml3);
			HashMap resultMap3 = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_YDZRJSHDY3.txt", rexml3, req);
			//HashMap resultMap3 = MessageCtrMain.analysisPacket(msgType, "E:/mcq/workspaceEclipse/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00073300/BSP_REP_YDZRJSHDY3.txt", rexml3, req);
			log.debug("解析报文MAP："+resultMap3);
			
			BeanUtil.transMap2Bean(resultMap3, app02303ZHResult);
			log.debug("MAP封装成BEAN："+app02303ZHResult);
			if(!"0".equals(app02303ZHResult.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app02303ZHResult.getRecode());
				modelMap.put("msg", app02303ZHResult.getMsg());
				log.error("中心返回报文 状态recode :"+app02303ZHResult.getRecode()+"  ,  描述msg : "+app02303ZHResult.getMsg());
				return modelMap;
			}
//110059交易end
		}
	
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", BeanUtil.transBean2Map(app02303ZHResult));
		return modelMap;
	}
	
	/*public static void main(String[] args){
		AppApi020Form form1 = new AppApi020Form();
		ModelMap modelMap = new ModelMap();
		form1.setCenterId("00066800");
		form1.setUserId("123456");
		form1.setChannel("40");
		form1.setGrzh("11111");
		form1.setCustid("888");
		form1.setDwzh("22222");
		form1.setInstance("333");
		form1.setYdgjjzx("abc");
		form1.setYhdm("0011");
	
		Handle02303_00066800 hand = new Handle02303_00066800();
		try {
			System.out.println(JsonUtil.getGson().toJson(hand.action(form1, modelMap)));
		} catch (CenterRuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
}
