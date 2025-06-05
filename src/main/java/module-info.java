module com.stresstester.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.compiler;


    opens com.stresstester to javafx.fxml;
    exports com.stresstester;
}