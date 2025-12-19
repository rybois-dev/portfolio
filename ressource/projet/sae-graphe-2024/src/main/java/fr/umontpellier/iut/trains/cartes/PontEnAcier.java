package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class PontEnAcier extends CartePoseDeRails {
    public PontEnAcier() {
        super(4, "Pont en acier");
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);

        joueur.ajouterEffet(EffetTour.PONT_EN_ACIER);
    }
}
