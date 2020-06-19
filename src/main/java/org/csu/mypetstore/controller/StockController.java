package org.csu.mypetstore.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.csu.mypetstore.domain.Item;
import org.csu.mypetstore.domain.Product;
import org.csu.mypetstore.service.CatalogService;
import org.csu.mypetstore.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sun.text.normalizer.NormalizerBase;

import javax.servlet.http.HttpSession;
import java.util.List;
//将修改的item放入session
@SessionAttributes({"Item"})
@Controller
@RequestMapping("/M_stock/")
public class StockController {
    @Autowired
    private CatalogService catalogService;
    @Autowired
    private ManagerService managerService;

    @GetMapping("stocks")
    public String stockForm(Model model,@RequestParam(value = "start", defaultValue = "1") int start,
                            @RequestParam(value = "size", defaultValue = "10") int size) {
            //分页
            PageHelper.startPage(start, size);
            List<Item> itemList = catalogService.getItemList();
            PageInfo<Item> page = new PageInfo<>(itemList);
            //将所有商品放入pages
            model.addAttribute("pages", page);
            model.addAttribute("itemList", itemList);
            return "/M_stock/stocks";
    }
    @GetMapping("rack")
    public String rackForm(Model model,@RequestParam(value = "start", defaultValue = "1") int start,
                           @RequestParam(value = "size", defaultValue = "10") int size) {
        PageHelper.startPage(start, size);
        List<Item> itemList = catalogService.getItemList();
        PageInfo<Item> page = new PageInfo<>(itemList);
        model.addAttribute("pages", page);
        model.addAttribute("itemList", itemList);
        return "/M_rack/rack";
    }
    @GetMapping("addItemStock")
    public  String addItemStock(String workingItemId, Model model){
        //取出对应的item对象
        Item item = catalogService.getItem(workingItemId);
        model.addAttribute("Item",item);
        return "/M_stock/addStock";
    }

    @PostMapping ("changeItemStock")
    public  String changeItemStock(HttpSession session, String newStock,Model model){
        //设定库存不能小于0
        if (Integer.parseInt(newStock)<0){
            String msg = "Invalid username or password.  Signon failed.";
            model.addAttribute("msg", msg);
            return "/M_stock/addStock";
        }
        //取出对应的item对象
        Item item = (Item) session.getAttribute("Item");
        managerService.updateStock(item,newStock);
        return "redirect:/M_stock/stocks";
    }
}
