package fr.umontpellier.iut.trainsJavaFX.vues;


import fr.umontpellier.iut.trainsJavaFX.mecanique.cartes.Carte;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Cette classe représente la vue d'une carte.
 * <p>
 * On y définit le listener à exécuter lorsque cette carte a été choisie par l'utilisateur
 */
public class VueCarte extends StackPane {

    private final Carte carte;

    public VueCarte(Carte carte) {
        this.carte = carte;
    }


    public void ajouteImageABouton(Button b){
        String source = "images/cartes/" +
                carte.getNom().toLowerCase().replace(' ', '_').replace("é", "e").replace("ô","o") +
                ".jpg";
        System.out.println(source);
        ImageView imageCarte = new ImageView(source);
        imageCarte.setPreserveRatio(true);
        imageCarte.setFitHeight(130);

        b.setGraphic(imageCarte);
        b.textProperty().set(carte.getNom());
        b.setStyle("-fx-background-color: transparent; -fx-text-fill: transparent;");
    }
    public void ajouterCompteurSurBoutonCarte(Button b, Label compteur) {
        Circle cercleDuCompteur = new Circle(10,10 ,10,Color.RED);
        compteur.setTextFill(Color.BLACK);
        compteur.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        //cercleDuCompteur.setCenterX(10);
        // je regoupe le cercle et le compteur
        Group groupCompteurLabel = new Group(cercleDuCompteur, compteur);

        // je crée une StackPane pour superposer le groupe sur l'image existante du bouton
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(b.getGraphic(), groupCompteurLabel);

        // je place le compteir et le cercle en bas à droite
        StackPane.setAlignment(groupCompteurLabel, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(groupCompteurLabel, new Insets(0, 0, 10, 10));

        //met le compteur au premier plan
        groupCompteurLabel.toFront();

        //affiche le compteur
        b.setGraphic(stackPane);
    }

    public void setCarteChoisieListener(EventHandler<MouseEvent> quandCarteEstChoisie) {
        setOnMouseClicked(quandCarteEstChoisie);
    }

}
