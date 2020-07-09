package scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.text.Text;

public class LoginController {
    private static struct.Controller myController;
    @FXML
    private Text actiontarget;
    @FXML private PasswordField passwordField;

    @FXML protected void handleSubmitButtonAction(ActionEvent event) {
        actiontarget.setText(""+myController.auth(passwordField.getText()));
    }
    public static void setController(struct.Controller _controller){
        myController = _controller;
    }
}