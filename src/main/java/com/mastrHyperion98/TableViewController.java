package com.mastrHyperion98;

import com.mastrHyperion98.struct.Controller;
import com.mastrHyperion98.struct.Password;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Modality;
import javafx.stage.Stage;


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
    private Controller myController;

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
        if(item==null)
            return;
        boolean success = myController.getSession().deleteEntry(item.getId());
        if(success)
            data.getItems().removeAll(item);
    }

    @FXML
    void onUpdateDialog(ActionEvent event) throws IOException {
        Password item = data.getSelectionModel().getSelectedItem();
        if (item==null)
            return;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EditEntry.fxml"));
        Parent parent = fxmlLoader.load();
        UpdateEntryController dialogController = fxmlLoader.<UpdateEntryController>getController();
        dialogController.setController(myController);
        dialogController.setPassword(item);
        Scene scene = new Scene(parent, 550, 300);
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
        data.refresh();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDomain.setCellValueFactory(new PropertyValueFactory<>("Domain"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("Username"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("Password"));
        data.setItems(entryObservableList);
        data.getSelectionModel().setCellSelectionEnabled(true);

        MenuItem item = new MenuItem("Copy");
        item.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ObservableList<TablePosition> posList = data.getSelectionModel().getSelectedCells();
                int old_r = -1;
                StringBuilder clipboardString = new StringBuilder();
                for (TablePosition p : posList) {
                    int r = p.getRow();
                    int c = p.getColumn();
                    Object cell = data.getColumns().get(c).getCellData(r);
                    if (cell == null)
                        cell = "";
                    if (old_r == r)
                        clipboardString.append('\t');
                    else if (old_r != -1)
                        clipboardString.append('\n');
                    clipboardString.append(cell);
                    old_r = r;
                }
                final ClipboardContent content = new ClipboardContent();
                content.putString(clipboardString.toString());
                Clipboard.getSystemClipboard().setContent(content);
            }
        });
        ContextMenu menu = new ContextMenu();
        menu.getItems().add(item);
        data.setContextMenu(menu);
    }

    public void setController(Controller _controller){
        myController = _controller;
        try {
            entryObservableList.addAll(myController.getSession().fetchEntries());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
