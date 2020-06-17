package org.csu.mypetstore.persistence;


import org.csu.mypetstore.domain.Manager;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerMapper {
    Manager getManagerByUsernameAndPassword(Manager manager);

    Manager getManagerByPassword(String password);

    void updatePassword(Manager manager);

}
