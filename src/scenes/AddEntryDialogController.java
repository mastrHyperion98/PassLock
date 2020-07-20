package scenes;
import Encoder.AES;
import javafx.collections.ObservableList;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.scene.Node;
        import javafx.scene.control.TextField;
        import javafx.stage.Stage;
import struct.Password;

import java.sql.SQLException;

public class AddEntryDialogController   {
    @FXML
    private TextField tfDomain;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfUsername;
    @FXML
    private TextField tfPassword;

    private ObservableList<Password> appMainObservableList;
    private struct.Controller myController;

    @FXML
    void btnAddPersonClicked(ActionEvent event) {
        System.out.println("btnAddPersonClicked");
        String domain = tfDomain.getText().trim().toLowerCase();
        String email = tfEmail.getText().trim();
        String username = tfUsername.getText().trim();
        String password = tfPassword.getText().trim();
        Password data = new Password(1, domain, username, email, AES.encrypt(password));

        try {
            myController.getSession().writeEntry(domain,email,username,password);
        } catch (SQLException throwables) {
            System.out.println("ERROR--DUPLICATE");
        }

        appMainObservableList.add(data);

        closeStage(event);
    }

    public void setAppMainObservableList(ObservableList<Password> observableList) {
        this.appMainObservableList = observableList;
    }

    public void setController(struct.Controller _controller){

        myController = _controller;
    }

    private void closeStage(ActionEvent event) {
        Node  source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

}
