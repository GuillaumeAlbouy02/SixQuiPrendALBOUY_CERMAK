module com.example.sixquiprendalbouy_cermak {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.example.sixquiprendalbouy_cermak to javafx.fxml;
    exports com.example.sixquiprendalbouy_cermak;
}