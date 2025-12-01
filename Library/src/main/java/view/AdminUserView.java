package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import view.model.UserDTO;

import java.util.List;


public class AdminUserView {

    private TableView<UserDTO> userTableView;
    private final ObservableList<UserDTO> usersObservableList;

    private TextField usernameField;
    private PasswordField passwordField;
    private ComboBox<String> roleComboBox;

    private Button addUserButton;
    private Button deleteUserButton;

    public AdminUserView(Stage stage, List<UserDTO> users) {
        stage.setTitle("User Management");
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25,25,25,25));

        usersObservableList = FXCollections.observableArrayList(users);
        initTableView(gridPane);
        initSaveOptions(gridPane);

        Scene scene = new Scene(gridPane, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    private void initTableView(GridPane gridPane) {
        userTableView = new TableView<>();
        userTableView.setPlaceholder(new Label("No users found"));

        TableColumn<UserDTO, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<UserDTO, String> roleColumn = new TableColumn<>("Role");
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

        userTableView.getColumns().addAll(usernameColumn, roleColumn);
        userTableView.setItems(usersObservableList);
        userTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        gridPane.add(userTableView, 0,0,4,1);
    }

    private void initSaveOptions(GridPane gridPane) {
        usernameField = new TextField();
        usernameField.setPromptText("Username/Email");
        gridPane.add(usernameField, 0,1);

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        gridPane.add(passwordField, 1,1);

        roleComboBox = new ComboBox<>();
        roleComboBox.getItems().addAll("Admin", "Employee", "Customer");
        roleComboBox.getSelectionModel().selectFirst();
        gridPane.add(roleComboBox, 2,1);

        addUserButton = new Button("Add User");
        gridPane.add(addUserButton, 3,1);

        deleteUserButton = new Button("Delete User");
        gridPane.add(deleteUserButton, 0,2);
    }

    public String getUsername()
    {
        return usernameField.getText();
    }

    public String getPassword()
    {
        return passwordField.getText();
    }

    public UserDTO getSelectedUser()
    {
        return userTableView.getSelectionModel().getSelectedItem();
    }

    public void addUserToObservableList(UserDTO user)
    {
        this.usersObservableList.add(user);
    }

    public void removeUserFromObservableList(UserDTO user)
    {
        this.usersObservableList.remove(user);
    }

    public void addAddUserListener(EventHandler<ActionEvent> actionListener)
    {
        addUserButton.setOnAction(actionListener);
    }

    public void addDeleteUserListener(EventHandler<ActionEvent> actionListener)
    {
        deleteUserButton.setOnAction(actionListener);
    }

    public void displayAlertMessage(String title, String header, String content)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public String getRole() {
        return roleComboBox.getValue();
    }
}
