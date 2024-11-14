package fr.umontpellier.iut.trainsJavaFX.vues;

import fr.umontpellier.iut.trainsJavaFX.TrainsIHM;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class VueDemarage extends Stage {
    private TrainsIHM trainsIHM;
    public VueDemarage(TrainsIHM trainsIHM) {
        this.trainsIHM=trainsIHM;
        StackPane root = new StackPane();


        ImageView photoPlateau = new ImageView("images/icons/boiteJeu.png");
        root.getChildren().add(photoPlateau);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(5);

        // Définissez l'action à exécuter à la fin du Timeline
        timeline.setOnFinished(event -> {
            trainsIHM.debuterJeu();
            this.close();
        });
        timeline.play();

        setScene(new Scene(root));
        setMaxHeight(450);
        setMinHeight(450);
        setMinWidth(420);
        setMaxWidth(420);
        show();
    }
}
