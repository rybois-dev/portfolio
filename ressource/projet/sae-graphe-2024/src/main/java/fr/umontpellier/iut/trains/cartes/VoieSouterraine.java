package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class VoieSouterraine extends CartePoseDeRails {
    public VoieSouterraine() {
        super(7, "Voie souterraine");
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);

        joueur.ajouterEffet(EffetTour.VOIE_SOUTERRAINE);
    }
}
