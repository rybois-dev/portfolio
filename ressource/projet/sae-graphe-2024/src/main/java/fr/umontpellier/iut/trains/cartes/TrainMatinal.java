package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class TrainMatinal extends CarteTrain {

    public TrainMatinal() {
        super(5, "Train matinal", 2, true);
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);

        joueur.ajouterEffet(EffetTour.TRAIN_MATINAL);
    }
}
