package hi.Interview.vinnsla;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/******************************************************************************
 *  Nafn    : Hildur Agla
 *  T-póstur: hao37@hi.is
 *
 *  Lýsing  : Vinnsluklasi fyrir spurningar. Geymir spurningar innan spurningaflokka.
 *            Hefur lista fyrir flokka og spurninga.
 *
 *
 *****************************************************************************/

public class Spurningar {
    private final ObservableList<String> flokkar;
    private final Map<String, ObservableList<String>> spurningar;
    private final SimpleStringProperty fjoldiSvaradraSpurninga;

    public Spurningar(String jobTitle) throws IOException {
        this.flokkar = FXCollections.observableArrayList();
        this.spurningar = new HashMap<>();
        this.fjoldiSvaradraSpurninga = new SimpleStringProperty("0");

        // Skilgreina flokka
        addFlokkur("General");
        addFlokkur("Technical");
        addFlokkur("Personal");
        addFlokkur("Strengths & Weaknesses");
        addFlokkur("Teamwork");
        addFlokkur("Motivation");
        addFlokkur("Problem Solving");
        addFlokkur("Leadership");

        // Get AI-generated questions for each category and job title
        for (String flokkur : flokkar) {
            List<String> questions = OpenAIAssistant.getAIGeneratedQuestions(flokkur, jobTitle);
            addSpurningar(flokkur, questions.toArray(new String[0]));
        }
    }

    public void addFlokkur(String flokkur) {
        if (!flokkar.contains(flokkur)) {
            flokkar.add(flokkur);
            spurningar.put(flokkur, FXCollections.observableArrayList());
        }
    }

    public void addSpurningar(String flokkur, String... spurningarTextar) {
        List<String> listi = spurningar.get(flokkur);
        if (listi != null) {
            listi.addAll(List.of(spurningarTextar));
        }
    }

    public ObservableList<String> getFlokkar() {
        return flokkar;
    }

    public ObservableList<String> getSpurningaListi(String flokkur) {
        return spurningar.getOrDefault(flokkur, FXCollections.observableArrayList());
    }

    public SimpleStringProperty fjoldiSvaradraSpurningaProperty() {
        return fjoldiSvaradraSpurninga;
    }

    public void aukaFjoldiSvaradra() {
        int nyttGildi = Integer.parseInt(fjoldiSvaradraSpurninga.get()) + 1;
        fjoldiSvaradraSpurninga.set(String.valueOf(nyttGildi));
    }

}
