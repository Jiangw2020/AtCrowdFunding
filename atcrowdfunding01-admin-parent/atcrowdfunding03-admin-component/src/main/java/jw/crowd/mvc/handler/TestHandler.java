package jw.crowd.mvc.handler;

import jw.crowd.entity.Admin;
import jw.crowd.service.api.AdminService;
import jw.crowd.util.CrowdUtil;
import jw.crowd.util.ResultEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class TestHandler {
    Logger logger = LoggerFactory.getLogger(TestHandler.class);
    @Autowired
    AdminService adminService;
    @ResponseBody
    @RequestMapping("send/array1.html")
    public String testReceiveArrayOne(@RequestParam("array[]") List<Integer> array,HttpServletRequest request){
        boolean b = CrowdUtil.judgeRequestType(request);
        logger.info("是ajax请求："+b);
        for (Integer number:array){
            logger.info("number="+number);
        }
        int i=10/0;
        return "success";
    }
    @ResponseBody
    @RequestMapping("send/array3.json")
    public ResultEntity<Integer> testReceiveArrayThree(@RequestBody List<Integer> array){
        for (Integer number:array){
            logger.info("number="+number);
        }
        return ResultEntity.successWithoutData();
    }
    @RequestMapping("test/ssm.html")
    public String testSsm(ModelMap modelMap, HttpServletRequest request){
        boolean b = CrowdUtil.judgeRequestType(request);
        logger.info("是ajax请求："+b);
        List<Admin> adminList=adminService.getAll();
        modelMap.addAttribute("adminList",adminList);
        int a=10/0;
        return "target";
    }

}
