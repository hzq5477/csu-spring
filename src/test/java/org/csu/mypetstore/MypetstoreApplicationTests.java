package org.csu.mypetstore;

import org.csu.mypetstore.domain.Category;
import org.csu.mypetstore.domain.Product;
import org.csu.mypetstore.service.CatalogService;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@MapperScan("org.csu.mypetstore.persistence")
@SpringBootTest
class MypetstoreApplicationTests {
    @Autowired
    CatalogService catalogService;
    @Test
    void contextLoads() {
    }
    //test for getCategory
    @Test
    void testCategory(){
         Category birds =catalogService.getCategory("BIRDS");
        System.out.println(birds.getName()+birds.getDescription());
    }
    //test for product
    @Test
    void testProduct(){
        List<Product> products=catalogService.getProductListByCategory("BIRDS");
        System.out.println(products.size());
    }

}
