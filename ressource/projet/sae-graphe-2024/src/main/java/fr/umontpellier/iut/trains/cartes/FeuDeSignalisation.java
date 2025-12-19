package fr.umontpellier.iut.trains.cartes;

import java.util.ArrayList;
import java.util.List;

import fr.umontpellier.iut.trains.Bouton;
import fr.umontpellier.iut.trains.Joueur;

public class FeuDeSignalisation extends Carte {

    public FeuDeSignalisation() {
        super("Feu de signalisation", 2, 0, TypeCarte.ACTION);
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);

        joueur.piocherEnMain();
        Carte c = joueur.piocher();

        if (c != null) {
            List<Bouton> boutons = new ArrayList<>();
            boutons.add(new Bouton("Oui", "oui"));
            boutons.add(new Bouton("Non", "non"));
            String choix = joueur.choisir(String.format("Voulez vous défausser %s ?", c.getNom()), null, boutons,
                    false);
            if (choix.equals("oui")) {
                joueur.defausser(c);
            } else {
                joueur.getPioche().add(0, c);
            }
        }
    }
}
