package jw.crowd.util;

import com.aliyun.api.gateway.demo.util.HttpUtils;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.comm.ResponseMessage;
import com.aliyun.oss.model.PutObjectResult;
import jw.crowd.constant.CrowdConstant;
import org.apache.http.HttpResponse;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
    /**
     * 专门负责上传文件到 OSS 服务器的工具方法
     * @param endpoint OSS 参数
     * @param accessKeyId OSS 参数
     * @param accessKeySecret OSS 参数
     * @param inputStream 要上传的文件的输入流
     * @param bucketName OSS 参数
     * @param bucketDomain OSS 参数
     * @param originalName 要上传的文件的原始文件名
     * @return 包含上传结果以及上传的文件在 OSS 上的访问路径
     */
    public static ResultEntity<String> uploadFileToOss(
            String endpoint,
            String accessKeyId,
            String accessKeySecret,
            InputStream inputStream,
            String bucketName,
            String bucketDomain,
            String originalName) {
        // 创建 OSSClient 实例
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 生成上传文件的目录
        String folderName = new SimpleDateFormat("yyyyMMdd").format(new Date());
        // 生成上传文件在 OSS 服务器上保存时的文件名
        // 原始文件名：beautfulgirl.jpg
        // 生成文件名：wer234234efwer235346457dfswet346235.jpg
        // 使用 UUID 生成文件主体名称
        String fileMainName = UUID.randomUUID().toString().replace("-", "");
        // 从原始文件名中获取文件扩展名
        String extensionName = originalName.substring(originalName.lastIndexOf("."));
        // 使用目录、文件主体名称、文件扩展名称拼接得到对象名称
        String objectName = folderName + "/" + fileMainName + extensionName;
        try {
            // 调用 OSS 客户端对象的方法上传文件并获取响应结果数据
            PutObjectResult putObjectResult = ossClient.putObject(bucketName, objectName, inputStream);
            // 从响应结果中获取具体响应消息
            ResponseMessage responseMessage = putObjectResult.getResponse();
            // 根据响应状态码判断请求是否成功
            if(responseMessage == null) {
                // 拼接访问刚刚上传的文件的路径
                String ossFileAccessPath = bucketDomain + "/" + objectName;
            // 当前方法返回成功
                return ResultEntity.successWithData(ossFileAccessPath);
            } else {
                // 获取响应状态码
                int statusCode = responseMessage.getStatusCode();
                // 如果请求没有成功，获取错误消息
                String errorMessage = responseMessage.getErrorResponseAsString();
                // 当前方法返回失败
                return ResultEntity.failed(" 当 前 响 应 状 态 码 ="+statusCode+" 错 误 消 息 ="+errorMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 当前方法返回失败
            return ResultEntity.failed(e.getMessage());
        } finally {
            if(ossClient != null) {
                // 关闭 OSSClient
                ossClient.shutdown();
            }
        }
    }
}
