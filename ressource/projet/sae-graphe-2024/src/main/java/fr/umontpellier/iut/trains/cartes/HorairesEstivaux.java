package fr.umontpellier.iut.trains.cartes;

import java.util.List;

import fr.umontpellier.iut.trains.Bouton;
import fr.umontpellier.iut.trains.Joueur;

public class HorairesEstivaux extends Carte {

    public HorairesEstivaux() {
        super("Horaires estivaux", 3, 0, TypeCarte.ACTION);
    }

    @Override
    public void jouer(Joueur joueur) {
        jouer(joueur, this);
    }

    @Override
    public void jouer(Joueur joueur, Carte carteJouee) {
        super.jouer(joueur);

        List<Bouton> boutons = List.of(new Bouton("Oui", "oui"), new Bouton("Non", "non"));
        String choix = joueur.choisir(String.format("Voulez-vous écarter %s?", getNom()), null, boutons, false);
        if (choix.equals("oui")) {
            joueur.getCartesEnJeu().remove(carteJouee);
            joueur.ecarterCarte(carteJouee);
            joueur.incrementerArgent(3);
        }
    }

}
