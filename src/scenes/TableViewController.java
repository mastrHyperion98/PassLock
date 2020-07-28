package scenes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import struct.Password;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TableViewController implements Initializable {

    @FXML
    private TableView<Password> data;
    @FXML
    private TableColumn colId;
    @FXML
    private TableColumn colDomain;
    @FXML
    private TableColumn colEmail;
    @FXML
    private TableColumn colUsername;
    @FXML
    private TableColumn colPassword;

    private ObservableList<Password> entryObservableList = FXCollections.observableArrayList();
    private struct.Controller myController;

    @FXML
    void onOpenDialog(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddEntry.fxml"));
        Parent parent = fxmlLoader.load();
        AddEntryDialogController dialogController = fxmlLoader.<AddEntryDialogController>getController();
        dialogController.setAppMainObservableList(entryObservableList);
        dialogController.setController(myController);
        Scene scene = new Scene(parent, 550, 300);
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    void onDeleteEntry(ActionEvent event){
        Password item = data.getSelectionModel().getSelectedItem();
        boolean success = myController.getSession().deleteEntry(item.getId());
        System.out.println("Removed Success: " + success);
        if(success)
            data.getItems().removeAll(item);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDomain.setCellValueFactory(new PropertyValueFactory<>("Domain"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("Username"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("Password"));
        data.setItems(entryObservableList);
    }

    public void setController(struct.Controller _controller){
        myController = _controller;
        try {
            entryObservableList.addAll(myController.getSession().fetchEntries());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
