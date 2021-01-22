package jw.crowd.util;

import com.aliyun.api.gateway.demo.util.HttpUtils;
import jw.crowd.constant.CrowdConstant;
import org.apache.http.HttpResponse;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class CrowdUtil {
    /**
     * 判断当前请求是否为 Ajax 请求
     *
     * @param request
     * @return
     */
    public static boolean judgeRequestType(HttpServletRequest request) {
        // 1.获取请求消息头信息
        String acceptInformation = request.getHeader("Accept");
        String xRequestInformation = request.getHeader("X-Requested-With");
        // 2.检查并返回
        return
            (acceptInformation != null && acceptInformation.length() > 0 && acceptInformation.contains("application/json"))
            ||
            (xRequestInformation != null && xRequestInformation.length() > 0 && xRequestInformation.equals("XMLHttpRequest"));
    }
    /**
     *  对明文字符串进行 MD5 加密
     *  @param source 传入的明文字符串
     *  @return 加密结果
     */
    public static String md5(String source) {
        // 1.判断 source 是否有效
        if(source == null || source.length() == 0) {
            // 2.如果不是有效的字符串抛出异常
            throw new RuntimeException(CrowdConstant.MESSAGE_STRING_INVALIDATE);
        }
        try {
            // 3.获取 MessageDigest 对象
            String algorithm = "md5";
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            // 4.获取明文字符串对应的字节数组
            byte[] input = source.getBytes();
            // 5.执行加密
            byte[] output = messageDigest.digest(input);
            // 6.创建 BigInteger 对象
            int signum = 1;
            BigInteger bigInteger = new BigInteger(signum, output);
            // 7.按照 16 进制将 bigInteger 的值转换为字符串
            int radix = 16;
            String encoded = bigInteger.toString(radix).toUpperCase();
            return encoded;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
    /** 给远程第三方短信接口发送请求把验证码发送到用户手机上
     * 0000:成功		i005:业务异常		9999:系统异常		1999:服务异常
     * 成功：返回验证码
     * 失败：返回失败消息
     *  */
    public static ResultEntity<String> sendCodeByShortMessage(
            String host,
            String path,
            String method,
            String mobile,
            String appCode,
            //模板编号；测试用默认的：0000000
            String templateID){
//        String host = "https://intlsms.market.alicloudapi.com";
//        String path = "/comms/sms/sendmsgall";
//        String method = "POST";
//        String appcode = "aad14596b0814f028a940470c30528d8";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appCode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        //可选	短信下发状态回调通知地址（主动回调）
        bodys.put("callbackUrl", "http://test.dev.esandcloud.com");
        //可选	0: 默认通道（默认值） 1: 高质量通道
        bodys.put("channel", "0");
        //必选	手机号码，采用 e.164 标准，格式为+[国家或地区码][手机号]，例如：+8613700000000
        bodys.put("mobile", mobile);
        //必选	模板 ID, 登录https://openali.esandcloud.com申请
        bodys.put("templateID", templateID);

        //生成4位数验证码
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<4;i++) {
            int rd = (int)(Math.random()*10);
            sb.append(rd);
        }
        String code = sb.toString();
        sb.append(", 1");
        String s = sb.toString();
        //可选	模板参数(多个参数用逗号分隔)
        bodys.put("templateParamSet", s);
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
//            System.out.println(response.toString());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
            //statusCode为200:成功
            int statusCode = response.getStatusLine().getStatusCode();
            String reasonPhrase = response.getStatusLine().getReasonPhrase();
            if(statusCode==200) {
                //成功，返回验证码
                return ResultEntity.successWithData(code);
            }
            //失败，返回原因
            return ResultEntity.failed(reasonPhrase);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }
}
