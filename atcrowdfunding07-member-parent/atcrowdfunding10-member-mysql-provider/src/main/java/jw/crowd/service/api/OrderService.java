package jw.crowd.service.api;

import jw.crowd.entity.vo.AddressVO;
import jw.crowd.entity.vo.OrderProjectVO;
import jw.crowd.entity.vo.OrderVO;

import java.util.List;

public interface OrderService {
    OrderProjectVO getOrderProjectVO(Integer projectId, Integer returnId);

    List<AddressVO> getAddressVOList(Integer memberId);

    void saveAddress(AddressVO addressVO);

    void saveOrder(OrderVO orderVO);
}
