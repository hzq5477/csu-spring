package org.csu.mypetstore.persistence;

import org.csu.mypetstore.domain.Category;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryMapper {
    //para:宠物种类id
    //result：宠物种类
    Category getCategory(String categoryId);
    //result:所有宠物种类
    //author:hzq
    List<Category> getCategoryList();
}
