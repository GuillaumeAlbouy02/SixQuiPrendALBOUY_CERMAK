module com.example.sixquiprendalbouy_cermak {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;


    opens com.example.sixquiprendalbouy_cermak to javafx.fxml;
    exports com.example.sixquiprendalbouy_cermak;
}