package fr.umontpellier.iut.trains.cartes;

import java.util.ArrayList;
import java.util.List;

import fr.umontpellier.iut.trains.Bouton;
import fr.umontpellier.iut.trains.Joueur;

public class PersonnelDeGare extends Carte {

    public PersonnelDeGare() {
        super("Personnel de gare", 2, 0, TypeCarte.ACTION);
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);

        List<Bouton> boutons = new ArrayList<>();
        boutons.add(new Bouton("Piocher une carte", "piocher"));
        boutons.add(new Bouton("Recevoir 1$", "argent"));
        boutons.add(new Bouton("Remettre ferraille", "ferraille"));
        String choix = joueur.choisir("Choisissez une action", null, boutons, false);

        switch (choix) {
            case "piocher" -> joueur.piocherEnMain();
            case "argent" -> joueur.incrementerArgent(1);
            case "ferraille" -> {
                Carte c = joueur.getMain().retirer("Ferraille");
                if (c != null) {
                    joueur.remettreCarteDansLaReserve(c);
                }
            }
        }
    }
}
