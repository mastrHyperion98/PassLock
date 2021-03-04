package com.mastrHyperion98;
/*
Created by: Steven Smith
Created for: PasswordManager project @ https://github.com/mastrHyperion98/PasswordManager

Project under the GPL3 license.
Controls the logic flow of the TableView fxml view.
 */
import com.mastrHyperion98.struct.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class TableViewController implements Initializable {

    @FXML
    private TableView<Data> data;
    @FXML
    private TableColumn<Object, Object> colId;
    @FXML
    private TableColumn<Object, Object> colDomain;
    @FXML
    private TableColumn<Object, Object> colEmail;
    @FXML
    private TableColumn<Object, Object> colUsername;
    @FXML
    private TableColumn<Object, Object> colPassword;
    private ObservableList<Data> entryObservableList = FXCollections.observableArrayList();
    private Controller myController;
    // to compensate for lack of precision in float arithmetic
    private static final double EPSILON = 0.0000005;
    @FXML
    void onOpenDialog(ActionEvent event) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddEntry.fxml"));
            Parent parent = fxmlLoader.load();
            AddEntryDialogController dialogController = fxmlLoader.<AddEntryDialogController>getController();
            dialogController.setAppMainObservableList(entryObservableList);
            dialogController.setController(myController);
            Scene scene = new Scene(parent, 550, 300);
            Stage stage = new Stage();
            stage.setTitle("PassLock: Add");
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.getIcons().add(new Image(App.class.getResourceAsStream("icon/icons8-lock-64.png")));
            stage.showAndWait();
    }

    // closes the application
    @FXML
    void onExit(ActionEvent event) throws IOException {
    }

    @FXML
    void onExport(ActionEvent event){
        // Select where to save the file to
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter fileExtensions =
                new FileChooser.ExtensionFilter("passpad", "*.pss");
        fileChooser.getExtensionFilters().add(fileExtensions);
        File selectedFile = fileChooser.showSaveDialog(new Stage());

        final int lines = entryObservableList.size();
        final ObservableList<Data> document = entryObservableList;
        final List<SerializableData> list = new LinkedList<SerializableData>();
        // Create a new task
        final Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                for(int i = 0; i <lines; i++){
                    Data data = entryObservableList.get(i);
                    list.add(new SerializableData(data));
                    updateProgress(i+1, lines);
                }

                Passlock passlock = new Passlock(list);

                try {
                    // write
                    passlock.write("Panther", selectedFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }
        };
        final Thread thread = new Thread(task, "task-thread");
        thread.setDaemon(true);
        thread.start();
    }

    @FXML
    void onImport(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter fileExtensions =
                new FileChooser.ExtensionFilter("CSV", "*.csv");
        fileChooser.getExtensionFilters().add(fileExtensions);
        File selectedFile = fileChooser.showOpenDialog(new Stage());
/*
        try {
            // Read the file into an CSV document
            if(selectedFile != null) {
                // create a task to run in parallel
                final Task<Void> task = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        for (int line = 0; line < lines; line++) {
                            String domain = documentBody[line][0];
                            String email = documentBody[line][1];
                            String username = documentBody[line][2];
                            String password = documentBody[line][3];
                            try {
                                myController.getSession().writeEntry(domain, email, username, AES.encrypt(password));
                                Data data = myController.getSession().fetchEntry(domain);
                                // TODO: Create a thread safe access to entryObservableList or lock Add, edit and delete
                                entryObservableList.add(data);
                            } catch (SQLException ignored) {
                            }
                            updateProgress(line+1, lines);
                        }
                        data.refresh();
                        return null;
                    }
                };
                // bind the progress of the progress bar to task
                progressBar.progressProperty().bind(task.progressProperty());
                // color the bar green when the work is complete.
                final Thread thread = new Thread(task, "task-thread");
                thread.setDaemon(true);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    @FXML
    void onDeleteEntry(ActionEvent event){
        Data item = data.getSelectionModel().getSelectedItem();
        if (item == null)
            return;
        boolean success = myController.getSession().deleteEntry(item.getId());
        if (success)
            data.getItems().removeAll(item);
    }

    @FXML
    void onUpdateDialog(ActionEvent event) throws IOException {
        Data item = data.getSelectionModel().getSelectedItem();
        if (item == null)
            return;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EditEntry.fxml"));
        Parent parent = fxmlLoader.load();
        UpdateEntryController dialogController = fxmlLoader.<UpdateEntryController>getController();
        dialogController.setController(myController);
        dialogController.setPassword(item);
        Scene scene = new Scene(parent, 550, 300);
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setTitle("PassLock: Edit");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.getIcons().add(new Image(App.class.getResourceAsStream("icon/icons8-lock-64.png")));
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
        item.setOnAction(event -> {
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
            System.out.println("ERROR WITH CONTROLLER");
        }
    }

    private void showError(String error){
        FXMLLoader _fxmlLoader = new FXMLLoader(App.class.getResource("Notification.fxml"));
        Parent parent = null;
        Stage popup_stage = new Stage();
        Scene popup_scene;
        try {

            parent = _fxmlLoader.load();
            NotificationController viewController = _fxmlLoader.<NotificationController>getController();
            viewController.setDatabaseController(myController);
            viewController.setMessage(error);
            viewController.setCurrent_stage(popup_stage);
            popup_scene = new Scene(parent, 600, 250);
            popup_stage.setResizable(false);
            popup_stage.setTitle("PassLock: Error");
            popup_stage.initModality(Modality.APPLICATION_MODAL);
            popup_stage.setScene(popup_scene);
            popup_stage.getIcons().add(new Image(App.class.getResourceAsStream("icon/icons8-lock-64.png")));
            popup_stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
