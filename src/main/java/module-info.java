module com.javaflappybird {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;
    requires annotations;

    opens com.javaflappybird to javafx.fxml;
    exports com.javaflappybird;
}