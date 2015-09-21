package be.kdg.repaircafe.backend.persistence.api;

import be.kdg.repaircafe.backend.dom.users.User;
import be.kdg.repaircafe.backend.services.exceptions.UserServiceException;

import java.util.List;

/**
 * Extra interface to support customization of Spring's Data interfaces
 */
public interface UserRepositoryCustom
{
    // https://programmeren3-repaircafe.rhcloud.com/repair-cafe-applicatie/repair-cafe-backend/backend-persistence-layer/

    List<User> findUsersByRole(Class c);

    Integer addUser(User user) throws UserServiceException;
}
