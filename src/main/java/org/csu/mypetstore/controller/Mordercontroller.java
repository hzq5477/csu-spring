package org.csu.mypetstore.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.csu.mypetstore.domain.Order;
import org.csu.mypetstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;

@Controller
@RequestMapping("/M_order/")
@SessionAttributes({"order"})
public class Mordercontroller {
    @Autowired
    private OrderService orderService;

    @GetMapping("order")
    public String stockForm(Model model, @RequestParam(value = "start", defaultValue = "1") int start,
                            @RequestParam(value = "size", defaultValue = "10") int size) {
        //分页
        PageHelper.startPage(start, size);
        List<Order> orderList = orderService.getOrders();
        PageInfo<Order> page = new PageInfo<>(orderList);
        //将所有商品放入pages
        model.addAttribute("pages", page);
        model.addAttribute("orderList", orderList);
        return "/M_order/order";
    }

    //管理员查看一个具体的订单
    @GetMapping("/viewOrderDetail")
    public String viewOrderDetail(@RequestParam("orderId") int orderId, Model model) {
        Order order = orderService.getOrder(orderId);
        model.addAttribute("order", order);
        return "/M_order/orderDetail";
    }
    //删除订单
    @GetMapping("/orderRemove")
    public String orderRemove(@RequestParam("orderID") int orderId) {
        orderService.removeOrder(orderId);
        return "redirect:/M_order/order";
    }
}
