package fr.umontpellier.iut.trainsJavaFX.vues;

import fr.umontpellier.iut.trainsJavaFX.GestionJeu;
import fr.umontpellier.iut.trainsJavaFX.IJoueur;
import fr.umontpellier.iut.trainsJavaFX.mecanique.CouleurJoueur;
import fr.umontpellier.iut.trainsJavaFX.mecanique.Joueur;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;


/**
 * Cette classe présente les éléments des joueurs autres que le joueur courant,
 * en cachant ceux que le joueur courant n'a pas à connaitre.
 * <p>
 * On y définit les bindings sur le joueur courant, ainsi que le listener à exécuter lorsque ce joueur change
 */
public class VueAutresJoueurs extends VBox {
    private CouleursJoueurs couleursJoueurs;
    public VueAutresJoueurs (){
        couleursJoueurs = new CouleursJoueurs();
        creerBindings();
    }
    public void creerBindings() {
        GestionJeu.getJeu().joueurCourantProperty().addListener((src, oldJ, newJ) -> {
            this.getChildren().clear();
            for (IJoueur joueur : GestionJeu.getJeu().getJoueurs()) {
                if (joueur != GestionJeu.getJeu().joueurCourantProperty().get()) {

                    Joueur j = (Joueur) joueur;

                    HBox hbox = new HBox();

                    hbox.getChildren().addAll(new Label(j.getNom() + " : ")); // le nom du joueur
                    int tailleIcone = 30;
                    ImageView sous = initialisationImage(30,"coins");
                    hbox.getChildren().addAll(sous, new Label("" + j.getArgent())); // l'argent du joueur

                    ImageView nbRailImage = initialisationImage(tailleIcone,"rail"); //icone nbrail
                    hbox.getChildren().addAll(nbRailImage, new Label("" + j.nbJetonsRailsProperty().get())); // l'argent du joueur

                    ImageView imageScore = initialisationImage(tailleIcone,"score"); // iconte
                    hbox.getChildren().addAll(imageScore, new Label("" + j.getScoreTotal())); // l'argent du joueur

                    ImageView deck = initialisationImage(tailleIcone,"deck"); // icone deck
                    hbox.getChildren().addAll(deck, new Label("" + j.getPioche().size())); // l'argent du joueur

                    ImageView defausse = initialisationImage(tailleIcone,"defausse"); // icone deck
                    hbox.getChildren().addAll(defausse, new Label("" + j.getDefausse().size())); // l'argent du joueur

                    hbox.setSpacing(10);
                    hbox.setAlignment(Pos.CENTER);

                    hbox.setBackground(new Background(new BackgroundFill(couleursJoueurs.convertiCouleurJoueur(j.getCouleur()),CornerRadii.EMPTY,Insets.EMPTY)));
                    this.getChildren().add(hbox);
                }
            }
        });

        /*pointRailsAutreJoueur.textProperty().addListener((ObservableValue, IJoueur, t1)-> {
            pointRailsAutreJoueur.setText("Point de rails : "+joueur.pointsRailsProperty().toString());
        });
        argentAutresJoueur.textProperty().addListener((ObservableValue, IJoueur, t1) -> {
            argentAutresJoueur.setText("argent : " + joueur.argentProperty());
        });*/
    }

    private ImageView initialisationImage(int tailleIcone, String nom){
            ImageView imageView = new ImageView("images/boutons/"+nom+".png");
            imageView.setFitHeight(tailleIcone);
            imageView.setPreserveRatio(true);
       return imageView;
    }
}
