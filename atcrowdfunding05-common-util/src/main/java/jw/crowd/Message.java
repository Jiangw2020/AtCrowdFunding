package jw.crowd;

import com.aliyun.api.gateway.demo.util.HttpUtils;
import org.apache.http.HttpResponse;

import java.util.HashMap;
import java.util.Map;

public class Message {
    public static void main(String[] args) {
        String host = "https://intlsms.market.alicloudapi.com";
        String path = "/comms/sms/sendmsgall";
        String method = "POST";
        String appcode = "aad14596b0814f028a940470c30528d8";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        //可选	短信下发状态回调通知地址（主动回调）
        bodys.put("callbackUrl", "http://test.dev.esandcloud.com");
        //可选	0: 默认通道（默认值） 1: 高质量通道
        bodys.put("channel", "0");
        //必选	手机号码，采用 e.164 标准，格式为+[国家或地区码][手机号]，例如：+8613700000000
        bodys.put("mobile", "+8619959099421");
        //必选	模板 ID, 登录https://openali.esandcloud.com申请
        bodys.put("templateID", "0000000");
        //可选	模板参数(多个参数用逗号分隔)。
        bodys.put("templateParamSet", "2233, 1");


        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
