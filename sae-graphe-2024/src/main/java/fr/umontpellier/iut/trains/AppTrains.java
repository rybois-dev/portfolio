package fr.umontpellier.iut.trains;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.umontpellier.iut.trains.cartes.FabriqueListeDeCartes;
import fr.umontpellier.iut.trains.plateau.Plateau;

public class AppTrains {
    public static void main(String[] args) {
        String[] nomsJoueurs = { "Guybrush", "Largo"};

        // Tirer aléatoirement 8 cartes préparation
        List<String> cartesPreparation = new ArrayList<>(FabriqueListeDeCartes.getNomsCartesPreparation());
        Collections.shuffle(cartesPreparation);
        String[] nomsCartes = cartesPreparation.subList(0, 8).toArray(new String[0]);

        Jeu jeu = new Jeu(nomsJoueurs, nomsCartes, Plateau.OSAKA);
        jeu.run();
    }
}
