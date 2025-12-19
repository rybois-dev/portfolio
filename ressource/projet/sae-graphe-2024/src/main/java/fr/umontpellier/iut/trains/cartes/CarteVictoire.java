package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public abstract class CarteVictoire extends Carte {
    private int nbPointsVictoire;

    public CarteVictoire(int cout, String nom, int nbPointsVictoire) {
        super(nom, cout, 0, TypeCarte.VICTOIRE);
        this.nbPointsVictoire = nbPointsVictoire;
    }

    @Override
    public int getNbPointsVictoire() {
        return nbPointsVictoire;
    }   

    @Override
    public boolean peutEtreJouee(Joueur joueur) {
        return false;
    }

    @Override
    public void onAchat(Joueur joueur) {
        joueur.recevoirFerraille();
    }
}
