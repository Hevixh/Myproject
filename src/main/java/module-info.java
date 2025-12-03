module org.example.myproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome;

    exports org.example.myproject;

    opens org.example.myproject to javafx.fxml;
}
