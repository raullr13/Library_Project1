package service.user;

import model.User;
import model.validator.Notification;

import java.util.List;

public interface UserService {
    List<User> findAll();
    Notification<Boolean> save(String username, String password, String role);
    boolean delete(Long id);
}
