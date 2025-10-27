package org.example.lab1_nenashevda_staryginva.web;


import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.example.lab1_nenashevda_staryginva.model.Task;
import org.example.lab1_nenashevda_staryginva.model.User;
import org.example.lab1_nenashevda_staryginva.service.TaskService;
import org.example.lab1_nenashevda_staryginva.service.UserService;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Named
@SessionScoped
public class UserBean implements Serializable {

    @Inject
    private UserService userService;

    @Inject
    private TaskService taskService;

    private User user = new User();
    private List<User> users;
    private String message;

    @PostConstruct
    public void init() {
        loadUsers();
    }

    public void loadUsers() {
        users = userService.getAllUsers();

        if (users != null) {
            users = users.stream()
                    .sorted(Comparator.comparing(User::getId))
                    .collect(Collectors.toList());
        }
    }

    public String addUser() {
        userService.addUser(user);
        loadUsers();
        user = new User();
        message = "Пользователь успешно добавлен!";
        return "list?faces-redirect=true";
    }

    public String deleteUser(Integer id) {
        User userToDelete = userService.getUserById(id);
        if (userToDelete == null) {
            message = "Пользователь не найден";
            return null;
        }

        long taskCount = taskService.getTaskCountByUser(id);
        if (taskCount > 0) {
            message = "Невозможно удалить пользователя: у него есть " + taskCount + " задач";
            return null;
        }

        userService.deleteUser(id);
        loadUsers();
        message = "Пользователь удален";
        return "list?faces-redirect=true";
    }

    public String editUser(Integer id) {
        try {
            this.user = userService.getUserById(id);
            if (this.user != null) {
                return "edit?faces-redirect=true";
            } else {
                message = "Ошибка: пользователь не найден!";
                return null;
            }
        } catch (Exception e) {
            message = "Ошибка при загрузке пользователя: " + e.getMessage();
            return null;
        }
    }

    public String updateUser() {
        try {
            User existingUser = userService.getUserById(user.getId());
            if (existingUser == null) {
                message = "Пользователь не найден!";
                return null;
            }

            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            existingUser.setAge(user.getAge());

            userService.updateUser(existingUser);
            loadUsers();
            user = new User();
            message = "Пользователь успешно обновлен!";
            return "list?faces-redirect=true";

        } catch (Exception e) {
            message = "Ошибка при обновлении пользователя: " + e.getMessage();
            return null;
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getUsers() {
        return users;
    }

    public String getMessage() {
        return message;
    }

    public String getTaskStatus(Task task) {
        if (task == null) return "Нет задач";
        return task.getCompleted() ? "Завершена" : "В работе";
    }

    public String getTaskStatusStyle(Task task) {
        if (task == null) return "";
        return task.getCompleted() ? "color: green;" : "color: orange;";
    }

    public void loadUserById() {
        try {
            if (user.getId() != null) {
                User loadedUser = userService.getUserById(user.getId());
                if (loadedUser != null) {
                    this.user.setName(loadedUser.getName());
                    this.user.setEmail(loadedUser.getEmail());
                    this.user.setAge(loadedUser.getAge());
                } else {
                    message = "Пользователь не найден!";
                }
            }
        } catch (Exception e) {
            message = "Ошибка при загрузке пользователя: " + e.getMessage();
        }
    }

    public String cancel() {
        user = new User();
        message = null;
        return "list?faces-redirect=true";
    }

}
