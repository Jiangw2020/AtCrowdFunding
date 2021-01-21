package jw.crowd.mvc.handler;

import com.github.pagehelper.PageInfo;
import jw.crowd.constant.CrowdConstant;
import jw.crowd.entity.Admin;
import jw.crowd.service.api.AdminService;
import jw.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class AdminHandler {

    @Autowired
    AdminService adminService;

    @RequestMapping("/edit/admin.html")
    public String editAdmin(Admin admin,@RequestParam("pageNum") Integer pageNum){
        adminService.editAdminById(admin);
        return "redirect:/admin/get/page.html?pageNum="+pageNum;
    }

    @RequestMapping("/admin/to/edit/page.html")
    public String toEditPage(@RequestParam("adminId") Integer adminId,
                             @RequestParam("pageNum") Integer pageNum,
                             ModelMap modelMap) {
        // 1.根据 id（主键）查询待更新的 Admin 对象
        Admin admin = adminService.getAdminById(adminId);
        // 2.将 Admin 对象存入模型
        modelMap.addAttribute("admin", admin);
        modelMap.addAttribute("pageNum",pageNum);
        return "admin-edit";
    }
    @PreAuthorize("hasAuthority('user:save')")
    @RequestMapping("/save/admin.html")
    public String saveAdmin(Admin admin) {
        // 执行保存
        adminService.saveAdmin(admin);
        // 重定向到分页页面，使用重定向是为了避免刷新浏览器重复提交表单
        return "redirect:/admin/get/page.html?pageNum="+Integer.MAX_VALUE;
    }

    @RequestMapping("/admin/remove/{adminId}/{pageNum}/{keyword}.html")
    public String remove(
            @PathVariable("adminId") Integer adminId,
            @PathVariable("pageNum") Integer pageNum,
            @PathVariable("keyword") String keyword) {
        // 执行删除
        adminService.remove(adminId);
        // 页面跳转：回到分页页面
        // 尝试方案 1：直接转发到 admin-page.jsp 会无法显示分页数据
        // return "admin-page";
        // 尝试方案 2：转发到/admin/get/page.html 地址，一旦刷新页面会重复执行删除浪费性能
        // return "forward:/admin/get/page.html";
        // 尝试方案 3：重定向到/admin/get/page.html 地址
        // 同时为了保持原本所在的页面和查询关键词再附加 pageNum 和 keyword 两个请求参数
        return "redirect:/admin/get/page.html?pageNum="+pageNum+"&keyword="+keyword;
    }

    @RequestMapping("/admin/get/page.html")
    public String getAdminPageInfo(
            // 注意：页面上有可能不提供关键词，要进行适配
            // 在@RequestParam注解中设置defaultValue属性为空字符串表示浏览器不提 供关键词时，keyword 变量赋值为空字符串
            @RequestParam(value="keyword", defaultValue="") String keyword,
            // 浏览器未提供 pageNum 时，默认前往第一页
            @RequestParam(value="pageNum", defaultValue="1") Integer pageNum,
            // 浏览器未提供 pageSize 时，默认每页显示 5 条记录
            @RequestParam(value="pageSize", defaultValue="5") Integer pageSize,
            ModelMap modelMap ) {
        // 查询得到分页数据
        PageInfo<Admin> pageInfo = adminService.getAdminPage(keyword, pageNum, pageSize);
        // 将分页数据存入模型
        modelMap.addAttribute(CrowdConstant.ATTR_NAME_PAGE_INFO, pageInfo);
        return "admin-page";
    }

    @RequestMapping("/admin/do/logout.html")
    public String doLogout(HttpSession session) {
        // 强制 Session 失效
        session.invalidate();
        return "redirect:/admin/to/login/page.html";
    }

    @RequestMapping("/admin/do/login.html")
    public String doLogin(@RequestParam("loginAcct") String loginAcct,
                          @RequestParam("userPswd") String userPswd,
                          HttpSession session ) {
        // 调用 Service 方法执行登录检查
        // 这个方法如果能够返回 admin 对象说明登录成功，如果账号、密码不正确则会抛出 异常
        Admin admin = adminService.getAdminByLoginAcct(loginAcct, userPswd);
        // 将登录成功返回的 admin 对象存入 Session 域
        session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN, admin);
        return "redirect:/admin/to/main/page.html";
    }

    @PreFilter(value="filterObject%2==0")
    @ResponseBody
    @RequestMapping("/admin/test/pre/filter.json")
    public ResultEntity<List<Integer>> saveList(@RequestBody List<Integer> valueList) {
        return ResultEntity.successWithData(valueList);
    }
}
