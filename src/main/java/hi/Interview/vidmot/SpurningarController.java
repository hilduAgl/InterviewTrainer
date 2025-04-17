package hi.Interview.vidmot;

import hi.Interview.vinnsla.Spurningar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.Optional;

/******************************************************************************
 *  Nafn    : Hildur Agla
 *  T-póstur: hao37@hi.is
 *
 *  Lýsing  : Stýring fyrir spurningaglugga. Notandi getur valið flokk og
 *            síðan spurningar úr ListView. Notandi ýtir á svara, til baka til að
 *            komast aftur á velkomin gluggan eða hætta og þá fer hann í kveðjugluggan.
 *****************************************************************************/

public class SpurningarController {

    @FXML
    private TextField fxVinna;
    @FXML
    private TextField fxFyrirtaeki;
    @FXML
    private ListView<String> fxSpurningaflokkar;
    @FXML
    private ListView<String> fxSpurningar;
    @FXML
    private Label fxFjoldaSvar;
    @FXML
    private ListView<String> fxLogger;

    private Spurningar spurningar;
    private String valinSpurning;
    private ObservableList<String> loggListi = FXCollections.observableArrayList();

    /**
     * Upphafsstilla controllerinn eftir að FXML hefur verið hlaðið.
     */
    public void initialize() {
        String jobTitle = fxVinna.getText();

        try {
            spurningar = new Spurningar(jobTitle);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Setja spurningaflokka í listann
        ObservableList<String> flokkar = spurningar.getFlokkar();
        fxSpurningaflokkar.setItems(flokkar);

        // Þegar flokk er valinn, sækja spurningar
        fxSpurningaflokkar.getSelectionModel().selectedIndexProperty().addListener((obs, old, newIndex) -> {
            if (newIndex.intValue() > -1) {
                String valinnFlokkur = fxSpurningaflokkar.getItems().get(newIndex.intValue());
                ObservableList<String> spurningarListi = spurningar.getSpurningaListi(valinnFlokkur);
                fxSpurningar.setItems(spurningarListi);
            }
        });

        // Vista valda spurningu
        fxSpurningar.getSelectionModel().selectedIndexProperty().addListener((obs, old, newIndex) -> {
            if (newIndex.intValue() > -1) {
                valinSpurning = fxSpurningar.getItems().get(newIndex.intValue());
            }
        });

        // Binda fjölda svaraðra við label
        fxFjoldaSvar.textProperty().bind(spurningar.fjoldiSvaradraSpurningaProperty());
        fxLogger.setItems(loggListi);
    }

    public void onTilbaka() {
        ViewSwitcher.switchTo(View.VELKOMINN);
    }

    public void onHaetta() {
        ViewSwitcher.switchTo(View.KVEDJA);
    }

    public void onSvara(ActionEvent actionEvent) {
        if (valinSpurning != null) {
            svara();
        }
    }

    private void svara() {
        SvarDialogController d = new SvarDialogController(valinSpurning, fxFyrirtaeki.getText(), fxVinna.getText());
        Optional<String> utkoma = d.showAndWait();

        utkoma.ifPresent(spurningin -> {
            loggListi.add(spurningin);
            spurningar.aukaFjoldiSvaradra();
        });
    }
}
