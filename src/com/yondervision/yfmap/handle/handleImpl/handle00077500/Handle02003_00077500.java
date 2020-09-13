package com.yondervision.yfmap.handle.handleImpl.handle00077500;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi020Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter1;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.zhuzhou.AppApi02003ZHResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JsonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle02003_00077500 implements CtrlHandleInter1{
	Logger log = Logger.getLogger("YFMAP");
	
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws CenterRuntimeException, Exception {
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi020Form form = (AppApi020Form)form1;
		log.debug("00077500请求02003参数："+CommonUtil.getStringParams(form));
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		AppApi02003ZHResult app02003ZHResult = new AppApi02003ZHResult();
		
		if(Constants.method_BSP.equals(send)){////xml通信处理
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType"+form.getCenterId()).trim();
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key"+form.getCenterId()).trim());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type"+form.getCenterId()).trim());
			
			if(!form.getChannel().trim().equals("40"))
			{
				form.setChannelSeq(form.getSendSeqno());
				form.setTellCode("9999");
				form.setBrcCode("00073199");
				form.setTranDate(form.getSendDate());
			}
			form.setFlag(Channel.getZzChannel(form.getChannel()));
			
			HashMap map = BeanUtil.transBean2Map(form);		
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_DWXXGGCX.txt", map, req);
			//String xml = MessageCtrMain.encapsulatedPackets(msgType, "E:/mcq/workspaceEclipse/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00077500/BSP_REQ_DWXXGGCX.txt", map, req);
			log.debug("前置YFMAP发往中心报文："+xml);			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "119001");
			
			/*String rexml ="<AuthCode1>1</><AuthCode2>1</><AuthCode3>1</><AuthFlag>1</><BrcCode>1</><BusiSeq>1</><ChannelSeq>1</><ChkCode>1</><MTimeStamp>1</><NoteMsg>1</><RspCode>000000</><RspMsg>OK</><STimeStamp>1</><TellCode>1</><TranChannel>1</><TranCode>192704</><TranDate>1</><TranIP>1</><TranSeq>1</>"
				+"<grjcbl>1</><higpropflag>2</><hproppayamt>3</><hproppaynum>4</><indihigprop>5</><indiprop>6</><indispeprop>7</><fundsouflag>8</><insurancenum>9</><intaccu>10</><isserdispach>11</><jbrgddhhm>12</><jbrsjhm>13</><jbrxm>14</><accbankcode>15</><accinstcode>16</><agentop>17</><allexchgflag>18</><appronum>19</>"
				+"<autpayamt>20</><basebankaccname>21</><basebankaccnum>22</><basenum>23</><baseuppflag>24</><begintdate>25</><begpayym>26</><begym>27</><bfchgindiprop>28</><bfchgunitprop>29</><calintflag>30</><centerid>31</><chgaddpayamt>32</>"
				+"<chgdecpayamt>33</><clsaccdate>34</><cmaddpayamt>35</><cmaddpaynum>36</><cmdecpayamt>37</><cmdecpaynum>38</><cmfinpayamt>39</><cmfinpaynum>40</><cmselpayamt>41</><cmselpaynum>42</><cocietycode>43</><cstcollflag>44</><cstcollno>45</><cydpsum>46</><dwdz>47</><dwfcrs>48</>"
				+"<dwfrdbsjhm>49</><dwfrdbxm>50</><dwfrdbzjhm>51</><dwfrdbzjlx>52</><dwfxr>53</><finalflag>54</><finlpaym>55</><finpaymap>56</><frzamt>57</><frzflag>58</><dwjcbl>59</><dwjcje>60</><dwjcrs>61</><dwjjlx>62</><dwlsgx>63</><dwmc>64</><dwslrq>65</><dwsshy>66</>"
				+"<dwyb>67</><dwzhye>68</><dwzhzt>69</><endym>70</><entflag>71</><exttranslvl>72</><jzny>73</><lastbal>74</><lasttransdate>75</><lmfinpayamt>76</><lmfinpaynum>77</><lmselpayamt>78</><lmselpaynum>79</><lpaym>80</><mngdept>81</><mngdepter>82</>"
				+"<mngdepterphone>83</><morecheflag>84</><norproptype>85</><nproppayamt>86</><nproppaynum>87</><onym>88</><opnaccdate>89</><orgtype>90</><payeebankaccnum>91</><payeebankname>92</><payeeunitname>93</><postpayflag>94</><prebal>95</><prodcode>96</>"
				+"<propflag>97</><propreduendym>98</><rounddigit>99</><roundmode>100</><sellpaym>101</><selpaymap>102</><sincolflag>103</><sproppayamt>104</><sproppaynum>105</><stpayamt>106</><styhdm>107</><styhmc>108</><subcode>109</>"
				+"<supunitaccnum>110</><supunittype>111</><unitacctype>112</><unitastprop>113</><unithigprop>114</><unitkind>115</><unitprefrzamt>116</><unitpresamt>117</><unitprop>118</><unitspeprop>119</><upreusebal>120</><workernum>121</><zzjgdm>122</><dwxhrs>123</><dwzh>124</><unitpreaccnum>125</>";
			*/
			log.debug("前置YFMAP接收中心报文——个人公积金信息查询："+rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_DWXXGGCX.txt", rexml, req);
			//HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "E:/mcq/workspaceEclipse/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00077500/BSP_REP_DWXXGGCX.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app02003ZHResult);
			log.debug("MAP封装成BEAN："+app02003ZHResult);
			if(!"0".equals(app02003ZHResult.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app02003ZHResult.getRecode());
				modelMap.put("msg", app02003ZHResult.getMsg());
				log.error("中心返回报文 状态recode :"+app02003ZHResult.getRecode()+"  ,  描述msg : "+app02003ZHResult.getMsg());
				return modelMap;
			}
		
		}
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("grjcbl", app02003ZHResult.getGrjcbl());
		modelMap.put("higpropflag", app02003ZHResult.getHigpropflag());
		modelMap.put("hproppayamt", app02003ZHResult.getHproppayamt());
		modelMap.put("hproppaynum", app02003ZHResult.getHproppaynum());
		modelMap.put("indihigprop", app02003ZHResult.getIndihigprop());
		modelMap.put("indiprop", app02003ZHResult.getIndiprop());
		modelMap.put("indispeprop", app02003ZHResult.getIndispeprop());
		modelMap.put("fundsouflag", app02003ZHResult.getFundsouflag());
		modelMap.put("insurancenum", app02003ZHResult.getInsurancenum());
		modelMap.put("intaccu", app02003ZHResult.getIntaccu());
		modelMap.put("isserdispach", app02003ZHResult.getIsserdispach());
		modelMap.put("jbrgddhhm", app02003ZHResult.getJbrgddhhm());
		modelMap.put("jbrsjhm", app02003ZHResult.getJbrsjhm());
		modelMap.put("jbrxm", app02003ZHResult.getJbrxm());
		modelMap.put("accbankcode", app02003ZHResult.getAccbankcode());
		modelMap.put("accinstcode", app02003ZHResult.getAccinstcode());
		modelMap.put("agentop", app02003ZHResult.getAgentop());
		modelMap.put("allexchgflag", app02003ZHResult.getAllexchgflag());
		modelMap.put("appronum", app02003ZHResult.getAppronum());
		modelMap.put("autpayamt", app02003ZHResult.getAutpayamt());
		modelMap.put("basebankaccname", app02003ZHResult.getBasebankaccname());
		modelMap.put("basebankaccnum", app02003ZHResult.getBasebankaccnum());
		modelMap.put("basenum", app02003ZHResult.getBasenum());
		modelMap.put("baseuppflag", app02003ZHResult.getBaseuppflag());
		modelMap.put("begintdate", app02003ZHResult.getBegintdate());
		modelMap.put("begpayym", app02003ZHResult.getBegpayym());
		modelMap.put("begym", app02003ZHResult.getBegym());
		modelMap.put("bfchgindiprop", app02003ZHResult.getBfchgindiprop());
		modelMap.put("bfchgunitprop", app02003ZHResult.getBfchgunitprop());
		modelMap.put("calintflag", app02003ZHResult.getCalintflag());
		modelMap.put("centerid", app02003ZHResult.getCenterid());
		modelMap.put("chgaddpayamt", app02003ZHResult.getChgaddpayamt());
		modelMap.put("chgdecpayamt", app02003ZHResult.getChgdecpayamt());
		modelMap.put("clsaccdate", app02003ZHResult.getClsaccdate());
		modelMap.put("cmaddpayamt", app02003ZHResult.getCmaddpayamt());
		modelMap.put("cmaddpaynum", app02003ZHResult.getCmaddpaynum());
		modelMap.put("cmdecpayamt", app02003ZHResult.getCmdecpayamt());
		modelMap.put("cmdecpaynum", app02003ZHResult.getCmdecpaynum());
		modelMap.put("cmfinpayamt", app02003ZHResult.getCmfinpayamt());
		modelMap.put("cmfinpaynum", app02003ZHResult.getCmfinpaynum());
		modelMap.put("cmselpayamt", app02003ZHResult.getCmselpayamt());
		modelMap.put("cmselpaynum", app02003ZHResult.getCmselpaynum());
		modelMap.put("cocietycode", app02003ZHResult.getCocietycode());
		modelMap.put("cstcollflag", app02003ZHResult.getCstcollflag());
		modelMap.put("cstcollno", app02003ZHResult.getCstcollno());
		modelMap.put("cydpsum", app02003ZHResult.getCydpsum());
		modelMap.put("dwdz", app02003ZHResult.getDwdz());
		modelMap.put("dwfcrs", app02003ZHResult.getDwfcrs());
		modelMap.put("dwfrdbsjhm", app02003ZHResult.getDwfrdbsjhm());
		modelMap.put("dwfrdbxm", app02003ZHResult.getDwfrdbxm());
		modelMap.put("dwfrdbzjhm", app02003ZHResult.getDwfrdbzjhm());
		modelMap.put("dwfrdbzjlx", app02003ZHResult.getDwfrdbzjlx());
		modelMap.put("dwfxr", app02003ZHResult.getDwfxr());
		modelMap.put("finalflag", app02003ZHResult.getFinalflag());
		modelMap.put("finlpaym", app02003ZHResult.getFinlpaym());
		modelMap.put("finpaymap", app02003ZHResult.getFinpaymap());
		modelMap.put("frzamt", app02003ZHResult.getFrzamt());
		modelMap.put("frzflag", app02003ZHResult.getFrzflag());
		modelMap.put("dwjcbl", app02003ZHResult.getDwjcbl());
		modelMap.put("dwjcje", app02003ZHResult.getDwjcje());
		modelMap.put("dwjcrs", app02003ZHResult.getDwjcrs());
		modelMap.put("dwjjlx", app02003ZHResult.getDwjjlx());
		modelMap.put("dwlsgx", app02003ZHResult.getDwlsgx());
		modelMap.put("dwmc", app02003ZHResult.getDwmc());
		modelMap.put("dwslrq", app02003ZHResult.getDwslrq());
		modelMap.put("dwsshy", app02003ZHResult.getDwsshy());
		modelMap.put("dwyb", app02003ZHResult.getDwyb());
		modelMap.put("dwzhye", app02003ZHResult.getDwzhye());
		modelMap.put("dwzhzt", app02003ZHResult.getDwzhzt());
		modelMap.put("endym", app02003ZHResult.getEndym());
		modelMap.put("entflag", app02003ZHResult.getEntflag());
		modelMap.put("exttranslvl", app02003ZHResult.getExttranslvl());
		modelMap.put("jzny", app02003ZHResult.getJzny());
		modelMap.put("lastbal", app02003ZHResult.getLastbal());
		modelMap.put("lasttransdate", app02003ZHResult.getLasttransdate());
		modelMap.put("lmfinpayamt", app02003ZHResult.getLmfinpayamt());
		modelMap.put("lmfinpaynum", app02003ZHResult.getLmfinpaynum());
		modelMap.put("lmselpayamt", app02003ZHResult.getLmselpayamt());
		modelMap.put("lmselpaynum", app02003ZHResult.getLmselpaynum());
		modelMap.put("lpaym", app02003ZHResult.getLpaym());
		modelMap.put("mngdept", app02003ZHResult.getMngdept());
		modelMap.put("mngdepter", app02003ZHResult.getMngdepter());
		modelMap.put("mngdepterphone", app02003ZHResult.getMngdepterphone());
		modelMap.put("morecheflag", app02003ZHResult.getMorecheflag());
		modelMap.put("norproptype", app02003ZHResult.getNorproptype());
		modelMap.put("nproppayamt", app02003ZHResult.getNproppayamt());
		modelMap.put("nproppaynum", app02003ZHResult.getNproppaynum());
		modelMap.put("onym", app02003ZHResult.getOnym());
		modelMap.put("opnaccdate", app02003ZHResult.getOpnaccdate());
		modelMap.put("orgtype", app02003ZHResult.getOrgtype());
		modelMap.put("payeebankaccnum", app02003ZHResult.getPayeebankaccnum());
		modelMap.put("payeebankname", app02003ZHResult.getPayeebankname());
		modelMap.put("payeeunitname", app02003ZHResult.getPayeeunitname());
		modelMap.put("postpayflag", app02003ZHResult.getPostpayflag());
		modelMap.put("prebal", app02003ZHResult.getPrebal());
		modelMap.put("prodcode", app02003ZHResult.getProdcode());
		modelMap.put("propflag", app02003ZHResult.getPropflag());
		modelMap.put("propreduendym", app02003ZHResult.getPropreduendym());
		modelMap.put("rounddigit", app02003ZHResult.getRounddigit());
		modelMap.put("roundmode", app02003ZHResult.getRoundmode());
		modelMap.put("sellpaym", app02003ZHResult.getSellpaym());
		modelMap.put("selpaymap", app02003ZHResult.getSelpaymap());
		modelMap.put("sincolflag", app02003ZHResult.getSincolflag());
		modelMap.put("sproppayamt", app02003ZHResult.getSproppayamt());
		modelMap.put("sproppaynum", app02003ZHResult.getSproppaynum());
		modelMap.put("stpayamt", app02003ZHResult.getStpayamt());
		modelMap.put("styhdm", app02003ZHResult.getStyhdm());
		modelMap.put("styhmc", app02003ZHResult.getStyhmc());
		modelMap.put("subcode", app02003ZHResult.getSubcode());
		modelMap.put("supunitaccnum", app02003ZHResult.getSupunitaccnum());
		modelMap.put("supunittype", app02003ZHResult.getSupunittype());
		modelMap.put("unitacctype", app02003ZHResult.getUnitacctype());
		modelMap.put("unitastprop", app02003ZHResult.getUnitastprop());
		modelMap.put("unithigprop", app02003ZHResult.getUnithigprop());
		modelMap.put("unitkind", app02003ZHResult.getUnitkind());
		modelMap.put("unitprefrzamt", app02003ZHResult.getUnitprefrzamt());
		modelMap.put("unitpresamt", app02003ZHResult.getUnitpresamt());
		modelMap.put("unitprop", app02003ZHResult.getUnitprop());
		modelMap.put("unitspeprop", app02003ZHResult.getUnitspeprop());
		modelMap.put("upreusebal", app02003ZHResult.getUpreusebal());
		modelMap.put("workernum", app02003ZHResult.getWorkernum());
		modelMap.put("zzjgdm", app02003ZHResult.getZzjgdm());
		modelMap.put("dwxhrs", app02003ZHResult.getDwxhrs());
		modelMap.put("dwzh", app02003ZHResult.getDwzh());
		modelMap.put("unitpreaccnum", app02003ZHResult.getUnitpreaccnum());
		
		return modelMap;
	}

	/*public static void main(String[] args){
		AppApi020Form form1 = new AppApi020Form();
		ModelMap modelMap = new ModelMap();
		form1.setCenterId("00077500");
		form1.setUserId("123456");
		form1.setChannel("40");
		form1.setTranflag("123");
		form1.setTsounitaccnum("456");
		form1.setUnitcustid("789");
		form1.setDwzh("001");
		form1.setUnitpreaccnum("200");

		Handle02003_00077500 hand = new Handle02003_00077500();
		try {
			System.out.println(JsonUtil.getGson().toJson(hand.action(form1, modelMap, null, null)));
		} catch (CenterRuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
}
