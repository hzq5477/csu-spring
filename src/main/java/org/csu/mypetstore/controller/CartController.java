package org.csu.mypetstore.controller;

import org.csu.mypetstore.domain.Cart;
import org.csu.mypetstore.domain.CartItem;
import org.csu.mypetstore.domain.Item;
import org.csu.mypetstore.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.SessionScope;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;

@Controller
@SessionScope
@RequestMapping("/cart/")
//把cart放进session里让orderController能取到
@SessionAttributes({"cart"})

public class CartController {

    @Autowired
    private CatalogService catalogService;
    @Autowired
    private Cart cart;

    @GetMapping("viewCart")
    public String viewCart(Model model){
        model.addAttribute("cart",cart);
        return "cart/cart";
    }

    @GetMapping("addItemToCart")
    public String addItemToCart(String workingItemId, Model model){
        Item item = catalogService.getItem(workingItemId);
        if (item.getQuantity()<=0){
            model.addAttribute("msg","This item is sold out.Please view other items.");
            return "common/error";
        }
        if(cart.containsItemId(workingItemId)){
            cart.incrementQuantityByItemId(workingItemId);
        }else{
            boolean isInStock = catalogService.isItemInStock(workingItemId);
//            Item item = catalogService.getItem(workingItemId);
            cart.addItem(item,isInStock);
        }
        model.addAttribute("cart",cart);
        return "cart/cart";
    }

    @GetMapping("removeItemFromCart")
    public String removeItemFromCart(String workingItemId, Model model){
        Item item = cart.removeItemById(workingItemId);
        model.addAttribute("cart",cart);
        if(item == null){
            model.addAttribute("msg", "Attempted to remove null CartItem from Cart.");
            return "common/error";
        }else{
            return "cart/cart";
        }
    }

//    @PostMapping("updateCartQuantities")
//    public String updateCartQuantities(HttpServletRequest request, Model model){
//        Iterator<CartItem> cartItems = cart.getAllCartItems();
//        while (cartItems.hasNext()){
//            CartItem cartItem = cartItems.next();
//            String itemId = cartItem.getItem().getItemId();
//            try{
//                int quantity = Integer.parseInt(request.getParameter(itemId));
//                cart.setQuantityByItemId(itemId,quantity);
//                if(quantity < 1){
//                    cartItems.remove();
//                }
//            }catch (Exception e){
//
//            }
//        }
//        model.addAttribute("cart",cart);
//        return "cart/cart";
//    }

}
