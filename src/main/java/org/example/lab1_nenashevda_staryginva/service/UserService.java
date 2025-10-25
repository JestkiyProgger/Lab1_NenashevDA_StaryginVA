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

    public List<User> getUsersByAgeRange(Integer minAge, Integer maxAge) {
        return em.createQuery("SELECT u FROM User u WHERE u.age BETWEEN :minAge AND :maxAge", User.class)
                .setParameter("minAge", minAge)
                .setParameter("maxAge", maxAge)
                .getResultList();
    }

    public User getUserByEmail(String email) {
        List<User> users = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                .setParameter("email", email)
                .getResultList();
        return users.isEmpty() ? null : users.get(0);
    }

    public List<User> getUsersByName(String name) {
        return em.createQuery("SELECT u FROM User u WHERE u.name LIKE :name", User.class)
                .setParameter("name", "%" + name + "%")
                .getResultList();
    }
}