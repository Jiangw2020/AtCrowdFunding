package jw.crowd.service.api;

import jw.crowd.entity.po.MemberPO;

public interface MemberService {
    public MemberPO getMemberPOByLoginAcct(String loginacct);

    void saveMember(MemberPO memberPO);
}

