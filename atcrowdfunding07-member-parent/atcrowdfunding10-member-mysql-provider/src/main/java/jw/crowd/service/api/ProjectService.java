package jw.crowd.service.api;

import jw.crowd.entity.vo.ProjectVO;

public interface ProjectService {
    void saveProject(ProjectVO projectVO, Integer memberId);
}
