package fr.umontpellier.iut.trains;

import fr.umontpellier.iut.trains.cartes.Carte;
import fr.umontpellier.iut.trains.cartes.FabriqueListeDeCartes;
import fr.umontpellier.iut.trains.cartes.ListeDeCartes;
import fr.umontpellier.iut.trains.plateau.Plateau;
import fr.umontpellier.iut.trains.plateau.Tuile;
import org.junit.jupiter.api.BeforeAll;

import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

// @Timeout(value = 1, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
@SuppressWarnings("unchecked")
public class BaseTestClass {
    public static final long TIMEOUT_VALUE = 500;

    IOJeu jeu;
    List<Tuile> tuiles;
    List<Joueur> joueurs;
    Joueur joueur;
    ListeDeCartes main;
    ListeDeCartes pioche;
    ListeDeCartes defausse;
    ListeDeCartes cartesEnJeu;
    ListeDeCartes cartesRecues;
    ListeDeCartes cartesEcartees;
    Map<String, ListeDeCartes> reserve;

    @BeforeAll
    static void staticInit() {
        System.setOut(new PrintStream(OutputStream.nullOutputStream()));
    }

    /**
     * Renvoie un attribut d'un objet à partir de son nom.
     * La méthode cherche s'il existe un champ déclaré dans la classe de l'objet et
     * si ce n'est pas le cas remonte dans la hiérarchie des classes jusqu'à trouver
     * un champ avec le nom voulu ou renvoie null.
     *
     * @param object objet dont on cherche le champ
     * @param name   nom du champ
     * @return le champ de l'objet, avec un type statique Object
     */
    public static Object getAttribute(Object object, String name) {
        Class<?> c = object.getClass();
        while (c != null) {
            try {
                Field field = c.getDeclaredField(name);
                field.setAccessible(true);
                return field.get(object);
            } catch (NoSuchFieldException e) {
                c = c.getSuperclass();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    /**
     * Setter générique pour forcer la valeur d'un attribut quelconque.
     * La méthode cherche s'il existe un champ déclaré dans la classe de l'objet et
     * si ce n'est pas le cas remonte dans la hiérarchie des classes jusqu'à trouver
     * un champ avec le nom voulu. Lorsque le champ est trouvé, on lui donne la
     * valeur
     * passée en argument.
     *
     * @param name  nom du champ
     * @param value valeur à donner au champ
     */
    public static void setAttribute(Class<?> c, String name, Object value) {
        while (c != null) {
            try {
                Field field = c.getDeclaredField(name);
                field.setAccessible(true);
                field.set(c, value);
                return;
            } catch (NoSuchFieldException e) {
                c = c.getSuperclass();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return;
            }
        }
        throw new RuntimeException("No such field: " + name);
    }

    /**
     * Setter générique pour forcer la valeur d'un attribut quelconque.
     * La méthode cherche s'il existe un champ déclaré dans la classe de l'objet et
     * si ce n'est pas le cas remonte dans la hiérarchie des classes jusqu'à trouver
     * un champ avec le nom voulu. Lorsque le champ est trouvé, on lui donne la
     * valeur
     * passée en argument.
     *
     * @param object objet dont on cherche le champ
     * @param name   nom du champ
     * @param value  valeur à donner au champ
     */
    public static void setAttribute(Object object, String name, Object value) {
        Class<?> c = object.getClass();
        while (c != null) {
            try {
                Field field = c.getDeclaredField(name);
                field.setAccessible(true);
                field.set(object, value);
                return;
            } catch (NoSuchFieldException e) {
                c = c.getSuperclass();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return;
            }
        }
        throw new RuntimeException("No such field: " + name);
    }

    public static <T> void addAll(List<T> liste, T... elements) {
        Collections.addAll(liste, elements);
    }

    public static <T> boolean containsReference(Collection<T> collection, T element) {
        return collection.stream().anyMatch(e -> e == element);
    }

    public static <T> boolean containsReferences(Collection<T> collection, T... elements) {
        if (elements.length != collection.size())
            return false;
        for (T e : elements) {
            if (!containsReference(collection, e))
                return false;
        }
        return true;
    }

    public static <T> boolean containsReferences(Collection<T> collection, Collection<T> elements) {
        if (elements.size() != collection.size())
            return false;
        for (T e : elements) {
            if (!containsReference(collection, e))
                return false;
        }
        return true;
    }

    public static <T> boolean containsReferencesInOrder(List<T> list, T... elements) {
        if (elements.length != list.size()) {
            return false;
        }
        for (int i = 0; i < elements.length; i++) {
            if (elements[i] != list.get(i)) {
                return false;
            }
        }
        return true;
    }

    public static boolean containsSame(Collection<Carte> collection, Carte... elements) {
        List<Carte> elementsList = Arrays.asList(elements);
        if (elements.length != collection.size()) {
            return false;
        }
        for (Carte e : elements) {
            long n1 = collection.stream().filter(el -> Objects.equals(el.getNom(), e.getNom())).count();
            long n2 = elementsList.stream().filter(el -> Objects.equals(el.getNom(), e.getNom())).count();
            if (n1 != n2) {
                return false;
            }
        }
        return true;
    }

    public ListeDeCartes getMain(Joueur joueur) {
        return (ListeDeCartes) getAttribute(joueur, "main");
    }

    public ListeDeCartes getCartesEnJeu(Joueur joueur) {
        return (ListeDeCartes) getAttribute(joueur, "cartesEnJeu");
    }

    public ListeDeCartes getCartesRecues(Joueur joueur) {
        return (ListeDeCartes) getAttribute(joueur, "cartesRecues");
    }

    public ListeDeCartes getPioche(Joueur joueur) {
        return (ListeDeCartes) getAttribute(joueur, "pioche");
    }

    public ListeDeCartes getDefausse(Joueur joueur) {
        return (ListeDeCartes) getAttribute(joueur, "defausse");
    }

    public static int getArgent(Joueur joueur) {
        return (int) getAttribute(joueur, "argent");
    }

    public static int getPointsRails(Joueur joueur) {
        return (int) getAttribute(joueur, "pointsRails");
    }

    public static int getNbJetonsRails(Joueur joueur) {
        return (int) getAttribute(joueur, "nbJetonsRails");
    }

    public int getNbJetonsGare(Jeu jeu) {
        return (int) getAttribute(jeu, "nbJetonsGare");
    }

    public List<Carte> getAllCartes(Jeu jeu) {
        List<Carte> cartes = new ArrayList<>();
        cartes.addAll(cartesEcartees);
        for (String nomCarte : reserve.keySet()) {
            cartes.addAll(reserve.get(nomCarte));
        }
        for (Joueur joueur : joueurs) {
            cartes.addAll(getMain(joueur));
            cartes.addAll(getPioche(joueur));
            cartes.addAll(getDefausse(joueur));
            cartes.addAll(getCartesEnJeu(joueur));
            cartes.addAll(getCartesRecues(joueur));
        }
        return cartes;
    }

    public void setJeu(IOJeu jeu) {
        this.jeu = jeu;
        tuiles = (List<Tuile>) getAttribute(jeu, "tuiles");
        joueurs = (List<Joueur>) getAttribute(jeu, "joueurs");
        reserve = (Map<String, ListeDeCartes>) getAttribute(jeu, "reserve");
        cartesEcartees = (ListeDeCartes) getAttribute(jeu, "cartesEcartees");
    }

    public void setJoueur(Joueur joueur) {
        this.joueur = joueur;
        main = (ListeDeCartes) getAttribute(joueur, "main");
        pioche = (ListeDeCartes) getAttribute(joueur, "pioche");
        defausse = (ListeDeCartes) getAttribute(joueur, "defausse");
        cartesEnJeu = (ListeDeCartes) getAttribute(joueur, "cartesEnJeu");
        cartesRecues = (ListeDeCartes) getAttribute(joueur, "cartesRecues");
    }

    public void setupJeu(int nbJoueurs, Plateau plateau, String... cartesRequises) {
        String[] names = {"George", "John", "Paul", "Ringo"};
        String[] nomsJoueurs = Arrays.copyOfRange(names, 0, nbJoueurs);
        
        List<String> nomsCartesDisponibles = new ArrayList<>(FabriqueListeDeCartes.getNomsCartesPreparation());
        Collections.shuffle(nomsCartesDisponibles);

        List<String> nomsCartesChoisies = new ArrayList<>();
        for (String nomCarte: cartesRequises) {
            if (nomsCartesDisponibles.contains(nomCarte)) {
                nomsCartesChoisies.add(nomCarte);
                nomsCartesDisponibles.remove(nomCarte);
            }
        };
        
        while (nomsCartesChoisies.size() < 8) {
            nomsCartesChoisies.add(nomsCartesDisponibles.remove(0));
        }

        setJeu(new IOJeu(nomsJoueurs, nomsCartesChoisies.toArray(new String[8]), plateau));
        setJoueur(joueurs.get(1));
    }

    public void setupJeu(String... cartesRequises) {
        setupJeu(2, Plateau.OSAKA, cartesRequises);
    }

    public void initialisation() {
        setAttribute(joueur, "nbJetonsRails", 20);
        main.clear();
        pioche.clear();
        defausse.clear();
        cartesEnJeu.clear();
        cartesRecues.clear();
    }

    // on autorise les exceptions produites lorsque le programme essaie de lire une
    // instruction depuis une liste d'instructions vide (tour partiellement joué).
    // Cependant, on vérifie que l'exception ne s'est pas produite pendant
    // l'exécution d'une méthode d'une carte (il faut terminer entièrement les
    // actions des cartes)
    public void jouerTourPartiel() {
        try {
            setAttribute(jeu, "joueurCourant", joueur);
            joueur.jouerTour();
        } catch (IndexOutOfBoundsException e) {
            assertEquals("fr.umontpellier.iut.trains.Joueur.jouerTour", getMethodeQuiAttendInput(e));
        }
    }

    public void jouerTourPartiel(List<String> instructions) {
        jeu.setInput(instructions);
        jouerTourPartiel();
    }

    public void jouerTourPartiel(String... instructions) {
        jeu.setInput(new ArrayList<>(List.of(instructions)));
        jouerTourPartiel();
    }

    public static String getMethodeQuiAppelle(String methodeAppelee, StackTraceElement[] stackTrace) {
        for (int i = 0; i < stackTrace.length; i++) {
            String nomMethode = stackTrace[i].getClassName() + "." + stackTrace[i].getMethodName();
            if (nomMethode.equals(methodeAppelee)) {
                return stackTrace[i + 1].getClassName() + "." + stackTrace[i + 1].getMethodName();
            }
        }
        return null;
    }

    public static String getMethodeQuiAttendInput(Exception e) {
        StackTraceElement[] stackTrace = e.getStackTrace();
        for (int i = 0; i < stackTrace.length; i++) {
            String classe = stackTrace[i].getClassName();
            String methode = stackTrace[i].getMethodName();
            if (classe.equals("fr.umontpellier.iut.trains.Joueur") && methode.equals("choisir")) {
                return stackTrace[i + 1].getClassName() + "." + stackTrace[i + 1].getMethodName();
            }
        }
        return null;
    }

    public void checkPlateau(List<Collection<Integer>> railsJoueurs, Map<Integer, Integer> gares) {
        for (int i = 0; i < 76; i++) {
            Tuile t = tuiles.get(i);
            for (int j = 0; j < railsJoueurs.size(); j++) {
                if (railsJoueurs.get(j).contains(i)) {
                    assertTrue(t.hasRail(joueurs.get(j)));
                } else {
                    assertFalse(t.hasRail(joueurs.get(j)));
                }
            }
            if (gares != null && gares.containsKey(i)) {
                assertEquals(gares.get(i), t.getNbGares());
            }
        }
    }

    public void checkPlateau(Collection<Integer> railsJoueur1, Collection<Integer> railsJoueur2,
            Map<Integer, Integer> gares) {
        if (railsJoueur1 == null) {
            railsJoueur1 = new ArrayList<>();
        }
        if (railsJoueur2 == null) {
            railsJoueur2 = new ArrayList<>();
        }
        checkPlateau(List.of(railsJoueur1, railsJoueur2), gares);
    }

    public void remplacerPlateau(PlateauExtra plateau) {
        setAttribute(jeu, "tuiles", plateau.makeTuiles());
        setAttribute(jeu, "nomVille", plateau.getNomVille());
        tuiles = (List<Tuile>) getAttribute(jeu, "tuiles");
    }

    public void assertIntegrity() {
        int nbJoueurs = joueurs.size();

        // vérification des cartes
        Map<String, Integer> counter = new HashMap<>();
        for (Carte carte : getAllCartes(jeu)) {
            counter.put(carte.getNom(), counter.getOrDefault(carte.getNom(), 0) + 1);
        }

        assertEquals(7 * joueurs.size(), counter.get("Train omnibus"));
        for (String nomCarte : jeu.getReserve().keySet()) {
            switch (nomCarte) {
                case "Ferraille":
                    assertEquals(70, counter.get(nomCarte));
                    break;
                case "Gare":
                case "Pose de rails":
                case "Train express":
                    assertEquals(20, counter.get(nomCarte));
                    break;
                default:
                    assertEquals(10, counter.get(nomCarte));
            }
        }

        // vérification des jetons
        int nbJetonsGare = 0;
        int[] nbJetonsRails = new int[joueurs.size()];
        for (Tuile tuile : jeu.getTuiles()) {
            nbJetonsGare += tuile.getNbGares();
            for (int i = 0; i < nbJoueurs; i++) {
                if (tuile.hasRail(joueurs.get(i))) {
                    nbJetonsRails[i] += 1;
                }
            }
        }

        assertEquals(30, nbJetonsGare + getNbJetonsGare(jeu));
        for (int i = 0; i < nbJoueurs; i++) {
            assertEquals(20, nbJetonsRails[i] + getNbJetonsRails(joueurs.get(i)));
        }
    }
}
