package fr.umontpellier.iut.trainsJavaFX.vues;

import fr.umontpellier.iut.trainsJavaFX.IJoueur;
import fr.umontpellier.iut.trainsJavaFX.TrainsIHM;
import fr.umontpellier.iut.trainsJavaFX.mecanique.Joueur;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class VueResultats extends Stage {

    private TrainsIHM ihm;

    public VueResultats(TrainsIHM ihm) {
        this.ihm = ihm;
        StackPane root = new StackPane();
        int place = 2;
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);

        IJoueur gagnant = ihm.getJeu().getJoueurs().get(0);
        int pointMax = 0;
        for(IJoueur j : ihm.getJeu().getJoueurs()){
            if (j.getScoreTotal()>pointMax) gagnant = j;
        }

        Label titreg = new Label("GAGNANT"+ "\n");
        titreg.setStyle("-fx-font-weight: bold; -fx-font-size: 20; -fx-text-fill: green");
        titreg.setAlignment(Pos.CENTER);
        vBox.getChildren().add(titreg);

        vBox.getChildren().add(new Label());

        Label gnt = new Label(gagnant.getNom() + " avec "+ gagnant.getScoreTotal() + " points"+ "\n");
        gnt.setStyle("-fx-font-weight: bold;-fx-font-size: 18;");
        gnt.setAlignment(Pos.CENTER);
        vBox.getChildren().add(gnt);
        ihm.getJeu().getJoueurs().remove(gagnant);

        Label autre = new Label("Autres joueurs:"+ "\n");
        autre.setStyle("-fx-font-weight: bold; -fx-text-fill: red");
        vBox.getChildren().add(new Label());
        vBox.getChildren().add(autre);

        for (int i = 0; i<ihm.getJeu().getJoueurs().size() ; i++){
            IJoueur joueur = ihm.getJeu().getJoueurs().get(0);
            int points = 0;
            for(IJoueur j : ihm.getJeu().getJoueurs()){
                if (j.getScoreTotal()>pointMax) joueur = j;
            }
            Label player = new Label("En "+ place +"e place: " + joueur.getNom() + " avec "+ joueur.getScoreTotal() + " points");
            player.setAlignment(Pos.CENTER);
            vBox.getChildren().add(player);
            place++;
            ihm.getJeu().getJoueurs().remove(joueur);
        }
        Button fermer = new Button("Fermer");
        fermer.setAlignment(Pos.CENTER);
        fermer.setOnAction(e -> Platform.exit());
        vBox.getChildren().add(new Label());


        Button rejouer = new Button("Rejouer");
        fermer.setAlignment(Pos.CENTER);
        rejouer.setOnAction(e -> {
            ihm.debuterJeu();
            this.close();
        });
        vBox.getChildren().add(rejouer);
        vBox.getChildren().add(fermer);

        root.getChildren().add(vBox);

        root.setPadding(new Insets(20, 20, 20, 20));
        setScene(new Scene(root));
        show();
    }

}
