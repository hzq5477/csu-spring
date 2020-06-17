package org.csu.mypetstore.service;

import org.csu.mypetstore.domain.Manager;
import org.csu.mypetstore.persistence.ManagerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagerService {
    @Autowired
    private ManagerMapper managerMapper;

    public Manager getManager(String password){
        return managerMapper.getManagerByPassword(password);
    }

    public Manager getManager(String username, String password){
        Manager manager = new Manager();
        manager.setUsername(username);
        manager.setPassword(password);
        return  managerMapper.getManagerByUsernameAndPassword(manager);
    }

    public void updatePassword(Manager manager){
        if(manager.getPassword() != null && manager.getPassword().length() > 0){
            managerMapper.updatePassword(manager);
        }
    }
}
