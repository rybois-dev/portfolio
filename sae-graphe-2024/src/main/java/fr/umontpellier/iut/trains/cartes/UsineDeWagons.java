package fr.umontpellier.iut.trains.cartes;

import java.util.List;

import fr.umontpellier.iut.trains.Joueur;

public class UsineDeWagons extends Carte {

    public UsineDeWagons() {
        super("Usine de wagons", 5, 0, TypeCarte.ACTION);
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);

        List<String> choixPossibles;
        String choix;
        choixPossibles = joueur.getMain()
                .stream()
                .filter(c -> c.getTypes().contains(TypeCarte.TRAIN))
                .map(Carte::getNom)
                .toList();
        choix = joueur.choisir("Écartez une carte TRAIN de votre main", choixPossibles, null, false);

        if (!choix.isEmpty()) {
            Carte carteEcartee = joueur.getMain().retirer(choix);
            int coutCarte = carteEcartee.getCout();
            joueur.ecarterCarte(carteEcartee);

            // le joueur peut recevoir une carte TRAIN de valeur <= (valeurCarte + 3)
            choixPossibles = joueur.getCartesDisponiblesEnReserve()
                    .stream()
                    .filter(c -> c.getTypes().contains(TypeCarte.TRAIN) && c.getCout() <= (coutCarte + 3))
                    .map(c -> "ACHAT:" + c.getNom())
                    .toList();
            choix = joueur.choisir(
                    String.format("Recevez une carte TRAIN de valeur <= %d", coutCarte + 3),
                    choixPossibles,
                    null,
                    false);
            if (!choix.isEmpty()) {
                String nomCarte = choix.split(":")[1];
                joueur.recevoirEnMain(nomCarte);
            }
        }
    }
}
