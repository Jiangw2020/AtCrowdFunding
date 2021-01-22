package jw.crowd.handler;

import jw.crowd.constant.CrowdConstant;
import jw.crowd.entity.po.MemberPO;
import jw.crowd.service.api.MemberService;
import jw.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberProviderHandler {
    @Autowired
    private MemberService memberService;

    @RequestMapping("save/member/remote")
    ResultEntity<String> saveMemberRemote(@RequestBody MemberPO memberPO){
        try {
            memberService.saveMember(memberPO);
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            //账号重复类型的错误
            if(e instanceof DuplicateKeyException) {
                return ResultEntity.failed(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }
            return ResultEntity.failed(e.getMessage());
        }
    }
//    @RequestMapping("/get/member/by/login/remote")
//    ResultEntity<MemberPO> getMemberByLoginRemote(@RequestParam("loginacct")String loginacct){
//        try {
//            //如果查得到--正常
//            MemberPO memberPO = memberService.getMemberByLoginService(loginacct);
//            return ResultEntity.successWithData(memberPO);
//        } catch (Exception e) {
//            //如果查不到--报错
//            return ResultEntity.failed(e.getMessage());
//        }
//    }
    @RequestMapping("/get/memberpo/by/login/acct/remote")
    public ResultEntity<MemberPO> getMemberPOByLoginAcctRemote(@RequestParam("loginacct") String loginacct) {
        try {
            // 1.调用本地 Service 完成查询
            MemberPO memberPO = memberService.getMemberPOByLoginAcct(loginacct);
            // 2.如果没有抛异常，那么就返回成功的结果
            return ResultEntity.successWithData(memberPO);
        } catch (Exception e) {
            e.printStackTrace();
            // 3.如果捕获到异常则返回失败的结果
            return ResultEntity.failed(e.getMessage());
        }
    }
}
