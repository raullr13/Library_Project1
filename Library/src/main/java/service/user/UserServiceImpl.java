package service.user;

import model.Role;
import model.User;
import model.builder.UserBuilder;
import model.validator.Notification;
import model.validator.UserValidator;
import repository.security.RightsRolesRepository;
import repository.user.UserRepository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;

    public UserServiceImpl(UserRepository userRepository, RightsRolesRepository rightsRolesRepository) {
        this.userRepository = userRepository;
        this.rightsRolesRepository = rightsRolesRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Notification<Boolean> save(String username, String password, String roleTitle) {
        Notification<Boolean> notification = new Notification<>();

        User user = new UserBuilder().setUsername(username).setPassword(password).build();
        UserValidator validator = new UserValidator(user);

        if(!validator.validate())
        {
            validator.getErrors().forEach(notification::addError);
            notification.setResult(Boolean.FALSE);
            return notification;
        }

        Role role = rightsRolesRepository.findRoleByTitle(roleTitle);
        if(role == null)
        {
            notification.addError("Role not found");
            notification.setResult(Boolean.FALSE);
            return notification;
        }

        user.setPassword(hashPassword(password));
        user.setRoles(Collections.singletonList(role));
        boolean success = userRepository.save(user);
        notification.setResult(success);
        return notification;
    }

    @Override
    public boolean delete(Long id) {
        return userRepository.delete(id);
    }

    private String hashPassword(String password) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for(byte b : hash)
            {
                String hex = Integer.toHexString(0xff & b);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
