package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.validator.Notification;
import service.user.UserService;
import view.AdminUserView;
import view.model.UserDTO;

public class AdminUserController {

    private final AdminUserView adminUserView;
    private final UserService userService;

    public AdminUserController(AdminUserView adminUserView, UserService userService) {
        this.adminUserView = adminUserView;
        this.userService = userService;

        this.adminUserView.addAddUserListener(new AddUserListener());
        this.adminUserView.addDeleteUserListener(new DeleteUserListener());
    }

    private class AddUserListener implements EventHandler<ActionEvent>
    {

        @Override
        public void handle(ActionEvent event) {
            String username = adminUserView.getUsername();
            String password = adminUserView.getPassword();
            String role = adminUserView.getRole().toLowerCase();

            if(role.equals("admin"))
            {
                role = "administrator";
            }

            Notification<Boolean> notification = userService.save(username, password, role);

            if(notification.hasErrors())
            {
                adminUserView.displayAlertMessage("Error", "Validation Failed", notification.getFormattedErrors());
            }
            else
            {
                adminUserView.displayAlertMessage("Success", "User Added", "User registered successfully");

                UserDTO userDTO = new UserDTO();
                userDTO.setUsername(username);
                userDTO.setRole(role);
                adminUserView.addUserToObservableList(userDTO);
            }
        }
    }

    private class DeleteUserListener implements EventHandler<ActionEvent>
    {

        @Override
        public void handle(ActionEvent event) {
            UserDTO selectedUser = adminUserView.getSelectedUser();

            if(selectedUser != null)
            {
                boolean success = userService.delete(selectedUser.getId());
                if(success)
                {
                    adminUserView.removeUserFromObservableList(selectedUser);
                    adminUserView.displayAlertMessage("Success", "User Deleted", "User deleted successfully");
                }
                else
                {
                    adminUserView.displayAlertMessage("Error", "Deletion Failed", "Could not delete user");
                }
            }
            else
            {
                adminUserView.displayAlertMessage("Error", "No selection", "Select a user first");
            }
        }
    }

}
