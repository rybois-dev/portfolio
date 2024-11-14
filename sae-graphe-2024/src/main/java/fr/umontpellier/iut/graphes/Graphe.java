package fr.umontpellier.iut.graphes;

import fr.umontpellier.iut.trains.Joueur;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Graphe simple non-orienté pondéré représentant le plateau du jeu.
 * Pour simplifier, on supposera que le graphe sans sommets est le graphe vide.
 * Le poids de chaque sommet correspond au coût de pose d'un rail sur la tuile correspondante.
 * Les sommets sont indexés par des entiers (pas nécessairement consécutifs).
 */

public class Graphe {
    private final Set<Sommet> sommets;
    private Set<Sommet> sommetsTraiter; //utile pour getDistance
    private int nbArrive;
    private int nbChem;

    public Graphe(Set<Sommet> sommets) {
        this.sommets = sommets;
        sommetsTraiter = new HashSet<>();
    }

    /**
     * Construit un graphe à n sommets 0..n-1 sans arêtes
     */
    public Graphe(int n) {
        sommets = new HashSet<>();
        sommetsTraiter = new HashSet<>();
        for(int i=0; i<n ; i++){
            sommets.add(new Sommet.SommetBuilder().setIndice(i).createSommet());
        }
    }

    /**
     * Construit un graphe vide
     */
    public Graphe() {
        sommets = new HashSet<>();
        sommetsTraiter = new HashSet<>();
    }

    /**
     * Construit un sous-graphe induit par un ensemble de sommets
     * sans modifier le graphe donné
     *
     * @param g le graphe à partir duquel on construit le sous-graphe
     * @param X les sommets à considérer (on peut supposer que X est inclus dans l'ensemble des sommets de g,
     *          même si en principe ce n'est pas obligatoire)
     */
    public Graphe(Graphe g, Set<Sommet> X) {
        // Vérifier que X est inclus dans l'ensemble des sommets de g
        for (Sommet sommet : X) {
            if (!g.getSommets().contains(sommet)) {
                throw new IllegalArgumentException("X n'est pas inclus dans l'ensemble des sommets de g");
            }
        }

        Set<Sommet> sommetsSousGraphe = new HashSet<>();
        for (Sommet sommet : X) {
            sommetsSousGraphe.add(sommet);
        }
        // Ajouter les arêtes de g
        for (int i = 0; i<g.getAretes().size(); i++) {
            g.getAretes().stream().toList().get(i).stream().toList().get(0).ajouterVoisin(g.getAretes().stream().toList().get(i).stream().toList().get(1));
            g.getAretes().stream().toList().get(i).stream().toList().get(1).ajouterVoisin(g.getAretes().stream().toList().get(i).stream().toList().get(0));
        }
        this.sommets = sommetsSousGraphe;
    }

    /**
     * Construit un nouveau graphe identique à celui passé en paramètre
     * @param g le graphe que l'on souhaite copier
     */
    public Graphe(Graphe g) {
        this.sommets = new HashSet<>();
        sommetsTraiter = new HashSet<>();
        for (Sommet s : g.sommets) {
            this.sommets.add(new Sommet(s));
        }
    }

    /**
     * @return true si et seulement si la séquence d'entiers passée en paramètre
     * correspond à un graphe simple valide dont les degrés correspondent aux éléments de la liste.
     * Pré-requis : on peut supposer que la séquence est triée dans l'ordre croissant.
     */
    public static boolean sequenceEstGraphe(List<Integer> sequence) {
            // Je vérifie si la séquence est vide
            if (sequence.isEmpty()) {
                return true; // Un graphe avec zéro degré est valide.
            }

            // Je vérifie si le premier élément de la séquence est négatif
            if (sequence.get(0) < 0) {
                return false; // Les degrés ne peuvent pas être négatifs
            }

            // J'initialise le nombre de degrés restants et le nombre de degrés impair
            int degreRestants = 0;
            int nbImpair = 0;

            for (int degre : sequence) { // Je compte le nombre de degrés impair
                if(degre % 2 != 0) nbImpair++;
            }
            if (nbImpair % 2 != 0) return false;// Si le nombre de degrés impair est impair alors la séquence n'est pas un graphe

            for (int degre : sequence) { // Je fais la somme des degrés
                degreRestants+=degre;
            }

            // Je parcours la séquence
            for (int degre : sequence) {

                // Je vérifie si le degré est négatif
                if (degre < 0) {
                    return false; // Les degrés ne peuvent pas être négatifs
                }

                // Je vérifie si le nombre de degrés restants est suffisant pour connecter le nœud actuel
                if (degreRestants < degre) {
                    return false; // Pas assez de degrés restants pour connecter le sommet actuel
                }

                // Mise à jour du nombre de degrés restants
                degreRestants -= degre;
            }

            // Je vérifie si tous les degrés ont été utilisés
            return degreRestants == 0; // Tous les degrés ont été utilisés, le graphe est valide


    }

    public List<Integer> getSequenceOrdreCroissant() {
        List<Integer> seq = new ArrayList<>();
        for (Sommet s : this.sommets) {
            seq.add(s.getVoisins().size());
        }
        Collections.sort(seq);
        return seq;
    }

    /**
     * @param g        le graphe source, qui ne doit pas être modifié
     * @param ensemble un ensemble de sommets
     *                 pré-requis : l'ensemble donné est inclus dans l'ensemble des sommets de {@code g}
         * @return un nouveau graph obtenu en fusionnant les sommets de l'ensemble donné.
     * On remplacera l'ensemble de sommets par un seul sommet qui aura comme indice
     * le minimum des indices des sommets de l'ensemble. Le surcout du nouveau sommet sera
     * la somme des surcouts des sommets fusionnés. Le nombre de points de victoire du nouveau sommet
     * sera la somme des nombres de points de victoire des sommets fusionnés.
     * L'ensemble de joueurs du nouveau sommet sera l'union des ensembles de joueurs des sommets fusionnés.
     */
    public static Graphe fusionnerEnsembleSommets(Graphe g, Set<Sommet> ensemble) {
        Graphe graphe = new Graphe();

        int iMin = -1;
        int surcoutTotal = 0;
        int pointsVictoireTotal = 0;
        Set<Integer> joueurs = new HashSet<>();
        for (Sommet s : ensemble) {
            joueurs.addAll(s.getJoueurs());
            surcoutTotal += s.getSurcout();
            pointsVictoireTotal += s.getNbPointsVictoire();
            if (iMin == -1 || s.getIndice() < iMin) {
                iMin = s.getIndice();
            }
        }

        graphe.ajouterSommet(new Sommet.SommetBuilder()
                .setSurcout(iMin)
                .setSurcout(surcoutTotal)
                .setJoueurs(joueurs)
                .setNbPointsVictoire(pointsVictoireTotal)
                .createSommet());

        return graphe;
    }

    /**
     * @param i un entier
     * @return le sommet d'indice {@code i} dans le graphe ou null si le sommet d'indice {@code i} n'existe pas dans this
     */
    public Sommet getSommet(int i) {
        for (Sommet s : sommets) {
            if (s.getIndice() == i) {
                return s;
            }
        }
        return null;
    }

    /**
     * @return l'ensemble des sommets du graphe
     */
    public Set<Sommet> getSommets() {
        return sommets;
    }

    /**
     * @return l'ordre du graphe, c'est-à-dire le nombre de sommets
     */
    public int getNbSommets() {
        return sommets.size();
    }

    /**
     * @return l'ensemble d'arêtes du graphe sous forme d'ensemble de paires de sommets
     */
    public Set<Set<Sommet>> getAretes() {

        Set<Set<Sommet>> aretes = new HashSet<>();

        for (Sommet s : this.sommets) {

            for (Sommet voisin : s.getVoisins()) {
                Set<Sommet> paire = new HashSet<>();
                paire.add(s);
                paire.add(voisin);
                aretes.add(paire);
            }
        }
        return aretes;
    }

    /**
     * @return le nombre d'arêtes du graphe
     */
    public int getNbAretes() {
        int taille = 0;

        for (Sommet s : this.sommets) {
            taille += s.getVoisins().size();
        }
        return taille/2;
    }

    /**
     * Ajoute un sommet d'indice i au graphe s'il n'est pas déjà présent
     *
     * @param i l'entier correspondant à l'indice du sommet à ajouter dans le graphe
     */
    public boolean ajouterSommet(int i) {
        if(getSommet(i) != null) return false;
        sommets.add(new Sommet.SommetBuilder().setIndice(i).createSommet());
        return true;
    }

    /**
     * Ajoute un sommet au graphe s'il n'est pas déjà présent
     *
     * @param s le sommet à ajouter
     * @return true si le sommet a été ajouté, false sinon
     */
    public boolean ajouterSommet(Sommet s) {
        return sommets.add(s);
    }

    /**
     * @param s le sommet dont on veut connaître le degré
     *          pré-requis : {@code s} est un sommet de this
     * @return le degré du sommet {@code s}
     */
    public int degre(Sommet s) {
        if (s != null && this.sommets.contains(s)) return s.getVoisins().size();
        return 0;
    }

    /**
     * @return true si et seulement si this est complet.
     */
    public boolean estComplet() {
        if (this.sommets.isEmpty()) return false;

        for (Sommet s : sommets) { // pour chaque sommet
            // si il n'a pas autant de voisins qu'il y a de sommets (moins lui même) dans le graphe, return false
            if (s.getVoisins().size() != (sommets.size() - 1)) return false;
        }
        return true;
    }

    /**
     * @return true si et seulement si this est une chaîne. On considère que le graphe vide est une chaîne.
     */
    public boolean estChaine() {

        if (this.sommets.isEmpty()) return true; // un graphe vide est une chaine

        // vu qu'un graphe est une chaine si tous ses sommets ont deux voisins sauf 2 qui en ont qu'une (les deux du bout)

        Set<Sommet> lesDeuxSommetsDuBout = new HashSet<>();

        for (Sommet s : this.sommets) { // je met les deux extremites dans un set
            if (s.getVoisins().size() == 1) lesDeuxSommetsDuBout.add(s);
        }

        // pas une chaine si il n'y a pas exactement deux sommets qui n'ont qu'un voisin
        if (lesDeuxSommetsDuBout.size() != 2) return false;

        for (Sommet s : this.sommets) {
            if (!lesDeuxSommetsDuBout.contains(s)) { // si ce sommet n'est pas une des extremites
                if (s.getVoisins().size() != 2) return false; // et qu'il n'a pas exactement 2 voisins, return false
            }
        }

        return true;

    }

    /**
     * @return true si et seulement si this est un cycle. On considère que le graphe vide n'est pas un cycle.
     */
    public boolean estCycle() {
        boolean contientBoucle = false; //un cycle est comme une espèce de boucle
        if(estConnexe() && getNbAretes() == getNbSommets() && !possedeSousGrapheComplet(getNbAretes())){
            for (Sommet s : sommets) {
                if (degre(s) == 0 || degre(s) > 2) { //un cycle à soit un degrés 1 ou 2
                    return false;
                }
                if (degre(s) == 1) {
                    if (contientBoucle) { //il me semble qu'un cycle n'a peut pas avoir deux degré 1
                        return false;
                    }
                    contientBoucle = true;
                    if (!s.getVoisins().stream().toList().get(0).equals(s)) { //cas ou on repasse plusieurs fois sur un sommet
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    /**
     * @return true si et seulement si this est une forêt. On considère qu'un arbre est une forêt
     * et que le graphe vide est un arbre.
     */
    public boolean estForet() {
        if(sommets.isEmpty()||getNbAretes() == 0){return true;}
        else return !estCycle() && getNbAretes() == (getNbSommets() - getEnsembleClassesConnexite().size());
    }

    /**
     * @return true si et seulement si this a au moins un cycle. On considère que le graphe vide n'est pas un cycle.
     */
    public boolean possedeUnCycle() {

        for (Sommet origine : this.sommets) { // je démarre d'une origine

            for (Sommet voisin : origine.getVoisins()) { // je cherche à partir d'un des voisins de mon origine

                Set<Sommet> rouges = new HashSet<>();
                rouges.add(voisin);

                // je ne fait pas directement à partir de voisin, car c'est garanti que origine se trouve dans ses voisins
                for (Sommet s : voisin.getVoisins()) {
                    if (s != origine) {
                        // je map l'ensemble du graphe, et si à un moment je retrouve origine, alors il y a un cycle
                        if (chercheCycle(origine, s, rouges)) return true;
                    }
                }

            }
        }
        return false;
    }

    public boolean chercheCycle(Sommet origine, Sommet balise, Set<Sommet> rouges) {

        if (balise.getVoisins().contains(origine)) return true; // origine retrouvée
        rouges.add(balise); // je ne veux pas repasser par ce sommet

        for (Sommet s : balise.getVoisins()) {
            if (!rouges.contains(s)) {
                chercheCycle(origine, s, rouges);
            }
        }
        return false;
    }

    /**
     * @return true si et seulement si this a un isthme
     */
    public boolean possedeUnIsthme() {

        if (!this.estConnexe()) return false; // un graphe non connexe ne peux pas avoir d'isthme

        for (Set<Sommet> a : this.getAretes()) { // pour chaque arete du graphe

            // ok donc là ça va être un peu galère à suivre donc restez avec moi.
            // les sommets contenus dans this.getAretes, sont différents de ceux d'une
            // copie profonde car ils n'ont pas la même adresse, ce qui fait que je ne peux
            // pas les supprimer dans la copie, car ce ne sont pas les mêmes. Mais je ne peux
            // pas non plus faire autre chose qu'une copie profonde, car je veux pas les modifier.
            // Je vais donc faire ce qu'on appelle, un pro gamer move.
            Graphe graphe = new Graphe(); // nouveau graphe vide

            // création d'une map pour associer les sommets de this avec leur copie profonde
            Map<Sommet, Sommet> assoSommet = new HashMap<>();

            for (Sommet s : this.sommets) { // pour chaque sommet de this
                Sommet t = new Sommet(s); // copie profonde du sommet
                assoSommet.put(s,t); // association du sommet avec sa copie
                graphe.sommets.add(t); // ajout de la copie à graphe
            }
            for (Sommet q : graphe.sommets) {
                Set<Sommet> newVoisins = new HashSet<>();
                for (Sommet r : q.getVoisins()) {
                    newVoisins.add(assoSommet.get(r));
                }
                q.setVoisins(newVoisins);
            }

            List<Sommet> arete = new ArrayList<>(a); // envoie des aretes en arraylist car y a pas de get() dans un Set

            graphe.supprimerArete(assoSommet.get(arete.get(0)), assoSommet.get(arete.get(1))); // suppression d'une des aretes

            // si en enlevant une arete, on se retrouve avec plus de classes de connexités que avant,
            // je pense pas qu'il est raisonnable de penser que cette arete est un isthme
            if (graphe.getEnsembleClassesConnexite().size() != this.getEnsembleClassesConnexite().size()) {
                return true;
            }
        }
        return false; // renvoie false si aucun isthme n'a été trouvé
    }

    public void ajouterArete(Sommet s, Sommet t) {
        if(sommets.contains(s) && sommets.contains(t)){
            s.ajouterVoisin(t);
            t.ajouterVoisin(s);
        }
    }

    public void supprimerArete(Sommet s, Sommet t) {
        s.enleverVoisin(t);
        t.enleverVoisin(s);
    }
    /**
     * @return une coloration gloutonne du graphe sous forme d'une Map d'ensemble indépendants de sommets.
     * L'ordre de coloration des sommets est suivant l'ordre décroissant des degrés des sommets
     * (si deux sommets ont le même degré, alors on les ordonne par indice croissant).
     */
    public Map<Integer, Set<Sommet>> getColorationGloutonne() {
        // Tri des sommets par ordre décroissant de degré
        List<Sommet> sommetsTriés = new ArrayList<>();
        int max = this.degreMax();
        while(this.sommets.size()!=sommetsTriés.size()){
            for (Sommet s : this.sommets){
                if(degre(s)==max){
                    sommetsTriés.add(s);
                }
            }
            max--;
        }
        // Initialisation des structures de données
        Map<Integer, Set<Sommet>> coloration = new HashMap<>();
        Set<Sommet> sommetsNonColories = new HashSet<>(sommetsTriés);

        // Parcours des sommets triés
        for (Sommet sommet : sommetsTriés) {
            if (sommetsNonColories.contains(sommet)) {


                Set<Integer> voisinsCouleurs = new HashSet<>();
                for (Sommet voisin : sommet.getVoisins()) {
                    for (Integer couleur : coloration.keySet()) {
                        if (coloration.get(couleur).contains(voisin)) {
                            voisinsCouleurs.add(couleur);
                            break;
                        }
                    }
                }

                int couleurDisponible = 1;
                while (voisinsCouleurs.contains(couleurDisponible)) {
                    couleurDisponible++;
                }

                coloration.putIfAbsent(couleurDisponible, new HashSet<>());
                coloration.get(couleurDisponible).add(sommet);
                sommetsNonColories.remove(sommet);
            }
        }

        return coloration;
    }


    /**
     * @param depart  - ensemble non-vide de sommets
     * @param arrivee
     * @return le surcout total minimal du parcours entre l'ensemble de depart et le sommet d'arrivée
     * pré-requis : l'ensemble de départ et le sommet d'arrivée sont inclus dans l'ensemble des sommets de this
     */
    public int getDistance(Set<Sommet> depart, Sommet arrivee) {
        if (!this.sommets.containsAll(depart) || !this.sommets.contains(arrivee)) return 0;
        int coutMinimal = 0;
        for (Sommet s : depart) {
            coutMinimal += getDistance(s, arrivee);
        }
        return coutMinimal;
    }

/*    public int getDistance(Sommet depart, Sommet arrivee) {

        Map<Sommet, Integer> valuation = new HashMap<>();
        for (Sommet s : this.sommets) {
            valuation.put(s, -1); // -1 voudra donc dire pas attegnable
        }

        for (Sommet s : depart.getVoisins()) {
            valuation.put(s, s.getSurcout());
        }

        Set<Sommet> marquesRouge = new HashSet<>();
        marquesRouge.add(depart);

        boolean conditionWhile = false;
        for (Sommet s : this.getClasseConnexite(depart)) {
            if (marquesRouge.contains(s) && valuation.get(s) != -1) conditionWhile = true;
        }

        while (this.getClasseConnexite(depart).size() != marquesRouge.size() && conditionWhile) {

            Sommet s = null;
            int min = -1;

            for (Sommet t : this.getClasseConnexite(depart)) {
                if (!marquesRouge.contains(t) && (min == -1 || valuation.get(t) < min)) {
                    s = t;
                    min = t.getSurcout();
                }
            }

            if (s != null) {
                for (Sommet t : s.getVoisins()) {
                    int valmin;
                    if (valuation.get(s) + t.getSurcout() > valuation.get(t)) valmin = valuation.get(t);
                    else valmin = valuation.get(s) + t.getSurcout();
                    valuation.put(t, valmin);
                }

            }
            marquesRouge.add(s);

            conditionWhile = false;
            for (Sommet t : this.getClasseConnexite(depart)) {
                if (marquesRouge.contains(t) && valuation.get(t) != -1) conditionWhile = true;
            }
        }
        int min = -1;
        for (Sommet s : valuation.keySet()) {
            if (min == -1 || valuation.get(s) < min) {
                min = valuation.get(s);
            }
        }

        return min;
    }*/

    /**
     * @return le surcout total minimal du parcours entre le sommet de depart et le sommet d'arrivée
     */
    public int getDistance(Sommet depart, Sommet arrivee) {

        // en gros j'avais un problème car la valuation d'un sommet est faite en attribuant un
        // chiffre à un sommet. Sauf que un sommet peut avoir plusieurs valuation de la manière
        // que je l'ai fait, bref un peu compliqué à expliquer, me voir pour plus d'expliquation
        Set<ArrayList<Sommet>> sommetsDifferencies = new HashSet<>();
        for (Sommet s : this.sommets) {
            for (Sommet t : s.getVoisins()) {
                ArrayList<Sommet> duo = new ArrayList<>(); // création d'un duo de sommet
                duo.add(t); // en gros j'identifie un sommet (t) par un sommet qui l'a comme voisin
                duo.add(s); // donc il peut y avoir deux sommet t différents par cette arraylist
                sommetsDifferencies.add(duo);
            }
        }
        ArrayList<Sommet> s = null; // normalement il sera init juste en dessous

        Map<ArrayList<Sommet>, Integer> valuation = new HashMap<>(); // la distance d'un sommet
        for (ArrayList<Sommet> duo : sommetsDifferencies) {
            if (duo.get(0) == depart) {
                valuation.put(duo, 0);
                s = duo; // voilà, c'est init maintenant
            }
            else valuation.put(duo, duo.get(0).getSurcout());
        }

        int valuArrivMin = 999; // c'est pour test là

        Set<Sommet> rouge = new HashSet<>(); // les sommets que je ne veux plus tester

        Set<ArrayList<Sommet>> options = new HashSet<>(); // les sommets qui seront passés en revue

        while (rouge.size() != this.sommets.size()) { // BOUCLE WHILE
            rouge.add(s.get(0));

            for (Sommet t : s.get(0).getVoisins()) {
                for (ArrayList<Sommet> duo : sommetsDifferencies) {
                    // je récup le duo qui a comme sommet principale t, sommet secondaire s, et si s n'est pas rouge :
                    if (duo.get(0) == t && duo.get(1) == s.get(0) && !rouge.contains(t)) {
                        options.add(duo);
                        valuation.put(duo, valuation.get(duo) + valuation.get(s)); // valu de t += valu du sommet precedent
                    }
                }
            }

            int lePlusBas = -1;
            for (ArrayList<Sommet> duo : options) { // boucle pour trouver la meilleure option
                if (!rouge.contains(duo.get(0)) && (lePlusBas == -1 || valuation.get(duo) < lePlusBas) ) {
                    lePlusBas = valuation.get(duo);
                    s = duo;
                }
                if (duo.get(0) == arrivee && valuation.get(duo) < valuArrivMin) valuArrivMin = valuation.get(duo);
            }
        }

        return valuArrivMin;
    }

    public Set<Set<Sommet>> chercher(Set<Sommet> precedents, Sommet sommetParent, Sommet arrivee, Set<Set<Sommet>> chemins, Set<Set<Sommet>> rouges) {

        // les prints
        if (this.nbChem != chemins.size()) {
            int cheminSize = chemins.size();
            double ratioAr = 1;
            if (this.nbArrive != 0) ratioAr = (double) this.nbArrive /cheminSize * 100;
            System.out.println("\nnb chemins : " + cheminSize + ", nbArrive : " + this.nbArrive + ", ar/ch : " + ratioAr + " %");
        this.nbChem = chemins.size();
//            System.out.println(precedents);
        }
//        if (this.idIter % 1000000 == 0) System.out.println("\n barre des " + this.idIter + " passé");

        for (Sommet s : sommetParent.getVoisins()) {
            Set<Sommet> sommetSetVerif = new HashSet<>(precedents);
            sommetSetVerif.add(sommetParent);
            sommetSetVerif.add(s);
            if (!rouges.contains(sommetSetVerif)) {

                if (s.equals(arrivee)) { // si sommetParent est arrivé à ... bah euh l'arrivée
                    Set<Sommet> sommetSet = new HashSet<>(precedents); // nouveau chemin à partir de l'ancien
                    sommetSet.add(sommetParent); // j'ajoute sommetParent à l'historique
                    sommetSet.add(s);
                    chemins.add(sommetSet); // j'ajoute ce chemin aux autres
                    rouges.add(sommetSet);
                    this.nbArrive++;

                } else { // si sommetParent n'est pas arrivée à la destination
                    boolean impasse = true; // je vérifie que sommetParent ne soit pas dans une impasse
                    for (Sommet t : sommetParent.getVoisins()) {
                        if (!precedents.contains(t)) {
                            impasse = false; // si dans ses voisins, il y a au moins un qui ne soit pas du chemin
                        }
                    }

                    if (impasse) { // si c'est une impasse
                        Set<Sommet> sommetSet = new HashSet<>(precedents); // nouveau chemin à partir de l'ancien
                        sommetSet.add(sommetParent); // j'ajoute ce sommet à l'historique
                        chemins.add(sommetSet); // j'ajoute ce chemin aux autres
                        rouges.add(sommetSet);

                    } else { // sinon je continue
//                    System.out.println("check impasse");
                        Set<Sommet> sommetSet = new HashSet<>(precedents); // nouveau chemin à partir de l'ancien
                        sommetSet.add(sommetParent); // j'ajoute ce sommet à l'historique
                        chemins.add(sommetSet); // j'ajoute ce chemin aux autres
                        rouges.add(sommetSet);

                        // ET C'EST LA QUE CA CHANGE DES AUTRES
                        // ET NON VOUS NE REVEZ PAS, je vais bel et bien appeler chercherAll, dans chercherAll !
                        // ça me fait penser à Inception tout ça, c'était un super film en vrai
                        // je pense le re regarder quand j'aurais du temps, parce que là avec toutes les SAE... bref


                        // je cherche pour les voisins de sommetParent s'ils n'ont pas déjà été cherché
                        if (!precedents.contains(s)) chercher(sommetSet, s, arrivee, chemins, rouges);

                    }
                }
            }
        }

        return chemins; // je retourne mes chemins

    }

/*    private Sommet plusPetitSurcout(Sommet depart, Sommet arrivee){
        Sommet plusPetitSurcout = depart;
        int surcout = depart.getVoisins().stream().toList().get(0).getSurcout();
        for (Sommet voisin : depart.getVoisins()){
            if(!sommetsTraiter.contains(voisin)){
                if(voisin.equals(arrivee)) return depart;
                if (surcout > voisin.getSurcout()){
                    surcout = voisin.getSurcout();
                    plusPetitSurcout = voisin;
                }
                sommetsTraiter.add(voisin);
            }
        }
        return plusPetitSurcout;
    }*/

    /**
     * @return l'ensemble des classes de connexité du graphe sous forme d'un ensemble d'ensembles de sommets.
     */
    public Set<Set<Sommet>> getEnsembleClassesConnexite() {
        Set<Set<Sommet>> ensembleClassesConnexite = new HashSet<>();
        if (sommets.isEmpty())
            return ensembleClassesConnexite;
        Set<Sommet> sommets = new HashSet<>(this.sommets);
        while (!sommets.isEmpty()) {
            Sommet v = sommets.iterator().next();
            Set<Sommet> classe = getClasseConnexite(v);
            sommets.removeAll(classe);
            ensembleClassesConnexite.add(classe);
        }
        return ensembleClassesConnexite;
    }

    /**
     * @param v un sommet du graphe this
     * @return la classe de connexité du sommet {@code v} sous forme d'un ensemble de sommets.
     */
    public Set<Sommet> getClasseConnexite(Sommet v) {
        if (!sommets.contains(v))
            return new HashSet<>();
        Set<Sommet> classe = new HashSet<>();
        calculerClasseConnexite(v, classe);
        return classe;
    }

    private void calculerClasseConnexite(Sommet v, Set<Sommet> dejaVus) {
        dejaVus.add(v);
        Set<Sommet> voisins = v.getVoisins();

        for (Sommet voisin : voisins) {
            if (dejaVus.add(voisin))
                calculerClasseConnexite(voisin, dejaVus);
        }
    }

    /**
     * @return true si et seulement si this est connexe.
     */
    public boolean estConnexe() {
        return this.getEnsembleClassesConnexite().size() == 1;
    }

    /**
     * @return le degré maximum des sommets du graphe
     */
    public int degreMax() {
        int degreMaximum = 0;
        for(Sommet s : sommets){
            if(degre(s)>degreMaximum) degreMaximum = degre(s);
        }
        return degreMaximum;
    }

    /**
     * @return une coloration propre optimale du graphe sous forme d'une Map d'ensemble indépendants de sommets.
     * Chaque classe de couleur est représentée par un entier (la clé de la Map).
     * Pré-requis : le graphe est issu du plateau du jeu Train (entre autres, il est planaire).
     */
    public Map<Integer, Set<Sommet>> getColorationPropreOptimale() {
        Map<Integer, Set<Sommet>> coloration = new HashMap<>();

        // attribution de couleur qui individualise chaque sommet
        Map<Sommet, Integer> colorTemp = new HashMap<>();
        for (Sommet s : this.sommets) {
            colorTemp.put(s, 1); // on commence par tous les mettre à 1
        }

        do {

            for (Sommet s : this.sommets) { // pour chaque sommet
                Set<Integer> vals = new HashSet<>();
                for (Sommet t : s.getVoisins()) {
                    vals.add(colorTemp.get(t)); // je met les couleurs des voisins dans le Set
                }

                int cMin = 1;
                while (vals.contains(cMin)) { // tant que la couleur n'est pas disponible
                    cMin++; // j'augmente la couleur de 1 jusqu'à ce qu'elle ne soit prise par aucun voisin
                }
                colorTemp.put(s, cMin); // j'attribue la couleur à mon sommet
            }

        } while (!toutVaBien(colorTemp)); // et je refait jusqu'à avoir une coloration valide (voir fonction)

        for (Sommet s : this.sommets) { // pour chaque sommet
            if (coloration.containsKey(colorTemp.get(s))) coloration.get(colorTemp.get(s)).add(s);
            else {
                coloration.put(colorTemp.get(s), new HashSet<>());
                coloration.get(colorTemp.get(s)).add(s);
            }
        }
        return coloration;
    }

    private boolean toutVaBien (Map<Sommet, Integer> colorTemp) {
        for (Sommet s : this.sommets) { // pour chaque sommet
            Set<Integer> vals = new HashSet<>();
            for (Sommet t : s.getVoisins()) { // pour chaque voisin de ce sommet
                // si la couleur du sommet est la même que celle du voisin, alors il y a un problème
                if (colorTemp.get(s) == colorTemp.get(t)) return false; // et je renvoie false
                vals.add(colorTemp.get(t)); // je met les couleurs des voisins dans le Set
            }

            int cMin = 1;
            while (vals.contains(cMin)) { // tant que la couleur n'est pas disponible
                cMin++; // j'augmente la couleur de 1 jusqu'à ce qu'elle ne soit prise par aucun voisin
            }

            if (colorTemp.get(s) != cMin) return false; // est-ce possible de mettre une couleur plus basse ?
        }
        return true; // si aucun problème, je renvoie true
    }


    private void attributNbASommetPourColoration(Sommet s,Map<Integer,Sommet> sommetAvecNb){
        // si la liste est vide cela signifie qu'il n'y a pas de coloration donc que c'est le commencement
        if(sommetAvecNb.isEmpty()) s.setNumeroColoration(1);
        Set<Integer> enregistrementNum = new HashSet<>();
        // ajoute dans un Set les num déjà utilisé par ces voisins
        for(Sommet sommet : s.getVoisins()){
            if(sommetAvecNb.containsKey(sommet)){
                enregistrementNum.add(sommet.getNumColoration());
            }
        }
        // recherche quelle numéro n'est pas utiliser (est disponible)
        for(int i = 0; i< enregistrementNum.size(); i++){
            // regarde chaque num de enregistrement et arrête la boucle quand num est dispo
            if(!enregistrementNum.contains(i)){
                s.setNumeroColoration(i);
                break;
            }
        }
    }


    /**
     * @return true si et seulement si this possède un sous-graphe complet d'ordre {@code k}
     */
     public boolean possedeSousGrapheComplet(int k) {
        if (k > sommets.size()) {
            return false;
        }

        // Appeler la méthode récursive pour vérifier si le graphe contient un sous-graphe complet d'ordre k
        return contientSousGrapheComplet(new HashSet<Sommet>(), k, 0);
    }

    private boolean contientSousGrapheComplet(Set<Sommet> sousGraphe, int k, int indice) {
        if (sousGraphe.size() == k) {
            // Vérifier si le sous-graphe est complet en utilisant la méthode estSousGrapheComplet()
            return estSousGrapheComplet(sousGraphe);
        }

        if (indice == sommets.size()) {
            // Tous les sommets ont été examinés et aucun sous-graphe complet n'a été trouvé
            return false;
        }

        List<Sommet> listeSommets = new ArrayList<Sommet>(sommets);
        Sommet s = listeSommets.get(indice);

        // Ajouter le sommet s au sous-graphe et vérifier si le graphe contient un sous-graphe complet d'ordre k
        sousGraphe.add(s);
        if (contientSousGrapheComplet(sousGraphe, k, indice + 1)) {
            return true;
        }
        sousGraphe.remove(s);

        // Ne pas ajouter le sommet s au sous-graphe et vérifier si le graphe contient un sous-graphe complet d'ordre k
        return contientSousGrapheComplet(sousGraphe, k, indice + 1);
    }

    private boolean estSousGrapheComplet(Set<Sommet> sousGraphe) {
        for (Sommet s1 : sousGraphe) {
            for (Sommet s2 : sousGraphe) {
                if (s1 != s2 && !s1.getVoisins().contains(s2)) {
                    return false;
                }
            }
        }

        return true;
    }


    /*public boolean possedeSousGrapheComplet(int k) {
         if(getSousGrapheComplet(k)!=null) return true;
         else return false;
     }
    public Graphe getSousGrapheComplet(int k) {
        if (k > sommets.size()) {
            return null;
        }
        for (Sommet sommet : sommets) {
            Graphe sousGraphe = getSousGrapheCompletRec(sommet, k, new HashSet<>());
            if (sousGraphe != null) {
                return sousGraphe;
            }
        }
        return null;
    }

    private Graphe getSousGrapheCompletRec(Sommet sommet, int k, Set<Sommet> visites) {
        if (k == 0) {
            Graphe sousGraphe = new Graphe(this, visites);
            return sousGraphe;
        }
        visites.add(sommet);
        for (Sommet voisin : sommet.getVoisins()) {
            if (!visites.contains(voisin)) {
                Graphe sousGraphe = getSousGrapheCompletRec(voisin, k - 1, visites);
                if (sousGraphe != null) {
                    sousGraphe.ajouterArete(sommet, voisin);
                    return sousGraphe;
                }
            }
        }
        visites.remove(sommet);
        return null;
    }*/

     public boolean estSousGrapheComplet(Graphe graphe, int k) {
        if (graphe.getNbSommets() != k) {
            return false;
        }
        for (Sommet sommet : graphe.getSommets()) {
            if (degre(sommet) != k - 1) {
                return false;
            }
        }
        return true;
    }

    private Set<Sommet> donneUnSousGraphe(int taille) throws IndexOutOfBoundsException{
        if(sommets.isEmpty()){throw new IndexOutOfBoundsException("la liste de sommet est vide");}
        Set<Sommet> sommets1 = new HashSet<>();
        for(int i=0; i<taille ; i++){
            sommets1.add(sommets.stream().toList().get(i));
        }
        return sommets1;
    }


    private boolean estSousGraphe(Set<Sommet> s){
        if(sommets.isEmpty())return  false;
        for(Sommet sommet : sommets){
            if(!s.contains(sommet))return false;
        }
        return true;
    }

    /**
     * @param g un graphe
     * @return true si et seulement si this possède un sous-graphe isomorphe à {@code g}
     */
    public boolean possedeSousGrapheIsomorphe(Graphe g) {
        throw new RuntimeException("Méthode à implémenter");
    }

    /**
     * @param depart
     * @param arrivee
     * @return un ensemble de sommets qui forme un ensemble critique de plus petite taille entre {@code depart} et {@code arrivee}
     */
    public Set<Sommet> getEnsembleCritique(Sommet depart, Sommet arrivee){

        Set<ArrayList<Sommet>> sommetsDifferencies = new HashSet<>();
        for (Sommet s : this.sommets) {
            for (Sommet t : s.getVoisins()) {
                ArrayList<Sommet> duo = new ArrayList<>(); // création d'un duo de sommet
                duo.add(t); // en gros j'identifie un sommet (t) par un sommet qui l'a comme voisin
                duo.add(s); // donc il peut y avoir deux sommet t différents par cette arraylist
                sommetsDifferencies.add(duo);
            }
        }
        ArrayList<Sommet> s = null; // normalement il sera init juste en dessous

        Map<ArrayList<Sommet>, Set<Sommet>> chemin = new HashMap<>(); // la distance d'un sommet

        for (ArrayList<Sommet> duo : sommetsDifferencies) {
            chemin.put(duo, new HashSet<>());

            if (duo.get(0) == depart) s = duo; // voilà, c'est init maintenant
        }

        Set<Sommet> setArrivMin = null; // init à null

        Set<Sommet> rouge = new HashSet<>(); // les sommets que je ne veux plus tester

        Set<ArrayList<Sommet>> options = new HashSet<>(); // les sommets qui seront passés en revue

        while (rouge.size() != this.sommets.size()) { // BOUCLE WHILE
            rouge.add(s.get(0));

            for (Sommet t : s.get(0).getVoisins()) {
                for (ArrayList<Sommet> duo : sommetsDifferencies) {
                    // je récup le duo qui a comme sommet principale t, sommet secondaire s, et si s n'est pas rouge :
                    if (duo.get(0) == t && duo.get(1) == s.get(0) && !rouge.contains(t)) {
                        options.add(duo);
                        chemin.get(duo).addAll(chemin.get(s)); // j'ajoute les sommets de s aux sommets de t
                        chemin.get(duo).add(duo.get(1));
                        chemin.get(duo).add(duo.get(0));
                    }
                }
            }

            int lePlusBas = -1;
            for (ArrayList<Sommet> duo : options) { // boucle pour trouver la meilleure option
                if (!rouge.contains(duo.get(0)) && (lePlusBas == -1 || chemin.get(duo).size() < lePlusBas) ) {
                    lePlusBas = chemin.get(duo).size();
                    s = duo;
                }
                if (duo.get(0) == arrivee && (setArrivMin == null || chemin.get(duo).size() < setArrivMin.size())) {
                    setArrivMin = chemin.get(duo);
                }
            }
        }

        return setArrivMin;
    }
}
