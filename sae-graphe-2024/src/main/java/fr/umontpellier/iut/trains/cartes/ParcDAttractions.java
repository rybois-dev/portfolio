package fr.umontpellier.iut.trains.cartes;

import java.util.List;

import fr.umontpellier.iut.trains.Joueur;

public class ParcDAttractions extends Carte {

    public ParcDAttractions() {
        super("Parc d'attractions", 4, 1, TypeCarte.ACTION);
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);

        List<String> choixPossibles = joueur
                .getCartesEnJeu()
                .stream()
                .filter(c -> c.getTypes().contains(TypeCarte.TRAIN))
                .map(Carte::getNom)
                .toList();
        String choix = joueur.choisir("Choisissez une carte TRAIN en jeu", choixPossibles, null, false);

        if (!choix.isEmpty()) {
            Carte c = joueur.getCartesEnJeu().getCarte(choix);
            joueur.incrementerArgent(c.getValeur());
        }
    }
}
