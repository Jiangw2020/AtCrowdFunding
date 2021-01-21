package jw.crowd.handler;

import jw.crowd.api.RedisRemoteService;
import jw.crowd.config.ShortMessageProperties;
import jw.crowd.constant.CrowdConstant;
import jw.crowd.util.CrowdUtil;
import jw.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.TimeUnit;

@Controller
public class MemberHandler {
    @Autowired
    ShortMessageProperties shortMessageProperties;
    @Autowired
    RedisRemoteService redisRemoteService;
    @ResponseBody
    @RequestMapping("/auth/member/send/short/message.json")
    public ResultEntity<String> sendMessage(@RequestParam("phoneNum") String phoneNum) {
        StringBuilder sb = new StringBuilder();
        sb.append("+86");
        sb.append(phoneNum);
        phoneNum = sb.toString();
        // 1.发送验证码到phoneNum手机
        ResultEntity<String> sendMessageResultEntity = CrowdUtil.sendCodeByShortMessage(
                shortMessageProperties.getHost(),
                shortMessageProperties.getPath(),
                shortMessageProperties.getMethod(),
                phoneNum,
                shortMessageProperties.getAppCode(),
                shortMessageProperties.getTemplateID());
        System.out.println(sendMessageResultEntity.getOperationResult());
        // 2.判断短信发送结果
        if(ResultEntity.SUCCESS.equals(sendMessageResultEntity.getOperationResult())) {
            // 3.如果发送成功，则将验证码存入Redis
            // ①从上一步操作的结果中获取随机生成的验证码
            String code = sendMessageResultEntity.getQueryData();

            // ②拼接一个用于在Redis中存储数据的key
            String key = CrowdConstant.REDIS_CODE_PREFIX + phoneNum;

            // ③调用远程接口存入Redis
            ResultEntity<String> saveCodeResultEntity = redisRemoteService.setRedisKeyValueRemoteWithTimeout(key, code, 15, TimeUnit.MINUTES);

            // ④判断结果
            if(ResultEntity.SUCCESS.equals(saveCodeResultEntity.getOperationResult())) {

                return ResultEntity.successWithoutData();
            }else {
                return saveCodeResultEntity;
            }
        } else {
            return sendMessageResultEntity;
        }
    }
}
