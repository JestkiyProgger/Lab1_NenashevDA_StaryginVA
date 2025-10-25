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
import java.time.LocalDate;
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
        if (user == null) {
            message = "Пользователь не найден";
            return null;
        }
        task.setUser(user);
        task.setCreatedDate(LocalDate.now());

        if (task.getCompleted() == null) {
            task.setCompleted(false);
        }

        taskService.addTask(task);
        loadTasks();
        task = new Task();
        message = "Задача добавлена";
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
        return null;
    }

    public void loadTasksByUserId() {
        try {
            if (selectedUserId != null) {
                this.tasksByUser = taskService.getTasksByUser(selectedUserId);

                User user = userService.getUserById(selectedUserId);
                if (user != null) {
                    this.message = "Задачи пользователя: " + user.getName();
                }
            } else {
                this.message = "ID пользователя не указан";
            }
        } catch (Exception e) {
            message = "Ошибка при загрузке задач: " + e.getMessage();
        }
    }

    public void loadTaskById() {
        try {
            if (task.getId() != null) {
                Task loadedTask = taskService.getTaskById(task.getId());
                if (loadedTask != null) {
                    this.task.setTitle(loadedTask.getTitle());
                    this.task.setCompleted(loadedTask.getCompleted());
                    this.task.setCreatedDate(loadedTask.getCreatedDate());

                    if (loadedTask.getUser() != null) {
                        this.selectedUserId = loadedTask.getUser().getId();
                    }
                } else {
                    message = "Задача не найдена!";
                }
            }
        } catch (Exception e) {
            message = "Ошибка при загрузке задачи: " + e.getMessage();
        }
    }

    public String cancel() {
        task = new Task();
        selectedUserId = null;
        message = null;
        return "list?faces-redirect=true";
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
