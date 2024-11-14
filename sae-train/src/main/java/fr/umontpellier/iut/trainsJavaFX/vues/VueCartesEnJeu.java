package fr.umontpellier.iut.trainsJavaFX.vues;

import fr.umontpellier.iut.trainsJavaFX.GestionJeu;
import fr.umontpellier.iut.trainsJavaFX.IJoueur;
import fr.umontpellier.iut.trainsJavaFX.mecanique.cartes.Carte;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class VueCartesEnJeu extends VBox {
    private VueCarte vueCarte;
    private HBox hBoxCartes;

    public VueCartesEnJeu() {
        hBoxCartes = new HBox();
        this.getChildren().addAll(new Label("Cartes en Jeu :"), hBoxCartes);
        this.setMinHeight(160); // un peu plus de la hauteur d'une carte

        creerBindings();
    }

    public void creerBindings() {
        for (IJoueur joueur : GestionJeu.getJeu().getJoueurs()) { // pour tous les joueurs
            joueur.cartesEnJeuProperty().addListener((src, anc, nouv) -> { // j'ajoute un listener sur leur cartes en jeu

                // à chaque changement de la liste des cartes en jeu (de n'importe quel joueur), je clear tout
                hBoxCartes.getChildren().clear();
                hBoxCartes.setSpacing(-27); // définit l'espacement horizontal entre les boutons à -30 pixels

                for (Carte carte : joueur.cartesEnJeuProperty()) { // et je re-remplie avec les cartes

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
