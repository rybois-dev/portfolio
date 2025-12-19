package fr.umontpellier.iut.trains.plateau;

import fr.umontpellier.iut.trains.Joueur;
import fr.umontpellier.iut.trains.cartes.EffetTour;

public class TuileEtoile extends Tuile {
    private int valeur;

    public TuileEtoile(int valeur) {
        super();
        this.valeur = valeur;
    }

    @Override
    public int getSurcout(Joueur joueur) {
        if (joueur.hasEffet(EffetTour.VOIE_SOUTERRAINE)) {
            return 0;
        }
        return valeur + super.getSurcout(joueur);
    }
    
    @Override
    public int getSurcout(){
        return valeur + super.getSurcout();
    }

    @Override
    public boolean peutEtrePositionDepart() {
        return false;
    }

    @Override
    public int getNbPointsVictoire() {
        return valeur;
    }
}
