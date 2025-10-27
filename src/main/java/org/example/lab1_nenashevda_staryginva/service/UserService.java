package org.example.lab1_nenashevda_staryginva.service;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.lab1_nenashevda_staryginva.model.User;

import java.util.List;

@Stateless
public class UserService {

    @PersistenceContext(unitName = "userTask")
    private EntityManager em;

    public List<User> getAllUsers() {
        return em.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    public User getUserById(Integer id) {
        return em.find(User.class, id);
    }

    public void addUser(User user) {
        try {
            em.persist(user);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при создании пользователя: " + e.getMessage(), e);
        }
    }

    public void updateUser(User user) {
        try {
            em.merge(user);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при обновлении пользователя: " + e.getMessage(), e);
        }
    }

    public void deleteUser(Integer id) {
        User user = em.find(User.class, id);
        if (user != null) {
            em.remove(user);
        }
    }
}