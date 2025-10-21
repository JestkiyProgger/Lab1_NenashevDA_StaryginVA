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
        em.persist(user);
    }

    public void updateUser(User user) {
        em.merge(user);
    }

    public void deleteUser(Integer id) {
        User user = em.find(User.class, id);
        if (user != null) {
            em.remove(user);
        }
    }

    // Дополнительные методы для вашей бизнес-логики
    public List<User> getUsersByAgeRange(Integer minAge, Integer maxAge) {
        return em.createQuery("SELECT u FROM User u WHERE u.age BETWEEN :minAge AND :maxAge", User.class)
                .setParameter("minAge", minAge.longValue())
                .setParameter("maxAge", maxAge.longValue())
                .getResultList();
    }

    public User getUserByEmail(String email) {
        try {
            return em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (Exception e) {
            return null; // или обработать исключение
        }
    }

    public List<User> getUsersByName(String name) {
        return em.createQuery("SELECT u FROM User u WHERE u.name LIKE :name", User.class)
                .setParameter("name", "%" + name + "%")
                .getResultList();
    }
}