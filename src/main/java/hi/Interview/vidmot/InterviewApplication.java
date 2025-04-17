package hi.Interview.vidmot;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class InterviewApplication extends Application {
    @Override
    public void start(Stage stage) {
        var scene = new Scene(new Pane());

        // Setjum senuna sem núverandi senu
        ViewSwitcher.setScene(scene);

        // skiptum yfir í viðmótstré fyrir LOGIN
        ViewSwitcher.switchTo(View.VELKOMINN);

        // tengjum senuna við gluggann
        stage.setScene(scene);

        // sýnum glugggann
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
