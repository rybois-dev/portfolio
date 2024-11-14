package fr.umontpellier.iut.trains;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import fr.umontpellier.iut.trains.cartes.Carte;
import fr.umontpellier.iut.trains.cartes.EffetTour;
import fr.umontpellier.iut.trains.cartes.FabriqueListeDeCartes;
import fr.umontpellier.iut.trains.cartes.ListeDeCartes;
import fr.umontpellier.iut.trains.cartes.TypeCarte;
import fr.umontpellier.iut.trains.plateau.Plateau;
import fr.umontpellier.iut.trains.plateau.Tuile;

public class Joueur {
    private Jeu jeu;
    private String nom;
    private int argent;
    private int pointsRails;
    private int nbJetonsRails;
    private int score;
    private ListeDeCartes main;
    private ListeDeCartes pioche;
    private ListeDeCartes defausse;
    private ListeDeCartes cartesEnJeu;
    private ListeDeCartes cartesRecues;
    private List<EffetTour> listeEffets;
    private CouleurJoueur couleur;

    public Joueur(Jeu jeu, String nom, CouleurJoueur couleur) {
        this.jeu = jeu;
        this.nom = nom;
        this.couleur = couleur;
        argent = 0;
        score = 0;
        pointsRails = 0;
        nbJetonsRails = 20;
        main = new ListeDeCartes();
        defausse = new ListeDeCartes();
        pioche = new ListeDeCartes();
        cartesEnJeu = new ListeDeCartes();
        cartesRecues = new ListeDeCartes();
        listeEffets = new ArrayList<>();

        // créer 7 Train omnibus (non disponibles dans la réserve)
        pioche.addAll(FabriqueListeDeCartes.creerListeDeCartes("Train omnibus", 7));
        // prendre 2 Pose de rails de la réserve
        for (int i = 0; i < 2; i++) {
            pioche.add(jeu.prendreDansLaReserve("Pose de rails"));
        }
        // prendre 1 Gare de la réserve
        pioche.add(jeu.prendreDansLaReserve("Gare"));
        pioche.melanger();

        piocherEnMain(5);
    }

    public Jeu getJeu() {
        return jeu;
    }

    public boolean hasEffet(EffetTour effet) {
        return listeEffets.contains(effet);
    }

    public int nbEffet(EffetTour effet) {
        return (int) listeEffets.stream().filter(e -> e.equals(effet)).count();
    }

    public CouleurJoueur getCouleur() {
        return couleur;
    }

    public int getArgent() {
        return argent;
    }

    public int getNbJetonsRails() {
        return nbJetonsRails;
    }

    public int getScore() {
        return score;
    }

    public void incrementerScore() {
        score += 1;
    }

    public ListeDeCartes getDefausse() {
        return defausse;
    }

    public ListeDeCartes getPioche() {
        return pioche;
    }

    public ListeDeCartes getCartesEnJeu() {
        return cartesEnJeu;
    }

    public ListeDeCartes getCartesRecues() {
        return cartesRecues;
    }

    public ListeDeCartes getMain() {
        return main;
    }

    public String getNom() {
        return nom;
    }

    public void ajouterMain(Carte carte) {
        main.add(carte);
    }

    public void mettreSurPioche(Carte carte) {
        pioche.add(0, carte);
    }

    public Carte recevoirFerraille() {
        if (!hasEffet(EffetTour.DEPOTOIR)) {
            return recevoir("Ferraille");
        }
        return null;
    }

    /**
     * Retire une carte dans la réserve et la place dans les cartes reçues du joueur
     * (s'il en reste)
     * 
     * @param nomCarte nom de la carte à prendre dans la réserve
     * @return la carte retirée dans la réserve ou null si aucune carte disponible
     */
    public Carte recevoir(String nomCarte) {
        Carte c = jeu.prendreDansLaReserve(nomCarte);
        if (c != null) {
            cartesRecues.add(c);
        }
        return c;
    }

    /**
     * Place une carte dans les cartes reçues du joueur
     * Ne fait rien si la carte est null
     */
    public void recevoir(Carte carte) {
        if (carte != null) {
            cartesRecues.add(carte);
        }
    }

    /**
     * Retire une carte dans la réserve et la place dans la main du joueur
     * (s'il en reste)
     * 
     * @param nomCarte nom de la carte à prendre dans la réserve
     * @return la carte retirée dans la réserve ou null si aucune carte disponible
     */
    public Carte recevoirEnMain(String nomCarte) {
        Carte c = jeu.prendreDansLaReserve(nomCarte);
        if (c != null) {
            main.add(c);
        }
        return c;
    }

    public ListeDeCartes toutesLesCartes() {
        ListeDeCartes toutesLesCartes = new ListeDeCartes();
        toutesLesCartes.addAll(main);
        toutesLesCartes.addAll(cartesEnJeu);
        toutesLesCartes.addAll(cartesRecues);
        toutesLesCartes.addAll(pioche);
        toutesLesCartes.addAll(defausse);
        return toutesLesCartes;
    }

    /**
     * Prend et retourne la première carte de la pioche.
     * Si la pioche est vide, la méthode commence par mélanger toute la défausse
     * dans la pioche.
     *
     * @return la carte piochée ou {@code null} si aucune carte disponible
     */
    public Carte piocher() {
        if (pioche.isEmpty()) {
            pioche.addAll(defausse);
            defausse.clear();
            pioche.melanger();
        }
        if (pioche.isEmpty()) {
            return null;
        }
        return pioche.remove(0);
    }

    public List<Carte> piocher(int n) {
        List<Carte> cartes = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Carte c = piocher();
            if (c == null) {
                break;
            }
            cartes.add(c);
        }
        return cartes;
    }

    public void piocherEnMain() {
        Carte c = piocher();
        if (c != null) {
            main.add(c);
        }
    }

    public void piocherEnMain(int n) {
        main.addAll(piocher(n));
    }

    public void defausser(Carte carte) {
        if (carte != null) {
            defausse.add(carte);
        }
    }

    public void defausser(Collection<Carte> cartes) {
        defausse.addAll(cartes);
    }

    public void choisirPositionDepart() {
        List<String> choixPossibles = jeu.getPositionsDepartDisponibles().stream().map(p -> "TUILE:" + p).toList();
        String choix = choisir("Choisissez votre position de départ", choixPossibles, null, false);
        int i = Integer.parseInt(choix.split(":")[1]);
        this.nbJetonsRails -= 1;
        jeu.getTuile(i).ajouterRail(this);
    }

    public void jouerTour() {
        jeu.log("<div class=\"tour\">Tour de " + toLog() + "</div>");

        // initialisation
        boolean peutRecycler = true;
        boolean finTour = false;

        while (!finTour) {
            // Préparer la liste de choix possibles
            Set<String> choixPossibles = new HashSet<>();
            // Si le joueur peut recycler de la ferraille
            if (peutRecycler && main.getCarte("Ferraille") != null) {
                choixPossibles.add("Ferraille");
            }
            // Cartes jouables en main
            choixPossibles.addAll(main.stream()
                    .filter(c -> c.peutEtreJouee(this))
                    .map(Carte::getNom)
                    .collect(Collectors.toSet()));
            // Si le joueur peut poser des rails
            if (pointsRails > 0 && nbJetonsRails > 0) {
                for (String index : getPositionsRailDisponibles()) {
                    choixPossibles.add("TUILE:" + index);
                }
            }
            // Cartes que le joueur peut acheter
            for (String carte : getCartesAchatPossibles()) {
                choixPossibles.add("ACHAT:" + carte);
            }

            // Choix de l'action à réaliser
            String input = choisir(String.format("Tour de %s", this.nom), choixPossibles, null, true);

            if (input.isEmpty()) {
                // Passer (fin du tour)
                finTour = true;
            } else if (input.equals("Ferraille")) {
                // Recycler la ferraille
                recyclerFerraille();
                finTour = true;
            } else if (input.startsWith("TUILE:")) {
                // Poser un rail
                int index = Integer.parseInt(input.split(":")[1]);
                construireRail(index);
            } else if (input.startsWith("ACHAT:")) {
                // Acheter une carte
                String nomCarte = input.split(":")[1];
                acheterCarte(nomCarte);
            } else {
                // Jouer une carte de la main
                jouerCarte(input);
            }
            peutRecycler = false; // Le joueur ne peut recycler qu'au début de son tour
        }
        finaliserLeTour();
    }

    private void recyclerFerraille() {
        List<Carte> ferrailles = main
                .stream()
                .filter(c -> c.hasType(TypeCarte.FERRAILLE))
                .toList();
        for (Carte c : ferrailles) {
            main.remove(c);
            jeu.remettreCarteDansLaReserve(c);
        }
        log(String.format("Recycle %d %s", ferrailles.size(), ferrailles.get(0).toLog()));
    }

    private void construireRail(int index) {
        log(String.format("Construit un rail en %s", Plateau.getCoordonnees(index)));
        this.nbJetonsRails -= 1;
        this.pointsRails -= 1;
        Tuile tuile = jeu.getTuile(index);
        tuile.onConstruitRail(this);
        this.argent -= tuile.getSurcout(this);
        tuile.ajouterRail(this);
    }

    private Carte acheterCarte(String nomCarte) {
        Carte carte = recevoir(nomCarte);
        log(String.format("Achète %s", carte.toLog()));
        argent -= carte.getCout();
        carte.onAchat(this);
        if (hasEffet(EffetTour.TRAIN_MATINAL)) {
            String choix = choisir(
                    String.format("Voulez-vous placer %s sur votre deck?", carte),
                    null,
                    List.of(new Bouton("oui"), new Bouton("non")),
                    false);
            if (choix.equals("oui")) {
                cartesRecues.remove(carte);
                pioche.add(0, carte);
            }
        }
        return carte;
    }

    private void jouerCarte(String nomCarte) {
        Carte carte = main.retirer(nomCarte);
        log(String.format("Joue %s", carte.toLog()));
        cartesEnJeu.add(carte);
        incrementerArgent(carte.getValeur());
        carte.jouer(this);
    }

    private List<String> getCartesAchatPossibles() {
        return jeu.getCartesDisponiblesEnReserve().stream()
                .filter(carte -> carte.peutEtreAchetee(this))
                .map(Carte::getNom)
                .toList();
    }

    /**
     * Termine le tour du joueur
     * <p>
     * - Le compteur d'argent est remis à 0
     * - Les cartes en main, en jeu et gagnées sont défaussées
     * - Le joueur pioche 5 cartes en main
     */
    private void finaliserLeTour() {
        argent = 0;
        pointsRails = 0;
        listeEffets.clear();
        // défausse la main et les cartes en jeu
        defausse.addAll(cartesEnJeu);
        cartesEnJeu.clear();
        defausse.addAll(cartesRecues);
        cartesRecues.clear();
        defausse.addAll(main);
        main.clear();

        // pioche 5 cartes en main
        piocherEnMain(5);
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner("\n");
        joiner.add(String.format("=== %s (%d pts) ===", nom, score));
        joiner.add(String.format("  Argent: %d  Rails: %d", argent, pointsRails));
        joiner.add("  Cartes en jeu: " + cartesEnJeu);
        joiner.add("  Cartes reçues: " + cartesRecues);
        joiner.add("  Cartes en main: " + main);
        return joiner.toString();
    }

    public String toLog() {
        return String.format("<span class=\"joueur %s\">%s</span>", couleur.toString(), nom);
    }

    public void log(String message) {
        jeu.log(message);
    }

    /**
     * @return le score total du joueur (score courant + points des cartes + points
     *         des villes et lieux éloignés)
     */
    public int getScoreTotal() {
        int scoreTotal = score;
        for (Carte c : toutesLesCartes()) {
            scoreTotal += c.getNbPointsVictoire();
        }
        for (Tuile tuile : jeu.getTuiles()) {
            if (tuile.hasRail(this)) {
                scoreTotal += tuile.getNbPointsVictoire();
            }
        }
        return scoreTotal;
    }

    /**
     * Attend une entrée de la part du joueur (au clavier ou sur la websocket) et
     * renvoie le choix du joueur.
     * <p>
     * Cette méthode lit les entrées du jeu ({@code Jeu.lireligne()}) jusqu'à ce
     * qu'un choix valide (un élément de {@code choix} ou de {@code boutons} ou
     * éventuellement la chaîne vide si l'utilisateur est autorisé à passer) soit
     * reçu.
     * Lorsqu'un choix valide est obtenu, il est renvoyé par la fonction.
     * <p>
     * Exemple d'utilisation pour demander à un joueur de répondre à une question
     * par "oui" ou "non" :
     * <p>
     * {@code
     * List<String> choix = Arrays.asList("oui", "non");
     * String input = choisir("Voulez vous faire ceci ?", choix, null, false);
     * }
     * <p>
     * Si par contre on voulait proposer les réponses à l'aide de boutons, on
     * pourrait utiliser :
     * <p>
     * {@code
     * List<String> boutons = Arrays.asList(new Bouton("Un", "1"), new Bouton("Deux", "2"));
     * String input = choisir("Choisissez un nombre.", null, boutons, false);
     * }
     * (ici le premier bouton a le label "Un" et envoie la String "1" s'il est
     * cliqué, le second a le label "Deux" et envoie la String "2" lorsqu'il est
     * cliqué)
     *
     * @param instruction message à afficher à l'écran pour indiquer au joueur la
     *                    nature du choix qui est attendu
     * @param choix       une collection de chaînes de caractères correspondant aux
     *                    choix valides attendus du joueur (ou {@code null})
     * @param boutons     une collection d'objets de type Bouton définis par deux
     *                    chaînes de caractères (label, valeur) correspondant aux
     *                    choix valides attendus du joueur qui doivent être
     *                    représentés par des boutons sur l'interface graphique (le
     *                    label est affiché sur le bouton, la valeur est ce qui est
     *                    envoyé au jeu quand le bouton est cliqué) ou {@code null}
     * @param peutPasser  booléen indiquant si le joueur a le droit de passer sans
     *                    faire de choix. S'il est autorisé à passer, c'est la
     *                    chaîne de caractères vide ({@code ""}) qui signifie qu'il
     *                    désire
     *                    passer.
     * @return le choix de l'utilisateur (un élement de {@code choix}, ou la valeur
     *         d'un élément de {@code boutons} ou la chaîne vide)
     */
    public String choisir(
            String instruction,
            Collection<String> choix,
            List<Bouton> boutons,
            boolean peutPasser) {
        if (choix == null)
            choix = new ArrayList<>();
        if (boutons == null)
            boutons = new ArrayList<>();

        HashSet<String> choixDistincts = new HashSet<>(choix);
        choixDistincts.addAll(boutons.stream().map(Bouton::valeur).toList());
        if (peutPasser || choixDistincts.isEmpty()) {
            choixDistincts.add("");
        }

        String entree;
        // Lit l'entrée de l'utilisateur jusqu'à obtenir un choix valide
        while (true) {
            jeu.prompt(instruction, boutons, peutPasser);
            entree = jeu.lireLigne();
            // si une réponse valide est obtenue, elle est renvoyée
            if (choixDistincts.contains(entree)) {
                return entree;
            }
        }
    }

    /**
     * Renvoie une représentation du joueur sous la forme d'un dictionnaire de
     * valeurs sérialisables (qui sera converti en JSON pour l'envoyer à l'interface
     * graphique)
     */
    Map<String, Object> dataMap() {
        return Map.ofEntries(
                Map.entry("nom", nom),
                Map.entry("couleur", couleur),
                Map.entry("scoreTotal", getScoreTotal()),
                Map.entry("argent", argent),
                Map.entry("rails", pointsRails),
                Map.entry("nbJetonsRails", nbJetonsRails),
                Map.entry("main", main.dataMap()),
                Map.entry("defausse", defausse.dataMap()),
                Map.entry("cartesEnJeu", cartesEnJeu.dataMap()),
                Map.entry("cartesRecues", cartesRecues.dataMap()),
                Map.entry("pioche", pioche.dataMap()),
                Map.entry("listeEffets", listeEffets.stream().map(EffetTour::toString).toList()),
                Map.entry("actif", jeu.getJoueurCourant() == this));
    }

    public void ajouterEffet(EffetTour effet) {
        listeEffets.add(effet);
    }

    public void incrementerRails() {
        pointsRails++;
    }

    public void incrementerArgent(int i) {
        argent += i;
    }

    public void remettreCarteDansLaReserve(Carte c) {
        jeu.remettreCarteDansLaReserve(c);
    }

    public void ecarterCarte(Carte c) {
        jeu.ecarterCarte(c);
    }

    public List<Carte> getCartesDisponiblesEnReserve() {
        return jeu.getCartesDisponiblesEnReserve();
    }

    public Collection<String> getPositionsGareDisponibles() {
        return jeu.getPositionsGareDisponibles();
    }

    public Collection<String> getPositionsRailDisponibles() {
        return jeu.getPositionsRailDisponibles(this);
    }

    public void ajouterGare(int i) {
        jeu.ajouterGare(i);
    }
}
