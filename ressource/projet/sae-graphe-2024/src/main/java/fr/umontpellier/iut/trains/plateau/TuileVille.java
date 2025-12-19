package fr.umontpellier.iut.trains.plateau;

import fr.umontpellier.iut.trains.Joueur;
import fr.umontpellier.iut.trains.cartes.EffetTour;

public class TuileVille extends Tuile {
    private int nbGaresMax;
    private int nbGaresPosees;

    public TuileVille(int taille) {
        this(taille, 0);
    }

    /**
     * Constructeur pour pouvoir créer des villes avec des gares déjà posées.
     * Sert surtout pour la Phase 2 du projet.
    */
    public TuileVille(int taille, int nbGaresPosees) {
        super();
        this.nbGaresMax = taille;
        this.nbGaresPosees = nbGaresPosees;
    }

    @Override
    public boolean peutPlacerGare() {
        return nbGaresPosees < nbGaresMax;
    }

    @Override
    public void ajouterGare() {
        nbGaresPosees += 1;
    }

    @Override
    public int getSurcout(Joueur joueur) {
        if (joueur.hasEffet(EffetTour.VOIE_SOUTERRAINE)) {
            return 0;
        }
        if (joueur.hasEffet(EffetTour.VIADUC)) {
            return super.getSurcout(joueur);
        }
        return nbGaresPosees + 1 + super.getSurcout(joueur);
    }
    
    @Override
    public int getSurcout(){
        return nbGaresPosees + 1 + super.getSurcout();
    }

    @Override
    public int getNbGares() {
        return nbGaresPosees;
    }

    @Override
    public int getNbPointsVictoire() {
        return switch (nbGaresPosees) {
            case 1 -> 2;
            case 2 -> 4;
            case 3 -> 8;
            default -> 0;
        };
    }
}
