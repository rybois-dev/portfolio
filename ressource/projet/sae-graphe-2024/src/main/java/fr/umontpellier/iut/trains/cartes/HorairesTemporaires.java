package fr.umontpellier.iut.trains.cartes;

import java.util.ArrayList;
import java.util.List;

import fr.umontpellier.iut.trains.Joueur;

public class HorairesTemporaires extends Carte {

    public HorairesTemporaires() {
        super("Horaires temporaires", 5, 0, TypeCarte.ACTION);
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);

        List<Carte> cartesDevoilees = new ArrayList<>();
        List<Carte> cartesTrain = new ArrayList<>();
        while (cartesTrain.size() < 2) {
            Carte c = joueur.piocher();
            if (c == null) {
                break;
            }
            if (c.getTypes().contains(TypeCarte.TRAIN)) {
                cartesTrain.add(c);
            } else {
                cartesDevoilees.add(c);
            }
        }
        joueur.defausser(cartesDevoilees);
        joueur.getMain().addAll(cartesTrain);
    }
}
