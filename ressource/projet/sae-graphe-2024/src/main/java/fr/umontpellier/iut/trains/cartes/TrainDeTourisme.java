package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class TrainDeTourisme extends CarteTrain {

    public TrainDeTourisme() {
        super(4, "Train de tourisme", 1, true);
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);

        joueur.incrementerScore();
    }

    
}
