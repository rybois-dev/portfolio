package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class Tunnel extends CartePoseDeRails {
    public Tunnel() {
        super(5, "Tunnel");
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);

        joueur.ajouterEffet(EffetTour.TUNNEL);
    }
}
