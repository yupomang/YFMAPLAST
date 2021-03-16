package test;

import net.sf.json.JSONObject;

public class jsontest {
    public static void main(String[] args) {
        JSONObject json1 = new JSONObject();
        json1.put("filepath","http://61.153.144.77:7001/YBMAPZH/webapi90001.json?filepath=/ispshare/ftpdir/");
        json1.put("filename","json.txt");
        System.out.println(json1.toString());
    }
}
