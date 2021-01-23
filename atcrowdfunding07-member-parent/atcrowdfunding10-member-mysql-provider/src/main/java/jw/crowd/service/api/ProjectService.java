package jw.crowd.service.api;

import jw.crowd.entity.vo.DetailProjectVO;
import jw.crowd.entity.vo.PortalTypeVO;
import jw.crowd.entity.vo.ProjectVO;

import java.util.List;

public interface ProjectService {
    void saveProject(ProjectVO projectVO, Integer memberId);
    List<PortalTypeVO> getPortalTypeVO();
    DetailProjectVO getDetailProjectVO(Integer projectId);
}
