package fr.umontpellier.iut.trainsJavaFX.vues;

import fr.umontpellier.iut.trainsJavaFX.GestionJeu;
import fr.umontpellier.iut.trainsJavaFX.ICarte;
import fr.umontpellier.iut.trainsJavaFX.IJeu;
import fr.umontpellier.iut.trainsJavaFX.mecanique.cartes.Carte;
import javafx.beans.binding.Bindings;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class VueCartesReserve extends VBox{
    private IJeu jeu;
    private VueCarte vueCarte;
    private VBox carteReserve;

    public VueCartesReserve(IJeu jeu){
        this.jeu=jeu;
        carteReserve = new VBox();
        getChildren().add(carteReserve);
        creerReserve();
    }

    private void creerReserve(){
        HBox hBoxDuHaut = new HBox();
        HBox hBoxDuBas = new HBox();
        HBox hBoxDuMillieu = new HBox();
        hBoxDuHaut.setSpacing(-27); // définit l'espacement horizontal entre les boutons à -30 pixels
        hBoxDuBas.setSpacing(-27);
        hBoxDuMillieu.setSpacing(-27);
        carteReserve.getChildren().addAll(hBoxDuHaut, hBoxDuMillieu,hBoxDuBas);

        for(int i = 0; i<jeu.getReserve().size(); i++){
            Button b = new Button();
//            carteAchetable.setOrientation(Orientation.HORIZONTAL); // définit l'orientation du FlowPane à horizontale
//            carteAchetable.setVgap(-30);
            Carte carte = jeu.getReserve().get(i);
            b.setText(carte.getNom());

            vueCarte = new VueCarte(carte);
            vueCarte.ajouteImageABouton(b);
            creerBindings(b,carte);

            EventHandler<? super MouseEvent> carteAction = mouseEvent -> {
                System.out.println("Vous avez acheté " + carte.getNom());
                jeu.uneCarteDeLaReserveEstAchetee(carte.getNom());
            };
            b.setOnMouseClicked(carteAction);
            b.setMaxHeight(10);
            b.setMaxWidth(10);

            if (i % 3 == 0) hBoxDuHaut.getChildren().add(b); // une fois en haut
            else if (i % 2 == 0) {
                hBoxDuMillieu.getChildren().add(b);
            } else hBoxDuBas.getChildren().add(b); // une fois en bas*/
        }
    }
    private void creerBindings(Button b, Carte carte){
        Label compteur = new Label();
        compteur.textProperty().bind(Bindings.concat(jeu.getTaillesPilesReserveProperties().get(carte.getNom())));
        vueCarte.ajouterCompteurSurBoutonCarte(b,compteur);
    }
}
