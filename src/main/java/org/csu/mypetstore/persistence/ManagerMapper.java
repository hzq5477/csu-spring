package org.csu.mypetstore.persistence;


import org.csu.mypetstore.domain.Manager;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerMapper {
    Manager getManagerByUsernameAndPassword(Manager manager);

    Manager getManagerByPassword(String password);

    Manager getInfoByUsername(String username);

    void updatePassword(Manager manager);

    void updateInfo(String username,String birthday,String sex,String duty);
}
