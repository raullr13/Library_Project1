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
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import view.model.BookDTO;

import javafx.beans.value.ChangeListener;
import java.util.List;

public class CustomerBookView {
    private TableView<BookDTO> bookTableView;
    private final ObservableList<BookDTO> booksObservableList;
    private Button buyButton;

    public CustomerBookView(Stage primaryStage, List<BookDTO> bookDTOS) {
        primaryStage.setTitle("Customer Book View");
        GridPane gridPane = new GridPane();
        initializeGridPane(gridPane);
        Scene scene = new Scene(gridPane, 720, 480);
        primaryStage.setScene(scene);
        booksObservableList = FXCollections.observableList(bookDTOS);
        initTableView(gridPane);
        initBuyOptions(gridPane);
        primaryStage.show();
    }

    private void initializeGridPane(GridPane gridPane) {
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
    }

    private void initTableView(GridPane gridPane) {
        bookTableView = new TableView<>();
        bookTableView.setPlaceholder(new Label("No Books Available"));

        //titlu
        TableColumn<BookDTO, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        //autor
        TableColumn<BookDTO, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));

        //pret
        TableColumn<BookDTO, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        //stock
        TableColumn<BookDTO, Integer> stockColumn = new TableColumn<>("Stock");
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));

        bookTableView.getColumns().addAll(titleColumn, authorColumn, priceColumn, stockColumn);
        bookTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        bookTableView.setItems(booksObservableList);
        gridPane.add(bookTableView, 0,0,5,1);
    }

    private void initBuyOptions(GridPane gridPane) {
        buyButton = new Button("Buy");
        HBox buyButtonBox = new HBox(10);
        buyButtonBox.setAlignment(Pos.BOTTOM_RIGHT);
        buyButtonBox.getChildren().add(buyButton);
        gridPane.add(buyButtonBox,0,1,5,1);
    }

    public void addBuyButtonListener(EventHandler<ActionEvent> buyButtonListener)
    {
        buyButton.setOnAction(buyButtonListener);
    }

    public void addSelectionTableListener(ChangeListener selectionTableListener)
    {
        bookTableView.getSelectionModel().selectedItemProperty().addListener(selectionTableListener);
    }

    public BookDTO getSelectedBook()
    {
        return bookTableView.getSelectionModel().getSelectedItem();
    }

    public void displayAlertMessage(String title, String header, String content)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void refreshTable()
    {
        bookTableView.refresh();
    }

}
