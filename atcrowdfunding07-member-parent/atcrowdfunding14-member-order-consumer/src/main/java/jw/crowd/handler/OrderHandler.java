package jw.crowd.handler;

import jw.crowd.api.MySQLRemoteService;
import jw.crowd.entity.vo.OrderProjectVO;
import jw.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class OrderHandler {
    @Autowired
    MySQLRemoteService mySQLRemoteService;
    @RequestMapping("/confirm/return/info/{projectId}/{returnId}")
    public String showReturnConfirmInfo(
            @PathVariable("projectId") Integer projectId,
            @PathVariable("returnId") Integer returnId,
            Model model) {
        ResultEntity<OrderProjectVO> resultEntity = mySQLRemoteService.getOrderProjectVORemote(projectId, returnId);
        if (ResultEntity.SUCCESS.equals(resultEntity.getOperationResult())) {
            OrderProjectVO orderProjectVO = resultEntity.getQueryData();
            model.addAttribute("orderProjectVO", orderProjectVO);
        }
        return "confirm_return";
    }
}
