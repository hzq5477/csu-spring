package org.csu.mypetstore.persistence;

import org.csu.mypetstore.domain.Product;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductMapper {
    //para：宠物种类ID
    //result：该宠物种类的所有商品List
    //author:hzq
    List<Product> getProductListByCategory(String categoryId);
    //para：商品ID
    //result:对应的商品
    //author:hzq
    Product getProduct(String productId);
    //para：商品ID
    //result:对应的商品
    //author:hzq
    List<Product> searchProductList(String keywords);

}
