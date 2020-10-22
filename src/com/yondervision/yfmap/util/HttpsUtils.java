package com.yondervision.yfmap.util;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;

public class HttpsUtils {


    public static String post(String urlPath, String data, String charSet) {
        return httpPostData(urlPath, data, charSet, null, "application/json", "application/json");
    }

    private static String httpPostData(String urlPath, String data, String charSet, String[] header, String contentType, String accpect) {
        String result = "";
        URL url = null;
        HttpsURLConnection httpurlconnection = null;
        OutputStreamWriter out = null;
        BufferedReader in = null;
        BufferedReader reader = null;
        try {
            //如果是weblogic服务器，此处有坑，weblogic服务器默认使用soaphttpsurlconnection，而不是httpsURLconnection
            //weblogic服务器请用这句   url = new URL(null,urlString,new sun.net.www.protocol.https.Handler());
            url = new URL(urlPath);
            //url = new URL(null,urlPath,new sun.net.www.protocol.https.Handler());
            httpurlconnection = (HttpsURLConnection) url.openConnection();
            httpurlconnection.setSSLSocketFactory(new TLSSocketConnectionFactory());
            httpurlconnection.setDoInput(true);
            httpurlconnection.setDoOutput(true);
            httpurlconnection.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            if (header != null) {
                for (int i = 0; i < header.length; i++) {
                    String[] content = header[i].split(":");
                    httpurlconnection.setRequestProperty(content[0], content[1]);
                }
            }

            httpurlconnection.setRequestMethod("POST");
            httpurlconnection.setRequestProperty("Content-Type", contentType);
            httpurlconnection.connect();
            out = new OutputStreamWriter(httpurlconnection.getOutputStream(), charSet); // utf-8编码
            out.append(data);
            out.flush();
            out.close();

            int code = httpurlconnection.getResponseCode();
            if (code == 200) {
                in = new BufferedReader(new InputStreamReader(httpurlconnection.getInputStream(), "utf-8"));
            } else {
                if (httpurlconnection.getErrorStream() == null) {
                    in = new BufferedReader(new InputStreamReader(httpurlconnection.getInputStream(), "utf-8"));
                } else {
                    in = new BufferedReader(new InputStreamReader(httpurlconnection.getErrorStream(), "utf-8"));
                }
            }
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }

            return result;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            url = null;
            if (httpurlconnection != null) {
                httpurlconnection.disconnect();
            }
            try {
                if (out != null) {
                    out.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                // TODO
            }
        }
        return result;
    }
}
