/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package be.kdg.repaircafe.backend.persistence.api;

import be.kdg.repaircafe.backend.dom.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author wouter
 */
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User>, UserRepositoryCustom
{
    // https://programmeren3-repaircafe.rhcloud.com/repair-cafe-applicatie/repair-cafe-backend/backend-persistence-layer/

    User findUserByUsername(String username);
}
