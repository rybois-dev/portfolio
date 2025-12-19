package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class Cooperation extends CartePoseDeRails {
    public Cooperation() {
        super(5, "Coopération");
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);
        
        joueur.ajouterEffet(EffetTour.COOPERATION);
    }
}
