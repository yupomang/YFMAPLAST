package test;

import net.sf.json.JSONObject;

public class appapi00175test {

    public static void main(String[] args) {
        String result = "{\"code\":\"00\",\"msg\":\"成功\",\"data\":\"\",\"datas\":\"[{\\\"ZSXMMC\\\":\\\"契税\\\",\\\"JSJE\\\":942857.14,\\\"NSRMC\\\":\\\"张乐乐\\\",\\\"SKSSQQ\\\":\\\"2019-04-01\\\",\\\"NSRQ\\\":\\\"2019-04-22\\\",\\\"BZ\\\":\\\" 共有人：张乐乐，王家琴  房源编号:F33021120190010239 房屋坐落地址:镇海区九龙湖镇滕山路359号恒大山水城·明苑1号201室 权属转移面积:88.1平米 合同日期:2019-04-22,\\\",\\\"SWJGMC\\\":\\\"国家税务总局宁波市镇海区税务局骆驼税务所\\\",\\\"NSRSBH\\\":\\\"341224198912313075\\\",\\\"SJJE\\\":9428.58,\\\"SKSSQZ\\\":\\\"2019-04-30\\\",\\\"DZSPHM\\\":\\\"333021190400112108\\\",\\\"ZSPMMC\\\":\\\"存量房（商品住房买卖）\\\",\\\"KSSL\\\":88.1,\\\"SL_1\\\":0.03}]\",\"dataCount\":1,\"requestId\":null,\"interfaces\":null,\"secondaryResults\":null}";
        System.out.println("++++++++++++++++++appapiend!!!");
        JSONObject json = JSONObject.fromObject(result.replace("\\", "").replace(":\"{", ":{").replace("}\",", "},").replace("%},", "%}\","));
        //JSONObject json = JSONObject.fromObject(result.replace("\\", "").replace(":\"{", ":{").replace("}\",", "},").replace("%},", "%}\",").replace(":\"[",":[").replace("}]\"","}]"));
        System.out.println(json.toString());
        json.remove("data");
        if (json.get("code").equals("00")) {
            json.put("recode", "000000");
        }
        json.remove("code");
        System.out.println("++++++++++++++++++appapiend!!!");
    }
}
