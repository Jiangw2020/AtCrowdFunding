package jw.crowd.service.impl;

import jw.crowd.entity.po.MemberPO;
import jw.crowd.entity.po.MemberPOExample;
import jw.crowd.entity.po.MemberPOExample.Criteria;
import jw.crowd.mapper.MemberPOMapper;
import jw.crowd.service.api.MemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

// 在类上使用@Transactional(readOnly = true)针对查询操作设置事务属性
@Transactional(readOnly = true)
@Service
public class MemberServiceImpl implements MemberService {
    @Resource
    private MemberPOMapper memberPOMapper;

    @Override
    public MemberPO getMemberPOByLoginAcct(String loginacct) {
        // 1.创建 Example 对象
        MemberPOExample example = new MemberPOExample();
        // 2.创建 Criteria 对象

        Criteria criteria = example.createCriteria();
        // 3.封装查询条件
        criteria.andLoginacctEqualTo(loginacct);
        // 4.执行查询
        List<MemberPO> list = memberPOMapper.selectByExample(example);
        // 5.获取结果
        if(list==null || list.size()==0){
            return null;
        }
        return list.get(0);
    }
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class,readOnly = false)
    @Override
    public void saveMember(MemberPO memberPO) {
        memberPOMapper.insertSelective(memberPO);
    }
}