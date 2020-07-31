module com.mastrHyperion98 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.mastrHyperion98 to javafx.fxml;
    opens com.mastrHyperion98.struct to javafx.base;
    exports com.mastrHyperion98;
}