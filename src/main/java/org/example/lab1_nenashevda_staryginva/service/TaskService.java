package org.example.lab1_nenashevda_staryginva.service;


import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.example.lab1_nenashevda_staryginva.model.Task;

import java.util.List;

@Stateless
public class TaskService {

    @PersistenceContext(unitName = "userTask")
    private EntityManager em;

    public List<Task> getAllTasks() {
        return em.createQuery("SELECT t FROM Task t", Task.class).getResultList();
    }

    public Task getTaskById(Integer id) {
        return em.find(Task.class, id);
    }

    public void addTask(Task task) {
        em.persist(task);
    }

    public void updateTask(Task task) {
        em.merge(task);
    }

    public void deleteTask(Integer id) {
        Task task = em.find(Task.class, id);
        if (task != null) {
            em.remove(task);
        }
    }

    public List<Task> getTasksByUser(Integer userId) {
        TypedQuery<Task> query = em.createQuery(
                "SELECT t FROM Task t WHERE t.user.id = :userId", Task.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    public long getTaskCountByUser(Integer userId) {
        TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(t) FROM Task t WHERE t.user.id = :userId", Long.class);
        query.setParameter("userId", userId);
        return query.getSingleResult();
    }
}
