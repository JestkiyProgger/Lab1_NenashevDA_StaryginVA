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
import java.util.Date;
import java.util.List;

@Named
@SessionScoped
public class TaskBean implements Serializable {

    @Inject
    private TaskService taskService;

    @Inject
    private UserService userService;

    private Task task = new Task();
    private List<Task> tasks;
    private List<Task> tasksByUser;
    private Integer selectedUserId;
    private String message;

    @PostConstruct
    public void init() {
        loadTasks();
    }

    public void loadTasks() {
        tasks = taskService.getAllTasks();
    }

    public String addTask() {
        User user = userService.getUserById(selectedUserId);
        task.setUser(user);
        task.setCreatedDate(new Date());

        // Если completed не установлен, устанавливаем false
        if (task.getCompleted() == null) {
            task.setCompleted(false);
        }

        taskService.addTask(task);
        loadTasks();
        task = new Task();
        message = "Задача успешно добавлена!";
        return "list?faces-redirect=true";
    }

    public String deleteTask(Integer id) {
        taskService.deleteTask(id);
        loadTasks();
        message = "Задача успешно удалена!";
        return "list?faces-redirect=true";
    }

    public String editTask(Integer id) {
        this.task = taskService.getTaskById(id);
        this.selectedUserId = task.getUser().getId();
        return "edit?faces-redirect=true";
    }

    public String updateTask() {
        User user = userService.getUserById(selectedUserId);
        task.setUser(user);
        taskService.updateTask(task);
        loadTasks();
        task = new Task();
        message = "Задача успешно обновлена!";
        return "list?faces-redirect=true";
    }

    public String viewTasksByUser(Integer userId) {
        this.tasksByUser = taskService.getTasksByUser(userId);
        return "tasksByUser?faces-redirect=true";
    }

    public String toggleTaskCompletion(Integer taskId) {
        Task task = taskService.getTaskById(taskId);
        if (task != null) {
            task.setCompleted(!task.getCompleted());
            taskService.updateTask(task);
            loadTasks();
            message = "Статус задачи обновлен!";
        }
        return null; // Остаемся на той же странице
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public List<Task> getTasksByUser() {
        return tasksByUser;
    }

    public Integer getSelectedUserId() {
        return selectedUserId;
    }

    public void setSelectedUserId(Integer selectedUserId) {
        this.selectedUserId = selectedUserId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Методы для фильтрации задач
    public List<Task> getCompletedTasks() {
        return taskService.getCompletedTasks();
    }

    public List<Task> getPendingTasks() {
        return taskService.getPendingTasks();
    }

    public long getTaskCountByUser(Integer userId) {
        return taskService.getTaskCountByUser(userId);
    }
}
