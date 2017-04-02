package com.weike.java.util;

import com.sun.deploy.net.HttpResponse;
import net.sf.json.JSONObject;
import org.weixin4j.WeixinException;
import org.weixin4j.http.HttpClient;
import sun.misc.BASE64Decoder;

import javax.imageio.stream.FileImageOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by tina on 4/2/17.
 */
public class WeixinUtil {
    private HttpClientUtil httpClientUtil = new HttpClientUtil();
    private String APPID = "wx5761aea4e548362c";
    private String APPSECRET = "b6f9c59d06c3731a3bd1c28bcaf7af24";

    public boolean generateQRCode(String url, String path) throws WeixinException {

        String accessToken = getAccessToken().getString("access_token");
        String data = "{\"path\":\"" + url + "\",\"width\": 430}";
        return getQRCodeJson(accessToken, data, path);
    }

    private JSONObject getAccessToken() throws WeixinException {
        String requestUrl = " https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
        requestUrl = requestUrl.replace("APPID", APPID).replace("APPSECRET", APPSECRET);

        String returnString = httpClientUtil.sendGet(requestUrl);
        JSONObject jsonObject = JSONObject.fromObject(returnString);
        return jsonObject;
    }

    private boolean getQRCodeJson(String accessToken, String msg, String path) throws WeixinException {

        String requestUrl = " https://api.weixin.qq.com/cgi-bin/wxaapp/createwxaqrcode?access_token=TOKEN";
        requestUrl = requestUrl.replace("TOKEN", accessToken);

        byte[] returnByteArray = httpClientUtil.sendPostAndReturnByteArray(requestUrl, msg);
        if (byte2image(returnByteArray, path)) {
            return true;
        }
        return false;
    }

//    public String getQr(String accessToken, String jsonMsg) throws WeixinException {
//        String result = null;
//        String requestUrl = " https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKEN";
//        requestUrl = requestUrl.replace("TOKEN", accessToken);
//        JSONObject msg = JSONObject.fromObject(jsonMsg);
//        JSONObject jsonObject = JSONObject.fromObject(httpClient.post(requestUrl, msg));
//        if(null != jsonObject){
//            System.out.println(jsonObject);
//            result = jsonObject+"";
//        }
//        return result;
//    }
//
//    //用ticket获取二维码
//    public boolean chageQr(String ticket) throws WeixinException {
//        boolean result = false;
//        String requestUrl = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET";
//        requestUrl = requestUrl.replace("TICKET", ticket);
//        JSONObject jsonObject = JSONObject.fromObject(httpClient.get(requestUrl));
//        if(null != jsonObject){
//            System.out.println(jsonObject);
//            int errorCode = jsonObject.getInt("errcode");
//            String errorMsg = jsonObject.getString("errmsg");
//            if(0 == errorCode){
//                result = true;
//                System.out.println("成功errorCode:{"+errorCode+"},errmsg:{"+errorMsg+"}");
//            }else{
//                System.out.println("失败errorCode:{"+errorCode+"},errmsg:{"+errorMsg+"}");
//            }
//        }
//        return result;
//    }

    //对字节数组字符串进行Base64解码并生成图片
    private boolean generateImage(String imgStr, String filePath) {
        if (imgStr == null) {
            return false;
        }
        BASE64Decoder decoder = new BASE64Decoder();

        try {
            //Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for(int i=0;i<b.length;++i) {
                if(b[i]<0) {
                    b[i]+=256;
                }
            }
            //生成jpeg图片
            OutputStream out = new FileOutputStream(filePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    //byte数组到图片
    public boolean byte2image(byte[] data, String path){
        if(data.length < 3 || path.equals("")) return false;
        try{
            FileImageOutputStream imageOutput = new FileImageOutputStream(new File(path));
            imageOutput.write(data, 0, data.length);
            imageOutput.close();
            System.out.println("Make Picture success,Please find image in " + path);
            return true;
        } catch(Exception ex) {
            System.out.println("Exception: " + ex);
            ex.printStackTrace();
        }
        return false;
    }

    //byte数组到16进制字符串
    public String byte2string(byte[] data){
        if(data == null || data.length <= 1) return "0x";
        if(data.length > 200000) return "0x";
        StringBuffer sb = new StringBuffer();
        int buf[] = new int[data.length];
        //byte数组转化成十进制
        for(int k = 0; k < data.length; k++){
            buf[k] = data[k] < 0 ? (data[k] + 256) : (data[k]);
        }
        //十进制转化成十六进制
        for(int k = 0; k < buf.length; k++){
            if(buf[k] < 16) sb.append("0" + Integer.toHexString(buf[k]));
            else sb.append(Integer.toHexString(buf[k]));
        }
        return "0x" + sb.toString().toUpperCase();
    }


}
