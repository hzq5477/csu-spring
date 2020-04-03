package org.csu.mypetstore.controller;

import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.domain.Cart;
import org.csu.mypetstore.domain.Order;
import org.csu.mypetstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.annotation.SessionScope;


import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
//@author：hzq
//@note：
@Controller
@SessionScope
@RequestMapping("/order/")
public class OrderController {
    private static final List<String> CARD_TYPE_LIST;

    @Autowired
    private OrderService orderService;


    private Order order = new Order();
    private boolean shippingAddressRequired;
    private boolean confirmed;
    private List<Order> orderList;

    static {
        List<String> cardList = new ArrayList<String>();
        cardList.add("Visa");
        cardList.add("MasterCard");
        cardList.add("American Express");
        CARD_TYPE_LIST = Collections.unmodifiableList(cardList);
    }

    public int getOrderId() {
        return order.getOrderId();
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public boolean isShippingAddressRequired() {
        return shippingAddressRequired;
    }

    public void setShippingAddressRequired(boolean shippingAddressRequired) {
        this.shippingAddressRequired = shippingAddressRequired;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;

    }

    public List<String> getCreditCardTypes() {
        return CARD_TYPE_LIST;
    }
    //continue之后
    public List<Order> getOrderList() {
        return orderList;
    }

    //查看该账户的所有订单
    @GetMapping("listOrders")
    public String listOrders( HttpSession session) {

        /*Account account = (Account) model.getAttribute("");*/
        Account account = (Account) session.getAttribute("account");
        orderList = orderService.getOrdersByUsername(account.getUsername());
        return "order/ListOrders";
    }
    //点击checkout后跳转新订单页面
    @GetMapping("newOrderForm")
    public String newOrderForm(Model model,HttpSession session) {
        Account account = (Account) session.getAttribute("account");
        Cart cart =(Cart) session.getAttribute("cart");
  /*      boolean authenticated= (boolean) model.getAttribute("authenticated");*/
        clear();
        if (account == null ) {//先登录
            String msg = "请先登录";
            model.addAttribute("msg", msg);
            return "account/signon";
        } else if (cart != null) {
            order.initOrder(account, cart);
            return  "order/NewOrderForm";
        } else {
            String msg = "因为找不到您的购物车，无法创建订单";
            model.addAttribute("msg", msg);
            return "common/error";
        }
    }
    //新订单表newOrderForm的continue按钮，注意是post方法，要从从前端传入表单值
    @PostMapping("newOrder")
    public String newOrder(Model model,HttpSession session) {

        if (shippingAddressRequired) {
            shippingAddressRequired = false;
            return "order/ShippingForm";//如果需要邮寄的话要写填邮寄地址什么的
        } else if (!isConfirmed()) {
            return "order/ConfirmOrder";//如果没有确认转到确认订单进行确认
        } else if (getOrder() != null) {//确认订单

            orderService.insertOrder(order);

            Cart cart =(Cart)session.getAttribute("cart") ;
            model.addAttribute("cart",null);
            String msg="您的订单已经提交";
            model.addAttribute("msg",msg);


            return "order/ViewOrder";//转到查看刚刚完成的订单
        } else {
            String msg="错误！";
            model.addAttribute("msg",msg);
            return "common/error";
        }
    }
    //查看我的订单
    @GetMapping("viewOrder")
    public String viewOrder(Model model,HttpSession session ) {
        Account account = (Account) session.getAttribute("account");

        order = orderService.getOrder(order.getOrderId());

        if (account.getUsername().equals(order.getUsername())) {
            return "order/ViewOrder";
        } else {
            order = null;
            String msg="错误！账户订单不匹配！";
            model.addAttribute("msg",msg);
            return "common/error";
        }
    }

    public void clear() {
        order = new Order();
        shippingAddressRequired = false;
        confirmed = false;
        orderList = null;
    }
}
