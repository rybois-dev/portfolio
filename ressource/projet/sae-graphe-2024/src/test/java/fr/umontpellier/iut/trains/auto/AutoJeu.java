package fr.umontpellier.iut.trains.auto;

import fr.umontpellier.iut.trains.IOJeu;
import fr.umontpellier.iut.trains.plateau.Plateau;

public class AutoJeu extends IOJeu {
    private Strategy strategie;

    public AutoJeu(String[] nomJoueurs, String[] cartesPreparation, Plateau plateau) {
        super(nomJoueurs, cartesPreparation, plateau);
    }

    public void setStrategie(Strategy strategie) {
        this.strategie = strategie;
    }

    @Override
    public String lireLigne() {
        if (getInstructions().isEmpty()) {
            strategie.update();
        }
        return super.lireLigne();
    }
}
