module com.example.java_game2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires javafx.graphics;
    requires javafx.base;
    requires com.google.gson;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires java.persistence;
    requires java.naming;

    opens com.example.java_game2 to javafx.fxml;
    exports com.example.java_game2;
    exports com.example.java_game2.Server;
    exports com.example.java_game2.Constructor_objects;
    opens com.example.java_game2.Server to javafx.fxml, com.google.gson;
    opens com.example.java_game2.Constructor_objects to com.google.gson;
    //opens com.example.java_game2.Server to com.google.gson, javafx.fxml;

}