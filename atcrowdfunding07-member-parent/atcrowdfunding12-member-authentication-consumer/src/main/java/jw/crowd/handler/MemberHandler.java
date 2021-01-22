package jw.crowd.handler;

import jw.crowd.api.MySQLRemoteService;
import jw.crowd.api.RedisRemoteService;
import jw.crowd.config.ShortMessageProperties;
import jw.crowd.constant.CrowdConstant;
import jw.crowd.entity.po.MemberPO;
import jw.crowd.entity.vo.MemberLoginVO;
import jw.crowd.entity.vo.MemberVO;
import jw.crowd.util.CrowdUtil;
import jw.crowd.util.ResultEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Controller
public class MemberHandler {
    @Autowired
    MySQLRemoteService mySQLRemoteService;
    @Autowired
    ShortMessageProperties shortMessageProperties;
    @Autowired
    RedisRemoteService redisRemoteService;

    @RequestMapping("/auth/do/member/logout")
    public String memberLogin(HttpSession session){
        session.invalidate();
        return "redirect:http://localhost/";
    }

    @RequestMapping("/auth/do/member/login")
    public String memberLogin(
            @RequestParam("loginacct") String loginacct,
            @RequestParam("userpswd") String userpswd,
            ModelMap modelMap,
            HttpSession session){
        ResultEntity<MemberPO> resultEntity = mySQLRemoteService.getMemberPOByLoginAcctRemote(loginacct);
        if(ResultEntity.FAILED.equals(resultEntity.getOperationResult())){
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,resultEntity.getOperationMessage());
            return "member-login";
        }
        MemberPO memberPO = resultEntity.getQueryData();
        if(memberPO==null){
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_LOGIN_FAILED);
            return "member-login";
        }
        String userpswdFromDB = memberPO.getUserpswd();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean matcheResult = passwordEncoder.matches(userpswd, userpswdFromDB);
        if(!matcheResult){
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_LOGIN_FAILED);
            return "member-login";
        }
        MemberLoginVO memberLoginVO = new MemberLoginVO(memberPO.getId(), memberPO.getUsername(), memberPO.getEmail());
        session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER,memberLoginVO);
        return "redirect:http://localhost/auth/member/to/center/page";
    }
    @RequestMapping("/auth/do/member/register")
    public String memberRegister(MemberVO memberVO, ModelMap modelMap){
        // 1.获取用户输入的手机号
        String phoneNum = memberVO.getPhoneNum();
        // 2.拼Redis中存储验证码的Key
        String key = CrowdConstant.REDIS_CODE_PREFIX+phoneNum;
        // 3.从Redis读取Key对应的value
        ResultEntity<String> redisResult = redisRemoteService.getRedisStringValueByKeyRemote(key);
        // 4.检查查询操作是否有效
        String result = redisResult.getOperationResult();
        if(ResultEntity.SUCCESS.equals(result)) {
            // 5.如果从Redis能够查询到value则比较表单验证码和Redis验证码
            //表单验证码
            String formCode = memberVO.getCode();
            //redis验证码
            String redisCode = redisResult.getQueryData();
            if(Objects.equals(formCode, redisCode)) {
                // 6.如果验证码一致，则从Redis删除
                redisRemoteService.removeRedisKeyRemote(key);
                // 7.密码加密
                String userpswdBeforeEncode = memberVO.getUserpswd();
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String userpswdAfterEncode = passwordEncoder.encode(userpswdBeforeEncode);
                memberVO.setUserpswd(userpswdAfterEncode);
                // 8.保存
                // 复制属性
                MemberPO memberPO = new MemberPO();
                BeanUtils.copyProperties(memberVO, memberPO);
                //注意：api和mysql，也就是接受端，要加@RequestBody，不然不能正确接收到
                ResultEntity<String> SaveMemberResultEntity = mySQLRemoteService.saveMemberRemote(memberPO);
                if(ResultEntity.SUCCESS.equals(SaveMemberResultEntity.getOperationResult())) {
                    //成功
                    // 使用重定向避免刷新浏览器导致重新执行注册流程
                    return "redirect:/auth/member/to/login/page";
                }else {
                    //失败
                    modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,SaveMemberResultEntity.getOperationMessage());
                    return "member-reg" ;
                }
            }else {
                //验证码不一致
                modelMap.put(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_CODE_INVALID);
                return "member-reg";
            }
        }else {
            //查询失败，返回注册页
            modelMap.put(CrowdConstant.ATTR_NAME_MESSAGE, redisResult.getOperationMessage());
            return "member-reg";
        }

        //return null;
    }
    @ResponseBody
    @RequestMapping("/auth/member/send/short/message.json")
    public ResultEntity<String> sendMessage(@RequestParam("phoneNum") String phoneNum) {
        StringBuilder sb = new StringBuilder();
        sb.append("+86");
        sb.append(phoneNum);
        String phoneNumWithPrefix = sb.toString();
        // 1.发送验证码到phoneNum手机
        ResultEntity<String> sendMessageResultEntity = CrowdUtil.sendCodeByShortMessage(
                shortMessageProperties.getHost(),
                shortMessageProperties.getPath(),
                shortMessageProperties.getMethod(),
                phoneNumWithPrefix,
                shortMessageProperties.getAppCode(),
                shortMessageProperties.getTemplateID());
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
