package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class Depotoir extends Carte {

    public Depotoir() {
        super("Dépotoir", 5, 1, TypeCarte.ACTION);
    }

    public void jouer(Joueur joueur) {
        super.jouer(joueur);
        
        joueur.ajouterEffet(EffetTour.DEPOTOIR);
    }
}
