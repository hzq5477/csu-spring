package org.csu.mypetstore.service;

import org.csu.mypetstore.domain.Item;
import org.csu.mypetstore.domain.LineItem;
import org.csu.mypetstore.domain.Manager;
import org.csu.mypetstore.domain.Order;
import org.csu.mypetstore.persistence.ItemMapper;
import org.csu.mypetstore.persistence.ManagerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
public class ManagerService {
    @Autowired
    private ManagerMapper managerMapper;

    @Autowired
    private ItemMapper itemMapper;

    public Manager getManager(String password){
        return managerMapper.getManagerByPassword(password);
    }

    public Manager getManager(String username, String password){
        Manager manager = new Manager();
        manager.setUsername(username);
        manager.setPassword(password);
        return  managerMapper.getManagerByUsernameAndPassword(manager);
    }

    public Manager getInfo(String username){
        return managerMapper.getInfoByUsername(username);
    }

    public void updatePassword(Manager manager){
        if(manager.getPassword() != null && manager.getPassword().length() > 0){
            managerMapper.updatePassword(manager);
        }
    }

    public void updateInfo(String username,String birthday,String sex,String duty){
        managerMapper.updateInfo(username,birthday,sex,duty);
    }

    @Transactional
    public void updateStock(Item item,String newStock) {
            String itemId = item.getItemId();
             Integer stock = Integer.parseInt(newStock);
            Map<String, Object> param = new HashMap<String, Object>(2);
            param.put("itemId", itemId);
            param.put("stock", stock);
            itemMapper.updateStock(param);//更新库存
        }
}
