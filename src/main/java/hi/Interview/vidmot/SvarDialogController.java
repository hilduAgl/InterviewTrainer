package hi.Interview.vidmot;

import hi.Interview.vinnsla.FeedbackService;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.io.IOException;

/******************************************************************************
 *  Nafn    : Hildur Agla
 *  T-póstur: hao37@hi.is
 *
 *  Lýsing  : Dialogur til að skrá svarið og fá feedback við svarinu.
 *
 *
 *****************************************************************************/

public class SvarDialogController extends Dialog<String> {

    //Viðmótshlutir
    @FXML
    private ButtonType fxILagi;
    @FXML
    private TextField fxSvar;
    @FXML
    private Label fxFeedBack;
    @FXML
    private Label fxFjoldaSvar;
    @FXML
    private Label fxSpurningin;

    private final String spurning;

    private final String vinna;

    private final String fyrirtaeki;
    /**
     * Lesa inn notendaviðmótið, setja reglu sem bindur gögn við viðmót og
     * nær gögnum úr dialognum
     * @param s
     */
    public SvarDialogController(String s, String fyrirtaeki, String vinna) {
        spurning = s;
        this.fyrirtaeki = fyrirtaeki;
        this.vinna = vinna;
        setDialogPane(lesaSvarDialog());
        iLagiRegla();
        fxSpurningin.setText(s);
        initialize();
    }

    /**
     * Niðurstöðurnar fengnar úr dialognum
     */
    public void initialize() {
        // Bætum við ResultConverter til að skila spurningunni
        setResultConverter(b -> {
            if (b.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                return spurning;
            } else {
                return null;
            }
        });

    }

    /**
     * Fá feedback við svarinu
     * @param event
     */
    @FXML
    public void onFaFeedBack(ActionEvent event) {
        fxFeedBack.setText("Retrieving feedback...");

        String userAnswer = fxSvar.getText();

        Task<String> task = FeedbackService.feedbackTask(spurning, userAnswer, vinna, fyrirtaeki);

        task.setOnSucceeded(e -> fxFeedBack.setText(task.getValue()));

        task.setOnFailed(e -> {
            fxFeedBack.setText("Error when getting feedback");
            task.getException().printStackTrace(); //  sýnir villu í console
        });

        new Thread(task).start();
        event.consume();
    }



    /**
     * Dialogsviðmót er lesið inn
     * @return
     */
    private DialogPane lesaSvarDialog() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("svar-view.fxml"));
        try {
            // controller er settur sem þessi hlutur
            fxmlLoader.setController(this);
            return fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * Regla búin til um hvenær Í lagi hnappurinn á að vera óvirkur/virkur
     */
    private void iLagiRegla() {
        // fletta upp í lagi hnappnum út frá hnappategund
        Node iLagi = getDialogPane().lookupButton(fxILagi);
        // setja reglu um hvenær í lagi hnappur er virkur
        iLagi.disableProperty()
                .bind(fxSvar.textProperty().isEmpty());
    }

}
