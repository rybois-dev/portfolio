package fr.umontpellier.iut.trainsJavaFX.vues;

import fr.umontpellier.iut.trainsJavaFX.GestionJeu;
import fr.umontpellier.iut.trainsJavaFX.IJoueur;
import fr.umontpellier.iut.trainsJavaFX.mecanique.cartes.Carte;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class VueCartesRecues extends VBox {

    private VueCarte vueCarte;
    private HBox hBoxCartes;

    public VueCartesRecues() {
        hBoxCartes = new HBox();
        hBoxCartes.setSpacing(-27); // définit l'espacement horizontal entre les boutons à -30 pixels
        this.getChildren().addAll(new Label("Cartes Reçues :"), hBoxCartes);
        this.setMinHeight(160); // un peu plus de la hauteur d'une carte

        creerBindings();
    }

    public void creerBindings() {
        for (IJoueur joueur : GestionJeu.getJeu().getJoueurs()) { // pour tous les joueurs
            joueur.cartesRecuesProperty().addListener((src, anc, nouv) -> { // j'ajoute un listener sur leur cartes reçues

                // à chaque changement de la liste des cartes reçues (de n'importe quel joueur), je clear tout
                hBoxCartes.getChildren().clear();

                for (Carte carte : joueur.cartesRecuesProperty()) { // et je re-remplie avec les cartes

                    Button b = new Button();
                    b.setText(carte.getNom());

                    this.vueCarte = new VueCarte(carte);
                    this.vueCarte.ajouteImageABouton(b);

                    hBoxCartes.getChildren().add(b); // une fois en haut

                }
            });
        }
    }
}
