package fr.umontpellier.iut.trains.cartes;

import java.util.List;

import fr.umontpellier.iut.trains.Joueur;
import fr.umontpellier.iut.trains.plateau.Plateau;

public class Gare extends Carte {

    public Gare() {
        super("Gare", 3, 0, TypeCarte.GARE);
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);

        List<String> choixPossibles = joueur.getPositionsGareDisponibles().stream().map(i -> "TUILE:" + i).toList();

        String choix = joueur.choisir("Sélectionner la tuile où placer la gare", choixPossibles, null, false);
        if (!choix.isEmpty()) {
            int i = Integer.parseInt(choix.split(":")[1]);
            joueur.log(String.format("... place une gare en %s", Plateau.getCoordonnees(i)));
            joueur.ajouterGare(i);
        }

        joueur.recevoirFerraille();
    }

}
