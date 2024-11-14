package fr.umontpellier.iut.trainsJavaFX.vues;
import fr.umontpellier.iut.trainsJavaFX.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

/**
 * Cette classe correspond à la fenêtre principale de l'application.
 * <p>
 * Elle est initialisée avec une référence sur la partie en cours (Jeu).
 * <p>
 * On y définit les bindings sur les éléments internes qui peuvent changer
 * (le joueur courant, ses cartes en main, son score, ...)
 * ainsi que les listeners à exécuter lorsque ces éléments changent
 */

public class VueDuJeu extends HBox {

    private final IJeu jeu;
    @FXML
    private VuePlateau plateau;
    private VueJoueurCourant joueurCourant;
    private VueAutresJoueurs autresJoueurs;
    private HBox carteEnJeu;
    private VueCartesRecues vueCartesRecues;
    private VueCartesReserve vueCartesReserve;
    private VBox vueCartesEnJeu;

    public VueDuJeu(IJeu jeu) {
        this.jeu = jeu;
        carteEnJeu = new HBox();
        autresJoueurs = new VueAutresJoueurs();
        autresJoueurs.setAlignment(Pos.CENTER);
        carteEnJeu = new HBox();
        joueurCourant = new VueJoueurCourant(jeu);
        plateau = new VuePlateau();
        plateau.setPrefSize(100,100);
        vueCartesRecues = new VueCartesRecues();
        vueCartesReserve = new VueCartesReserve(jeu);
        this.vueCartesEnJeu = new VueCartesEnJeu();


        VBox vBoxGauche = new VBox(vueCartesReserve, new Separator(Orientation.HORIZONTAL), vueCartesRecues, new Separator(Orientation.HORIZONTAL), vueCartesEnJeu);
        VBox vBoxDroite = new VBox(autresJoueurs, new Separator(Orientation.HORIZONTAL), plateau, new Separator(Orientation.HORIZONTAL), joueurCourant);
        this.getChildren().addAll(vBoxGauche, new Separator(Orientation.VERTICAL), vBoxDroite);
    }

    public void creerBindings() {
        plateau.prefWidthProperty().bind(getScene().widthProperty());
        plateau.prefHeightProperty().bind(getScene().heightProperty());
        plateau.creerBindings();
        vueCartesReserve.prefWidthProperty().bind(getScene().widthProperty());
        vueCartesReserve.prefHeightProperty().bind(getScene().heightProperty());
    }

    public IJeu getJeu() {
        return jeu;
    }

    EventHandler<? super MouseEvent> actionPasserParDefaut = mouseEvent -> {System.out.println("Vous avez choisi Passer");
        getJeu().passerAEteChoisi();};


}

