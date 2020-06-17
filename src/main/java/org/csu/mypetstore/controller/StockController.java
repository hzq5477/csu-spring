package org.csu.mypetstore.controller;

import org.csu.mypetstore.domain.Item;
import org.csu.mypetstore.domain.Product;
import org.csu.mypetstore.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.text.normalizer.NormalizerBase;

import java.util.List;

@Controller
@RequestMapping("/M_stock/")
public class StockController {
    @Autowired
    private CatalogService catalogService;

    @GetMapping("stocks")
    public String stockForm(Model model) {
            List<Item> itemList = catalogService.getItemList();
            model.addAttribute("itemList", itemList);
            return "/M_stock/stocks";
    }
    @GetMapping("rack")
    public String rackForm(Model model) {
        List<Item> itemList = catalogService.getItemList();
        model.addAttribute("itemList", itemList);
        return "/M_rack/rack";
    }
    @GetMapping("addStock")
    public  String addItemStock(String workingItemId, Model model){
        return "/M_stock/addStock";
    }
}
