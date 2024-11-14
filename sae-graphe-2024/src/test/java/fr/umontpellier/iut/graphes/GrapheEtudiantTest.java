package fr.umontpellier.iut.graphes;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class GrapheEtudiantTest {
    private final Sommet a = new Sommet.SommetBuilder().setIndice(0).createSommet();
    private final Sommet b = new Sommet.SommetBuilder().setIndice(1).createSommet();
    private final Sommet c = new Sommet.SommetBuilder().setIndice(2).createSommet();
    private final Sommet d = new Sommet.SommetBuilder().setIndice(3).createSommet();
    private final Sommet e = new Sommet.SommetBuilder().setIndice(4).createSommet();
    private final Sommet f = new Sommet.SommetBuilder().setIndice(5).createSommet();
    private final Sommet g = new Sommet.SommetBuilder().setIndice(6).createSommet();

    @Test
    public void testEstUneChaine() {
        Graphe graphe = new Graphe();

        assertTrue(graphe.estChaine());

        graphe.ajouterSommet(a);
        graphe.ajouterSommet(b);
        graphe.ajouterSommet(c);
        graphe.ajouterSommet(d);

        graphe.ajouterArete(a, b);
        graphe.ajouterArete(b, c);
        graphe.ajouterArete(c, d);

        assertTrue(graphe.estChaine(),"A-B-C-D");

        // Ajouter une arête supplémentaire pour briser la chaîne
        graphe.ajouterArete(a, c);

        assertFalse(graphe.estChaine(),"A-B-C-D-A ou A-B-C-A = cycle or un cycle n'est pas une chaine");
    }
    @Test
    public void testSequenceEstGraphe() {
        assertTrue(Graphe.sequenceEstGraphe(new ArrayList<Integer>()),"séquence vide est un graphe valide");

        assertTrue(Graphe.sequenceEstGraphe(List.of(0)),"séquence avec un seul nœud est un graphe valide");

        assertTrue(Graphe.sequenceEstGraphe(List.of(0,1,1)),"séquence avec deux nœuds et une arête est un graphe valide");

        assertTrue(Graphe.sequenceEstGraphe(List.of(0,1,1,1,2,1)),"séquence avec trois sommets et deux arêtes est un graphe valide");

        assertFalse(Graphe.sequenceEstGraphe(List.of(0,1,1,1,1,1)),"séquence avec une boucle est un graphe invalide");

        assertFalse(Graphe.sequenceEstGraphe(List.of(0,1,1,1,2,1,2,2,1)),"séquence avec des arêtes multiples est un graphe invalide");

        assertFalse(Graphe.sequenceEstGraphe(List.of(0,1,1,2,1)),"séquence avec des sommets manquants est un graphe invalide");

        assertFalse(Graphe.sequenceEstGraphe(List.of(-1,0,1,1,2,1)),"séquence avec des valeurs négatives est un graphe invalide");
    }
    @Test
    public void testEstComplet() {
        Graphe graphe = new Graphe();

        assertFalse(graphe.estComplet(),"le graphe vide n'est pas complet car il n'a ni d'arètes ni de sommets");

        Sommet a = new Sommet.SommetBuilder().setIndice(1).createSommet();
        Sommet b = new Sommet.SommetBuilder().setIndice(2).createSommet();
        Sommet c = new Sommet.SommetBuilder().setIndice(3).createSommet();

        graphe.ajouterSommet(a);
        graphe.ajouterSommet(b);
        graphe.ajouterSommet(c);

        assertFalse(graphe.estComplet(),"le graphe avec des sommets mais sans arêtes n'est pas complet");

        // Ajouter des arêtes au graphe pour le rendre complet
        graphe.ajouterArete(a, b);
        graphe.ajouterArete(a, c);
        graphe.ajouterArete(b, c);

        assertTrue(graphe.estComplet(),"le graphe est maintenant complet");

        // Ajouter une arête supplémentaire pour briser la complétude
        graphe.ajouterArete(a, a);

        assertFalse(graphe.estComplet(),"le graphe n'est plus complet");
    }
    @Test
    public void testEst() {
        Graphe graphe = new Graphe();

        assertTrue(graphe.estForet(),"graphe est une forêt car il n'a pas de cycle");

        Sommet a = new Sommet.SommetBuilder().setIndice(0).createSommet();
        Sommet b = new Sommet.SommetBuilder().setIndice(1).createSommet();
        Sommet c = new Sommet.SommetBuilder().setIndice(2).createSommet();
        Sommet d = new Sommet.SommetBuilder().setIndice(3).createSommet();

        graphe.ajouterSommet(a);
        graphe.ajouterSommet(b);
        graphe.ajouterSommet(c);
        graphe.ajouterSommet(d);

        assertTrue(graphe.estForet(),"graphe est une forêt car il n'a pas de cycle");

        // Ajouter des arêtes au graphe pour former un arbre
        graphe.ajouterArete(a, b);
        graphe.ajouterArete(a, c);
        graphe.ajouterArete(c, d);

        assertTrue(graphe.estForet(),"le graphe est maintenant une forêt car pas de cycle");

        // Ajouter une arête supplémentaire pour former un cycle
        graphe.ajouterArete(b, d);

        assertFalse(graphe.estForet()," le graphe n'est plus une forêt");

        // Supprimer une arête pour briser le cycle
        graphe.supprimerArete(b, d);

        // Ajouter un autre arbre au graphe
        graphe.ajouterSommet(e);
        graphe.ajouterSommet(f);
        graphe.ajouterArete(e,f);

        assertTrue(graphe.estForet(),"le graphe est une forêt avec deux arbres");
    }
    @Test
    public void testPossedeUnIsthme() {
        Graphe graphe = new Graphe();

        assertFalse(graphe.possedeUnIsthme());

        Sommet a = new Sommet.SommetBuilder().setIndice(0).createSommet();
        Sommet b = new Sommet.SommetBuilder().setIndice(1).createSommet();
        Sommet c = new Sommet.SommetBuilder().setIndice(2).createSommet();
        Sommet d = new Sommet.SommetBuilder().setIndice(3).createSommet();

        graphe.ajouterSommet(a);
        graphe.ajouterSommet(b);
        graphe.ajouterSommet(c);
        graphe.ajouterSommet(d);

        assertFalse(graphe.possedeUnIsthme());

        // Ajouter des arêtes au graphe pour former un cycle
        graphe.ajouterArete(a, b);
        graphe.ajouterArete(b, c);
        graphe.ajouterArete(c, d);
        graphe.ajouterArete(d, a);

        assertFalse(graphe.possedeUnIsthme(),"cycle = ne possède pas d'isthme");

        // Supprimer une arête pour briser le cycle et former un isthme
        graphe.supprimerArete(a, d);

        assertTrue(graphe.possedeUnIsthme());
    }
    @Test
    public void testEstConnexe() {
        Graphe graphe = new Graphe();

        Sommet a = new Sommet.SommetBuilder().setIndice(0).createSommet();
        Sommet b = new Sommet.SommetBuilder().setIndice(1).createSommet();
        Sommet c = new Sommet.SommetBuilder().setIndice(2).createSommet();
        Sommet d = new Sommet.SommetBuilder().setIndice(3).createSommet();

        //assertTrue(graphe.estConnexe()); //TODO demander si le projet prend en compte qu'un graphe vide estConnexe ou non

        graphe.ajouterSommet(a);
        graphe.ajouterSommet(b);
        graphe.ajouterSommet(c);
        graphe.ajouterSommet(d);

        assertFalse(graphe.estConnexe(),"Les sommets ne sont pas connectés entre eux");

        //relie tous les points pour que graphe soit connexe
        graphe.ajouterArete(a, b);
        graphe.ajouterArete(b, c);
        graphe.ajouterArete(c, d);

        assertTrue(graphe.estConnexe(),"On peut accèdé à tous les sommets depuis n'import quelle sommet (tous les sommets sont connecté)");

        //cree deux composantes donc plus connexe
        graphe.supprimerArete(b, c);

        assertFalse(graphe.estConnexe(),"Le graphe à deux composantes");

        // Ajouter arête supplémentaire => autre chemin entre les deux composantes
        graphe.ajouterArete(b, d);

        assertTrue(graphe.estConnexe(),"présence d'une composante donc le graphe est connexe");
    }
    @Test
    public void testDegre() {
        Graphe graphe = new Graphe();

        Sommet a = new Sommet.SommetBuilder().setIndice(0).createSommet();
        Sommet b = new Sommet.SommetBuilder().setIndice(1).createSommet();
        Sommet c = new Sommet.SommetBuilder().setIndice(2).createSommet();
        Sommet d = new Sommet.SommetBuilder().setIndice(3).createSommet();
        Sommet e = new Sommet.SommetBuilder().setIndice(4).createSommet();


        //cas où on ajoute un sommet sans utiliser ajouterSommet()
        Sommet s = new Sommet(e);
        assertEquals(0, graphe.degre(s));

        graphe.ajouterSommet(a);
        graphe.ajouterSommet(b);
        graphe.ajouterSommet(c);
        graphe.ajouterSommet(d);

        //sommet isolé = 0
        assertEquals(0, graphe.degre(a),"chaque sommet n'a pas de voisin donc pas de degree");

        graphe.ajouterArete(a, b);
        graphe.ajouterArete(b, c);
        graphe.ajouterArete(c, d);
        graphe.ajouterArete(d, a);

        assertEquals(2, graphe.degre(a),"A à comme voisin : B,D donc = 2 degrés");
        assertEquals(2, graphe.degre(b),"B à comme voisin : A,C donc = 2 degrés");
        assertEquals(2, graphe.degre(c),"C à comme voisin : B,D donc = 2 degrés");
        assertEquals(2, graphe.degre(d),"D à comme voisin : A,C donc = 2 degrés");

        // Ajouter une boucle à un sommet
        graphe.ajouterArete(b, b);

        assertEquals(3, graphe.degreMax(),"B à comme sommet : A,C ainsi que lui-même (A:2,C:2,D:2)");
        assertEquals(3, graphe.degre(b),"B à comme sommet : A,C ainsi que lui-même");
    }
    @Test
    public void testDegreMax(){
        Graphe graphe = new Graphe();

        assertEquals(0,graphe.degreMax());

        graphe.ajouterSommet(a);
        graphe.ajouterSommet(b);
        graphe.ajouterSommet(c);
        graphe.ajouterSommet(d);
        graphe.ajouterSommet(e);
        graphe.ajouterSommet(f);

        assertEquals(0,graphe.degreMax(),"aucun sommet n'as de voisin");

        graphe.ajouterArete(a,b);
        graphe.ajouterArete(a,b); //test si éventuelle problème de doublon
        graphe.ajouterArete(b,a);
        assertEquals(1,graphe.degreMax(),"A et B on 1 degre donc degreMax est de 1");

        graphe.ajouterArete(a,c);
        graphe.ajouterArete(a,d);

        assertEquals(3, graphe.degreMax(),"A à comme voisin : B, C, D ; B: A ; C: A , D: A");

        graphe.ajouterArete(b,f);
        graphe.ajouterArete(c,d);
        graphe.ajouterArete(c,e);
        graphe.ajouterArete(f,b);
        graphe.ajouterArete(b,c);

        assertEquals(4,graphe.degreMax(),"A:B,C,D (3); B:A,F (2); C:A,B,D,E (4); D:A (1); E:C (1); F:B (1)");

        graphe.supprimerArete(b,a);

        assertEquals(4,graphe.degreMax(),"A:C,D (2); B:F (1); C:A,B,D,E (4); D:A (1); E:C (1); F:B (1)");

        graphe.supprimerArete(a,c);

        assertEquals(3,graphe.degreMax(),"A:D (1); B:F (1); C:B,D,E (3); D;A (1); E:C (1); F:B (1)");

        graphe.supprimerArete(b,f);
        graphe.supprimerArete(c,d);
        graphe.supprimerArete(c,e);
        graphe.supprimerArete(f,b);
        graphe.supprimerArete(b,c);

        assertEquals(1, graphe.degreMax(),"A:D (1); B:(0); C:(0); D;A (1); E:(0); F:(0)");
    }

    @Test
    public void testGetDistance() {
        Graphe graphe = new Graphe();

        Sommet a = new Sommet.SommetBuilder().setIndice(0).setSurcout(1).createSommet();
        Sommet b = new Sommet.SommetBuilder().setIndice(1).setSurcout(1).createSommet();
        Sommet c = new Sommet.SommetBuilder().setIndice(2).setSurcout(1).createSommet();
        Sommet d = new Sommet.SommetBuilder().setIndice(3).setSurcout(1).createSommet();

        // Vérifier que la distance entre deux sommets dans un graphe vide est nulle
        Sommet s1 = new Sommet(f);
        Sommet s2 = new Sommet(e);

        graphe.ajouterSommet(a);
        graphe.ajouterSommet(b);
        graphe.ajouterSommet(c);
        graphe.ajouterSommet(d);

        graphe.ajouterArete(a, b);
        graphe.ajouterArete(b, c);
        graphe.ajouterArete(c, d);
        graphe.ajouterArete(d, a);

        assertEquals(1, graphe.getDistance(a, b),"la distance entre deux sommets adjacents est égale à 1");
        assertEquals(1, graphe.getDistance(b, c),"la distance entre deux sommets adjacents est égale à 1");
        assertEquals(1, graphe.getDistance(c, d),"la distance entre deux sommets adjacents est égale à 1");
        assertEquals(1, graphe.getDistance(d, a),"la distance entre deux sommets adjacents est égale à 1");

        // Vérifier que la distance entre deux sommets non-adjacents est égale à la longueur du plus court chemin
        assertEquals(2, graphe.getDistance(a, c));
        assertEquals(2, graphe.getDistance(b, d));

        graphe.supprimerArete(a, d);

        assertEquals(3, graphe.getDistance(a, d));
        assertEquals(1, graphe.getDistance(b, c));

        graphe.ajouterArete(a, c);

        // Vérifier que la distance entre les deux sommets est mise à jour correctement
        assertEquals(1, graphe.getDistance(a, c));
        assertEquals(2, graphe.getDistance(a, d));
        assertEquals(1, graphe.getDistance(b, c));
    }

    @Test
    public void testPossedeSousGrapheComplet() {
        Graphe graphe = new Graphe();
        assertFalse(graphe.possedeSousGrapheComplet(2),"Un graphe vide ne peut pas avoir un sous graphe complet");

        graphe.ajouterSommet(a);
        assertFalse(graphe.possedeSousGrapheComplet(2),"Un graphe avec un seul sommet ne peut pas être un sous graphe complet");

        // Ajouter une arête entre deux sommets
        graphe.ajouterSommet(b);
        graphe.ajouterArete(a, b);
        assertTrue(graphe.possedeSousGrapheComplet(2),"A-B");
        assertFalse(graphe.possedeSousGrapheComplet(3),"A-B (on n'a pas de 3ème sommets)");

        // Ajouter un troisième sommet sans arête
        graphe.ajouterSommet(c);
        assertTrue(graphe.possedeSousGrapheComplet(2),"On a toujours le sous graphe A-B");
        assertFalse(graphe.possedeSousGrapheComplet(3),"A-B C  (A et B ne pas sont pas relier à C)");

        // Ajouter une arête entre le deuxième et le troisième sommet
        graphe.ajouterArete(b, c);
        assertTrue(graphe.possedeSousGrapheComplet(2),"A-B et B-C sont sous graphe complet");
        assertFalse(graphe.possedeSousGrapheComplet(3),"A-B-C (A n'est pas relier à C)");

        // Ajouter un quatrième sommet sans arête
        graphe.ajouterSommet(d);
        graphe.ajouterArete(a,c);
        assertTrue(graphe.possedeSousGrapheComplet(2),"A-B , B-C , A-C sont des sous graphes complet");
        assertTrue(graphe.possedeSousGrapheComplet(3),"A-B-A , B-C-B, C-B-C , ... (on a un sous graphe complet d'ordre 3)");
        assertFalse(graphe.possedeSousGrapheComplet(4),"D est isolé sachant qu'un a un 4 sommet on ne peut pas avoir un SGC d'ordre 4");

        // Ajouter une arête entre le troisième et le quatrième sommet
        graphe.ajouterArete(c,d);
        graphe.ajouterArete(a,d);
        graphe.ajouterArete(d,b);
        assertTrue(graphe.possedeSousGrapheComplet(2),"A-B , B-C , A-C , C-D , A-D , D-B sont des sous graphes complet");
        assertTrue(graphe.possedeSousGrapheComplet(3),"A-B-A , A-D-A , ... (on a un sous graphe complet d'ordre 3)");
        assertTrue(graphe.possedeSousGrapheComplet(4),"A:B,C,D ; B:A,C,D ; C:A,B,D ; D:A,B,C donc il y a un SGC d'ordre 4");
    }
    @Test
    public void testEstCycle() {
        Graphe graphe = new Graphe();

        Sommet a = new Sommet.SommetBuilder().setIndice(0).createSommet();
        Sommet b = new Sommet.SommetBuilder().setIndice(1).createSommet();
        Sommet c = new Sommet.SommetBuilder().setIndice(2).createSommet();
        Sommet d = new Sommet.SommetBuilder().setIndice(3).createSommet();

        assertFalse(graphe.estCycle(), "Le graphe vide ne devrait pas être un cycle");

        graphe.ajouterSommet(a);
        assertFalse(graphe.estCycle(), "Un graphe avec un seul sommet ne devrait pas être un cycle");

        graphe.ajouterSommet(b);
        graphe.ajouterArete(a, b);
        assertFalse(graphe.estCycle(), "Un graphe avec deux sommets et une arête ne devrait pas être un cycle");

        //graphe.ajouterArete(a, a);
        //assertTrue(graphe.estCycle(), "Un graphe avec une boucle devrait être un cycle");

        graphe.ajouterSommet(c);
        graphe.ajouterArete(b, c);
        assertFalse(graphe.estCycle(), "Un graphe avec trois sommets et deux arêtes ne devrait pas être un cycle");

        graphe.ajouterSommet(d);
        graphe.ajouterArete(c, d);
        assertFalse(graphe.estCycle(), "Un graphe avec quatre sommets et quatre arêtes ne devrait pas être un cycle");
    }
    @Test
    public void testPossedeUnCycle() {
        Graphe graphe = new Graphe();

        Sommet a = new Sommet.SommetBuilder().setIndice(0).createSommet();
        Sommet b = new Sommet.SommetBuilder().setIndice(1).createSommet();
        Sommet c = new Sommet.SommetBuilder().setIndice(2).createSommet();

        assertFalse(graphe.possedeUnCycle(), "Le graphe vide ne devrait pas avoir de cycle");

        graphe.ajouterSommet(a);
        graphe.ajouterSommet(b);
        graphe.ajouterSommet(c);

        assertFalse(graphe.possedeUnCycle());

        graphe.ajouterArete(a, b);
        assertFalse(graphe.possedeUnCycle());

        graphe.ajouterArete(b, c);
        assertFalse(graphe.possedeUnCycle());

        graphe.ajouterArete(a, c);
        assertTrue(graphe.possedeUnCycle());

    }
    @Test
    public void testGetColorationGlxoutonne() {
        Graphe graphe = new Graphe();

        Sommet a = new Sommet.SommetBuilder().setIndice(0).createSommet();
        Sommet b = new Sommet.SommetBuilder().setIndice(1).createSommet();
        Sommet c = new Sommet.SommetBuilder().setIndice(2).createSommet();
        Sommet d = new Sommet.SommetBuilder().setIndice(3).createSommet();
        Sommet e = new Sommet.SommetBuilder().setIndice(4).createSommet();
        Sommet f = new Sommet.SommetBuilder().setIndice(5).createSommet();
        Sommet g = new Sommet.SommetBuilder().setIndice(6).createSommet();

        graphe.ajouterSommet(a);
        graphe.ajouterSommet(b);
        graphe.ajouterSommet(c);
        graphe.ajouterSommet(d);
        graphe.ajouterSommet(e);
        graphe.ajouterSommet(f);
        graphe.ajouterSommet(g);

        // Forme un cycle de longueur 4
        graphe.ajouterArete(a, b);
        graphe.ajouterArete(b, c);
        graphe.ajouterArete(c, d);
        graphe.ajouterArete(d, a);

        // Augmenter le degré maximum des sommets
        graphe.ajouterArete(a, e);
        graphe.ajouterArete(b, f);
        graphe.ajouterArete(c, g);

        // Création d'un graphe
        Graphe graphe1 = new Graphe();
        graphe1.ajouterSommet(a);
        graphe1.ajouterSommet(b);
        graphe1.ajouterSommet(c);
        graphe1.ajouterSommet(d);

        graphe1.ajouterArete(a, b);
        graphe1.ajouterArete(a, d);
        graphe1.ajouterArete(b, c);
        graphe1.ajouterArete(c, d);

        // Exécution de la fonction de coloration gloutonne
        Map<Integer, Set<Sommet>> coloration1 = graphe1.getColorationGloutonne();

        // Vérification de la coloration
        assertEquals(2, coloration1.size()); // Deux couleurs doivent être utilisées
        assertTrue(coloration1.get(1).contains(a));
        assertTrue(coloration1.get(1).contains(c));
        assertTrue(coloration1.get(2).contains(b));
        assertTrue(coloration1.get(2).contains(d));

//        // Vérifier que la coloration propre optimale est correcte
//        Map<Integer, Set<Sommet>> coloration = graphe.getColorationGloutonne();
//        assertEquals(3, coloration.size(),"la coloration contient 3 entrées");
//
//        // Vérifier que chaque ensemble de sommets est correct
//        assertTrue(coloration.get(1).containsAll(Set.of(a, b, c)),"l'ensemble 1 contient les sommets a, d et f");
//        assertTrue(coloration.get(2).containsAll(Set.of(d)),"l'ensemble 2 contient les sommets b, c, e et g");
//        assertTrue(coloration.get(3).containsAll(Set.of(e, f, g)),"l'ensemble 3 est vide");
    }
    @Test
    public void testGetColorationPropreOptimiser() {
        Graphe graphe = new Graphe();

        Sommet a = new Sommet.SommetBuilder().setIndice(0).createSommet();
        Sommet b = new Sommet.SommetBuilder().setIndice(1).createSommet();
        Sommet c = new Sommet.SommetBuilder().setIndice(2).createSommet();
        Sommet d = new Sommet.SommetBuilder().setIndice(3).createSommet();

        // Création d'un graphe
        Graphe graphe1 = new Graphe();
        graphe1.ajouterSommet(a);
        graphe1.ajouterSommet(b);
        graphe1.ajouterSommet(c);
        graphe1.ajouterSommet(d);

        graphe1.ajouterArete(a, b);
        graphe1.ajouterArete(a, d);
        graphe1.ajouterArete(b, c);
        graphe1.ajouterArete(c, d);
        graphe1.ajouterArete(a, c);
        graphe1.ajouterArete(b, d);

        // Exécution de la fonction de coloration gloutonne
        Map<Integer, Set<Sommet>> coloration1 = graphe1.getColorationPropreOptimale();
        System.out.println(coloration1);
        // Vérification de la coloration
        //assertEquals(2, coloration1.size()); // Deux couleurs doivent être utilisées
        assertEquals(1,a.getNumColoration());
        assertEquals(1,c.getNumColoration());
        assertEquals(2,b.getNumColoration());
        assertEquals(2,d.getNumColoration());
    }
    @Disabled
    @Test
    public void testExceptions() {
        Graphe graphe = new Graphe();

        assertFalse(graphe.ajouterSommet(-1),"On ne devrait pas pouvoir ajouter de sommet à indice négatif");

        graphe.ajouterSommet(0);
        assertFalse(graphe.ajouterSommet(0),"On ne devrait pas ajouter un indice de sommet déjà existant");

        Sommet s1 = new Sommet(a);
        Sommet s2 = new Sommet(b);
        assertThrows(IllegalArgumentException.class, () -> graphe.ajouterArete(s1, s2),"on ne devrais pas pouvoir ajouter une arète à un sommet innexistant dans le graphe");

        assertThrows(IllegalArgumentException.class, () -> graphe.ajouterArete(s1, graphe.getSommet(0)), "on ne peut pas ajouter une arête entre un sommet qui n'existe pas et un sommet qui existe");
        assertThrows(IllegalArgumentException.class, () -> graphe.ajouterArete(graphe.getSommet(0), s1),"on ne peut pas ajouter une arête entre un sommet qui n'existe pas et un sommet qui existe");

        graphe.ajouterSommet(1);
        graphe.ajouterArete(graphe.getSommet(0), graphe.getSommet(1));
        assertThrows(IllegalArgumentException.class, () -> graphe.ajouterArete(graphe.getSommet(0), graphe.getSommet(1)),"on ne peut pas ajouter une arête entre deux sommets qui sont déjà reliés par une arête");

        assertThrows(IllegalArgumentException.class, () -> graphe.supprimerArete(s1, s2),"on ne peut pas supprimer une arête entre deux sommets qui n'existent pas dans le graphe");

        assertThrows(IllegalArgumentException.class, () -> graphe.supprimerArete(s1, graphe.getSommet(0)),"on ne peut pas supprimer une arête entre un sommet qui n'existe pas et un sommet qui existe");
        assertThrows(IllegalArgumentException.class, () -> graphe.supprimerArete(graphe.getSommet(0), s1),"on ne peut pas supprimer une arête entre un sommet qui n'existe pas et un sommet qui existe");

        assertThrows(IllegalArgumentException.class, () -> graphe.supprimerArete(graphe.getSommet(0), graphe.getSommet(2)),"on ne peut pas supprimer une arête entre deux sommets qui ne sont pas reliés par une arête");
    }

    @Test
    public void testGetEnsembleCritique() {
        Graphe graphe = new Graphe();

        Sommet a = new Sommet.SommetBuilder().setIndice(0).createSommet();
        Sommet b = new Sommet.SommetBuilder().setIndice(1).createSommet();
        Sommet c = new Sommet.SommetBuilder().setIndice(2).createSommet();
        Sommet d = new Sommet.SommetBuilder().setIndice(3).createSommet();

        assertFalse(graphe.possedeUnCycle(), "Le graphe vide ne devrait pas avoir de cycle");

        graphe.ajouterSommet(a);
        graphe.ajouterSommet(b);
        graphe.ajouterSommet(c);
        graphe.ajouterSommet(d);

        graphe.ajouterArete(a, b);
        graphe.ajouterArete(b, c);
        graphe.ajouterArete(c, d);
        graphe.ajouterArete(d, a);

        assertEquals(Set.of(a, b), graphe.getEnsembleCritique(a, b));

        assertEquals(Set.of(a, b, c).size(), graphe.getEnsembleCritique(a, c).size());

        assertEquals(Set.of(a, d), graphe.getEnsembleCritique(a, d));
//
//        graphe.ajouterArete(a, c);
//        assertTrue(graphe.possedeUnCycle());

    }
    @Test
    public void testGrosGraphe(){
        Graphe g = new Graphe();

        for(int i =0 ; i<1000 ; i++){
            g.ajouterSommet(i);
            if(i!=0){
                g.ajouterArete(g.getSommet(i),g.getSommet(i+1));
            }
        }

        assertFalse(g.estCycle(),"il ne peut pas avoir de cycle car il n'y a pas de répetition de sommet");
        //assertTrue(g.estChaine(),"A-B-C-D- ... (pas de retour sur un sommet c'est donc une chaine)");
        //assertFalse(g.possedeUnCycle(),"il ne peut pas avoir de cycle car il n'y a pas d'arètes");
        assertFalse(g.estConnexe(),"le graphe n'est connexe (2 degrès 1)");
        assertTrue(g.estForet(), "le graphe est une foret car il n'a pas de cycle");
        //assertTrue(g.possedeSousGrapheComplet(2),"le sousGraphe possède plusieurs sous graphe complet de 2");
        assertFalse(g.possedeUnIsthme(),"le graphe n'est pas un isthme car le graphe n'est pas connexe");
        assertEquals(1000,g.getNbSommets());
        assertEquals((1000*(1000-1))/2, g.getNbAretes());
    }
}
