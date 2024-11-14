package fr.umontpellier.iut.trainsJavaFX.vues;

import fr.umontpellier.iut.trainsJavaFX.mecanique.CouleurJoueur;
import javafx.scene.paint.Color;

import java.util.Map;

public class CouleursJoueurs {
    public static Map<CouleurJoueur, String> couleursBackgroundJoueur = Map.of(
            CouleurJoueur.JAUNE, "#FED440",
            CouleurJoueur.ROUGE, "#795593",
            CouleurJoueur.BLEU, "#4093B6",
            CouleurJoueur.VERT, "#2CCDB4"
    );
    public Color convertiCouleurJoueur(CouleurJoueur couleurJoueur){
        switch (couleurJoueur) {
            case BLEU:
                return Color.BLUE;
            case ROUGE:
                return Color.RED;
            case VERT:
                return Color.GREEN;
            case JAUNE:
                return Color.YELLOW;
            default:
                return Color.WHITE;
        }
    }

}