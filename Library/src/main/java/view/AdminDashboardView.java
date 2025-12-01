package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class AdminDashboardView {

    private final Button manageBooksButton;
    private final Button manageUsersButton;

    public AdminDashboardView(Stage primaryStage) {
        primaryStage.setTitle("Admin Dashboard");
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        Label label = new Label("Administrator Panel");
        label.setFont(new Font("Arial", 24));

        manageBooksButton = new Button("Manage Books");
        manageBooksButton.setStyle("-fx-font-size: 16px; -fx-min-width: 200px; -fx-min-height: 50px;");

        manageUsersButton = new Button("Manage Users");
        manageUsersButton.setStyle("-fx-font-size: 16px; -fx-min-width: 200px; -fx-min-height: 50px;");

        layout.getChildren().addAll(label, manageBooksButton, manageUsersButton);
        Scene scene = new Scene(layout, 400,300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void addManageBooksListener(EventHandler<ActionEvent> listener)
    {
        manageBooksButton.setOnAction(listener);
    }

    public void addManageUsersListener(EventHandler<ActionEvent> listener)
    {
        manageUsersButton.setOnAction(listener);
    }
}
