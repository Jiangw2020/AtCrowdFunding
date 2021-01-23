package jw.crowd.service.impl;

import jw.crowd.entity.po.MemberConfirmInfoPO;
import jw.crowd.entity.po.MemberLaunchInfoPO;
import jw.crowd.entity.po.ProjectPO;
import jw.crowd.entity.po.ReturnPO;
import jw.crowd.entity.vo.MemberConfirmInfoVO;
import jw.crowd.entity.vo.MemberLauchInfoVO;
import jw.crowd.entity.vo.ProjectVO;
import jw.crowd.entity.vo.ReturnVO;
import jw.crowd.mapper.*;
import jw.crowd.service.api.ProjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProjectServiceImpl implements ProjectService {
    @Resource
    private ProjectPOMapper projectPOMapper;
    @Resource
    private ProjectItemPicPOMapper projectItemPicPOMapper;
    @Resource
    private MemberConfirmInfoPOMapper memberConfirmInfoPOMapper;
    @Resource
    private MemberLaunchInfoPOMapper memberLaunchInfoPOMapper;
    @Resource
    private ReturnPOMapper returnPOMapper;

    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    @Override
    public void saveProject(ProjectVO projectVO, Integer memberId) {
        // 一.保存projectPO对象
        // 1.创建ProjectPO对象
        ProjectPO projectPO = new ProjectPO();
        // 2.把ProjectVO中的属性复制ProjectPO属性
        BeanUtils.copyProperties(projectVO, projectPO);
        // 把memberid设置进projectId中
        projectPO.setMemberid(memberId);
        // 生成创建时间
        String createdate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        projectPO.setCreatedate(createdate);
        // status设置为0
        projectPO.setStatus(0);
        // 3.保存projectPo
        // 需要获取到projectPO保存后的自增主键，需要ProjectPOMapper,xml文件中进行相关设置
        projectPOMapper.insertSelective(projectPO);
        // 4.从projectPO对象这里获取自增主键
        Integer projectId = projectPO.getId();
        // 二、保存项目、分类的关联关系信息
        // 1.从ProjectVO对象中获取typeIdList
        List<Integer> typeIdList = projectVO.getTypeIdList();
        projectPOMapper.insertTypeRelationship(typeIdList, projectId);
        // 三、保存项目、标签的关联关系信息
        List<Integer> tagIdList = projectVO.getTagIdList();
        projectPOMapper.insertTagRelationship(tagIdList, projectId);
        // 四、保存项目中详情图片路径信息
        List<String> detailPicturePathList = projectVO.getDetailPicturePathList();
        projectItemPicPOMapper.insertPathList(projectId, detailPicturePathList);
        // 五、保存项目发起人信息
        MemberLauchInfoVO memberLauchInfoVO = projectVO.getMemberLauchInfoVO();
        MemberLaunchInfoPO memberLaunchInfoPO = new MemberLaunchInfoPO();
        BeanUtils.copyProperties(memberLauchInfoVO, memberLaunchInfoPO);
        memberLaunchInfoPO.setMemberid(memberId);

        memberLaunchInfoPOMapper.insert(memberLaunchInfoPO);
        // 六、保存项目汇报信息
        List<ReturnVO> returnVOList = projectVO.getReturnVOList();
        List<ReturnPO> returnPOList = new ArrayList<>();
        for (ReturnVO returnVO : returnVOList) {
            ReturnPO returnPO = new ReturnPO();
            BeanUtils.copyProperties(returnVO, returnPO);
            returnPOList.add(returnPO);
        }
        returnPOMapper.insertReturnPOBatch(returnPOList, projectId);

        // 七、保存项目确认信息
        MemberConfirmInfoVO memberConfirmInfoVO = projectVO.getMemberConfirmInfoVO();
        MemberConfirmInfoPO memberConfirmInfoPO = new MemberConfirmInfoPO();
        BeanUtils.copyProperties(memberConfirmInfoVO, memberConfirmInfoPO);
        memberConfirmInfoPO.setMemberid(memberId);
        memberConfirmInfoPOMapper.insert(memberConfirmInfoPO);
    }
}
