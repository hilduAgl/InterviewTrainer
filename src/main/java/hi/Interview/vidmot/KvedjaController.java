package hi.Interview.vidmot;
import javafx.application.Platform;
import javafx.fxml.FXML;

/******************************************************************************
 *  Nafn    : Hildur Agla
 *  T-póstur: hao37@hi.is
 *
 *  Lýsing  : Stýring fyrir kveðjuna. Birtir kveðju áður en notandinn hættir.
 *
 *
 *****************************************************************************/

public class KvedjaController {
    /**
     * Hættir keyrslu forritsins
     */
    @FXML
    public void onKvedja() {
        Platform.exit();
        System.exit(0);
    }
}
