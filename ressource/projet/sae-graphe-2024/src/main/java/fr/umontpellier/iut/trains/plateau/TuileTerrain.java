package fr.umontpellier.iut.trains.plateau;

import fr.umontpellier.iut.trains.Joueur;
import fr.umontpellier.iut.trains.cartes.EffetTour;

public class TuileTerrain extends Tuile {
    private TypeTerrain type;

    public TuileTerrain(TypeTerrain type) {
        super();
        this.type = type;
    }

    @Override
    public int getSurcout(Joueur joueur) {
        if (joueur.hasEffet(EffetTour.VOIE_SOUTERRAINE)) {
            return 0;
        }
        return type.getSurcout(joueur) + super.getSurcout(joueur);
    }

    @Override
    public int getSurcout(){
        return type.getSurcout() + super.getSurcout();
    }

}
