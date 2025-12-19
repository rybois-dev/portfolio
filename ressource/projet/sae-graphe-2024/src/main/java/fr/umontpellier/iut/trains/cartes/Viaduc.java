package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class Viaduc extends CartePoseDeRails {
    public Viaduc() {
        super(5, "Viaduc");
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);

        joueur.ajouterEffet(EffetTour.VIADUC);
    }

}
