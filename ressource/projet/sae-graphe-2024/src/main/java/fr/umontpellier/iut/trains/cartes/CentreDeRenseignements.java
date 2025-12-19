package fr.umontpellier.iut.trains.cartes;

import java.util.List;

import fr.umontpellier.iut.trains.Bouton;
import fr.umontpellier.iut.trains.Joueur;

public class CentreDeRenseignements extends Carte {

    public CentreDeRenseignements() {
        super("Centre de renseignements", 4, 1, TypeCarte.ACTION);
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);

        ListeDeCartes cartesDevoilees = new ListeDeCartes(joueur.piocher(4));
        List<Bouton> boutons;
        String choix;
        Carte carteChoisie;

        // prendre une carte en main
        boutons = cartesDevoilees
                .stream()
                .map(Carte::getNom)
                .map(Bouton::new)
                .toList();
        choix = joueur.choisir("Vous pouvez prendre une carte en main", null, boutons, true);
        if (!choix.isEmpty()) {
            carteChoisie = cartesDevoilees.retirer(choix);
            joueur.getMain().add(carteChoisie);
        }

        // remettre les cartes restantes sur la pioche
        while (!cartesDevoilees.isEmpty()) {
            boutons = cartesDevoilees
                    .stream()
                    .map(Carte::getNom)
                    .map(Bouton::new)
                    .toList();
            choix = joueur.choisir("Remettez les cartes sur la pioche", null, boutons, false);

            carteChoisie = cartesDevoilees.retirer(choix);
            joueur.getPioche().add(0, carteChoisie);
        }
    }
}
