/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.kdg.repaircafe.backend.persistence.impl;

import be.kdg.repaircafe.backend.dom.users.User;
import be.kdg.repaircafe.backend.persistence.api.UserRepositoryCustom;
import be.kdg.repaircafe.backend.services.exceptions.UserServiceException;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * @author wouter
 */
@Repository("userRepository")
public class UserRepositoryImpl implements UserRepositoryCustom
{
    // https://programmeren3-repaircafe.rhcloud.com/repair-cafe-applicatie/repair-cafe-backend/backend-persistence-layer/
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<User> findUsersByRole(Class c)
    {
        TypedQuery<User> q = em.createQuery("SELECT u from User u where TYPE(u.roles) = " + c.getSimpleName(), User.class);
        return q.getResultList();
    }

    @Override
    // TODO: Replace UserServiceException by new UserDAOExceptions or something
    public Integer addUser(User user) throws UserServiceException
    {
        final TypedQuery<User> q = em.createNamedQuery("User.findByUsername", User.class);
        q.setParameter("username", user.getUsername());
        if (!q.getResultList().isEmpty())
        {
            throw new UserServiceException("User " + user.getUsername() + " already exists");
        }
        final Session session = em.unwrap(Session.class);
        session.saveOrUpdate(user);
        return user.getId();
    }
}
