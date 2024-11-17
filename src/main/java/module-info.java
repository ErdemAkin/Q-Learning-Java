module com.learningway.qlearning {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;

    opens com.learningway.qlearning to javafx.fxml;
    exports com.learningway.qlearning;
    
    opens com.learningway.qlearning.Application.Controller to javafx.fxml;
    exports com.learningway.qlearning.Application.Controller;
    
}
