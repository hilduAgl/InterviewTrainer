package hi.Interview.vidmot;

/******************************************************************************
 *  Nafn    : Hildur Agla
 *  T-póstur: hao37@hi.is
 *
 *  Lýsing  : Enum sem geymir fxml skrárnar sem ViewSwitcher notar til að skipta
 *            milli glugga.
 *
 *
 *****************************************************************************/

public enum View {
    VELKOMINN("velkominn-view.fxml"),
    SPURNINGAR("spurningar-view.fxml"),
    KVEDJA("kvedja-view.fxml");

    private String fileName;

    View(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}

