module hi.Interview.vidmot {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires okhttp3;
    requires jdk.jsobject;
    requires org.json;


    opens hi.Interview.vidmot to javafx.fxml;
    exports hi.Interview.vidmot;
}