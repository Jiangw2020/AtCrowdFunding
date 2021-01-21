package jw.crowd.service.api;

import jw.crowd.entity.Auth;

import java.util.List;
import java.util.Map;

public interface AuthService {

    List<Integer> getAssignedAuthIdByRoleId(Integer roleId);

    List<Auth> getAll();

    void saveRoleAuthRelathinship(Map<String, List<Integer>> map);

    List<String> getAssignedAuthNameByAdminId(Integer adminId);
}
