package org.csu.mypetstore.persistence;

import org.csu.mypetstore.domain.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderMapper {
    List<Order> getOrdersByUsername(String username);

    List<Order> getOrders();

    Order getOrder(int orderId);

    void insertOrder(Order order);

    void insertOrderStatus(Order order);

    //删除单个订单
    void removeOrder(int orderId);
    void removeLineItem(int orderId);
    void removeOrderStatus(int orderId);
}
