package fr.umontpellier.iut.trains.cartes;

import java.util.Set;
import java.util.stream.Collectors;

import fr.umontpellier.iut.trains.Joueur;

public class Depot extends Carte {
    public Depot() {
        super("Dépôt", 3, 1, TypeCarte.ACTION);
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);

        joueur.piocherEnMain(2);
        for (int i = 0; i < 2; i++) {
            Set<String> choix = joueur
                    .getMain()
                    .stream()
                    .map(Carte::getNom)
                    .collect(Collectors.toSet());
            String input = joueur.choisir("Défaussez une carte", choix, null, false);
            if (input.isEmpty()) {
                break;
            } else {
                Carte c = joueur.getMain().retirer(input);
                joueur.defausser(c);
            }
        }
    }
}
