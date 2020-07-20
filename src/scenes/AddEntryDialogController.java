package scenes;
import javafx.collections.ObservableList;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.scene.Node;
        import javafx.scene.control.TextField;
        import javafx.stage.Stage;
import struct.Password;

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

    @FXML
    void btnAddPersonClicked(ActionEvent event) {
        System.out.println("btnAddPersonClicked");
        String domain = tfDomain.getText().trim();
        String email = tfEmail.getText().trim();
        String username = tfUsername.getText().trim();
        String password = tfPassword.getText().trim();
        Password data = new Password(1, domain, username, email, password);
        appMainObservableList.add(data);

        closeStage(event);
    }

    public void setAppMainObservableList(ObservableList<Password> observableList) {
        this.appMainObservableList = observableList;

    }

    private void closeStage(ActionEvent event) {
        Node  source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

}
