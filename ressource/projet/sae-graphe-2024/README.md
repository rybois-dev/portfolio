![](ressources/logo.jpeg)

# Projet - *Trains*


**Phase 2 : _mise en œuvre des algorithmes de graphes afin de définir des stratégies de jeu pour les joueurs et finaliser le calcul des scores_**
* **Période (prévisionnelle) :** 16 mai - 7 juin 2024
* **Cours concerné** : Surtout _Graphes_ (et un peu [Dev-Objets](https://gitlabinfo.iutmontp.univ-montp2.fr/dev-objets))
* **Enseignants :**
  [Alexandre Bazin](mailto:alexandre.bazin@umontpellier.fr),
  [Thomas Haettel](mailto:thomas.haettel@umontpellier.fr),
  [Alain Marie-Jeanne](mailto:alain.marie-jeanne@umontpellier.fr)

Ce projet est la suite de la [Phase 1 du projet _Trains_](https://gitlabinfo.iutmontp.univ-montp2.fr/dev-objets/projets/trains).

## Calendrier de travail
Pour vous aider à démarrer et faire la transition depuis la [Phase 1 du projet _Trains_](https://gitlabinfo.iutmontp.univ-montp2.fr/dev-objets/projets/trains), à partir du 16 mai 2024, une séance de TD du module [Dev-Objets](https://gitlabinfo.iutmontp.univ-montp2.fr/dev-objets) sera consacrée à l'explication du paquetage `fr.umontpellier.iut.graphes` et son lien avec le reste du code du jeu.

Quelques fonctions élémentaires de graphes seront implémentées durant cette séance de **Dev-Objets** et vous pourrez commencer à travailler en autonomie.

À partir du 21 mai, des séances SAE supplémentaires seront affectées dans le cadre du cours sur les **Graphes** pour vous aider dans vos choix algorithmiques.

## Objectifs
Dans cette *Phase 2* de la SAÉ, nous vous proposons de programmer des algorithmes de base de la théorie des graphes et quelques algorithmes spécifiques du jeu _Trains_. Les concepts de la théorie des graphes sont indépendants du jeu mais ils devraient vous permettre de programmer les différents fonctionnalités nouvelles relatives au jeu. 

La plupart des concepts relatifs à ces algorithmes vous seront ou ont déjà été présentés dans le cours sur les graphes. Certains algorithmes sont décrits "mathématiquement" dans ce cours. Pour les autres algorithmes, vous pourrez profiter des séances de TD "SAE" pour demander des suppléments d'information.

Les graphes issus du jeu _Trains_ sont modélisés par un ensemble de sommets (classe `Sommet`) encapsulé dans une classe `Graphe`. Le tout réside dans le paquetage `fr.umontpellier.iut.graphes`.


## Travail à réaliser
Dans ce qui suit, on supposera que les graphes utilisés sont des graphes simples et sans boucle. Souvent ces graphes correspondront à un plateau du jeu _Trains_ **valide**, à savoir qu'à chaque somm et, il sera possible d'associer une tuile hexagonale et les adjacences des sommets correspondront aux adjacences des tuiles dans le plateau du jeu. Les tuiles mer ne doivent pas faire partie du graphe. Attention, le plateau pourrait être différent de celui d'Osaka ou Tokyo du jeu de base _Trains_. Notamment, il pourrait être beaucoup plus grand en termes de sommets...

Un objet de la classe `Graphe` pourra être construit de plusieurs façons :
* à partir d'une collection d'objets `Sommet` (le graphe induit par ces sommets) ;
* à partir de l'ordre `n` du graphe (dans ce cas, il s'agit d'un graphe avec comme sommets des entiers entre `0` et `n-1` et sans arêtes) ;
* à partir d'un objet `Graphe graphe` et un sous-ensemble de sommets `X` de `g`, le sous-graphe induit par `X` dans `g`.
* à partir d'une instance de jeu _Trains_, correspondant aux tuiles du plateau.

On pourra avec ce modèle construire à partir du jeu des objets de type `Graphe`. Pour cela, vous allez compléter les fonctions `getGraphe()` et `getGraphe(Joueur joueur)` de la classe `Jeu`.

Voici une liste des méthodes de la classe `Graphe` que nous vous demandons de programmer dans un premier temps :

1. Renvoyer le degré d'un sommet (un entier) et calculer le degré maximal du graphe.
2. Créer un sous-graphe induit par un sous-ensemble de sommets.
3. Déterminer si le graphe est complet.
4. Déterminer si le graphe est une chaîne.
5. Déterminer si le graphe est un cycle.
6. Savoir si le graphe a des cycles.
7. Déterminer si une séquence donnée (liste d'entiers) est celle d'un graphe.
8. Renvoyer la classe de connexité d'un sommet.
9. Renvoyer l'ensemble des classes de connexité du graphe.
10. Déterminer si le graphe possède un isthme.
11. Déterminer si le graphe est un arbre.
12. Déterminer si le graphe est une forêt.
13. Fusionner un ensemble de sommets.
14. Colorier de manière gloutonne le graphe en ordonnant les sommets par ordre décroissant des degrés.
15. Proposer un algorithme de coloration du graphe de plateau (avec un nombre minimal de couleurs).
16. Déterminer le coût minimal pour relier par des rails deux sommets (correspondant aux "hexagones" du plateau du jeu).
17. Déterminer le coût minimal pour relier par des rails un sous-ensemble de sommets à un autre sommet.
18. Déterminer si un graphe donné admet un sous-graphe isomorphe à un graphe complet d'au plus **k** sommets.

**Questions bonus :**

19. Déterminer si un graphe donné admet un sous-graphe isomorphe à un graphe donné.
20. Étant donné un joueur et deux tuiles **s** et **t** (correspondant à deux sommets dans le graphe), déterminer la taille d'un plus petit ensemble _critique_ de tuiles. Un ensemble **X** est critique si le joueur ne peut pas relier **s** et **t** sans utiliser une des tuiles de **X**.

Pour réaliser les algorithmes demandés, vous pouvez ajouter des fonctions et attributs qui vous paraissent nécessaires dans les différentes classes et paquetages du projet. En revanche, sauf indication de la part des enseignants, **les signatures des méthodes et attributs qui vous sont fournis doivent rester inchangées**. 

**D'autres fonctions pourraient être ajoutées par la suite. Vous serez informés s'il y a des nouveautés (surveillez le [Forum Piazza](https://piazza.com/class/lrahb0patze3u4)).**

### Concernant les tests
Quelques tests publics vous sont fournis dans la classe `GrapheTest` du répertoire de test (paquetage `fr.umontpellier.iut.graphes`). Vous pouvez les utiliser pour vous aider à démarrer. Comme d'habitude, vous devriez écrire beaucoup de tests unitaires pour vérifier la bonne implémentation de vos algorithmes.


**Pour des questions :**
* Le forum [Piazza](https://piazza.com/class/lrahb0patze3u4) - à privilégier lorsque vous avez des questions sur le projet. Il s'agit du même forum que pour le cours de [Dev-Objets](https://gitlabinfo.iutmontp.univ-montp2.fr/dev-objets) (et que vous avez utilisé pour la [Phase 1](https://gitlabinfo.iutmontp.univ-montp2.fr/dev-objets/projets/trains)). L'avantage du forum est que tous les étudiants, les enseignants du cours de Graphes, mais aussi les enseignants du cours de [Dev-Objets](https://gitlabinfo.iutmontp.univ-montp2.fr/dev-objets), pourront répondre à vos questions.
* [Email](mailto:alain.marie-jeanne@umontpellier.fr) pour une question d'ordre privée concernant le projet.

## Architecture du code

Le code qui vous est fourni contient la correction du projet correspondant à la [Phase 1 du projet _Trains_](https://gitlabinfo.iutmontp.univ-montp2.fr/dev-objets/projets/trains). La mécanique du jeu est restée intacte dans le paquetage `fr.umontpellier.iut.trains`. Quelques nouvelles fonctions simples ont été ajoutées (dans `Jeu` et dans les classes de tuiles) pour faciliter l'extraction des graphes à partir du plateau de jeu.

Les méthodes que vous devez implémenter, comme d'habitude, sont celles qui lèvent des exceptions de type `RuntimeException` avec le message `"Méthode à implémenter"`. Elles se trouvent dans les classes `Graphe` et `Sommet` du paquetage `fr.umontpellier.iut.graphes`.


## Évaluation

L'évaluation du projet se fera, en premier lieu à l'aide de tests unitaires automatisés. Un petit jeu de tests vous est fourni (comme d'habitude dans le répertoire `src/test/java`) pour que vous puissiez démarrer. Puis, nous utiliserons un second jeu de tests (secret) pour l'évaluation finale.

Il est donc attendu que vous écriviez beaucoup de tests unitaires pour vérifier par vous-mêmes que le projet se comporte correctement dans les différents cas particuliers qui peuvent se produire.

**Remarque importante** : puisque l'évaluation des rendus se fait par des tests automatisés, **les projets qui ne compilent pas ou qui ne respectent pas les signatures données, seront automatiquement rejetés** et la note sera 0.

Le second volet de l'évaluation sera une interrogation écrite individuelle qui mesurera votre implication réelle dans le projet.

**Le plus important** : La note finale de la Phase 2 sera **le minimum des 2 notes**.

## Remarque concernant les Phases 2 et 3 du projet

Comme indiqué précédemment, cette phase (Phase 2) se déroule en parallèle de la Phase 3, où vous devez programmer une interface graphique sous JavaFX. La Phase 3 démarrera vers la fin du mois de mai et est complètement indépendante de la Phase 2. Il est important que les projets GitLab des Phases 2 et 3 restent distincts.

Lorsque vos différentes fonctions de calculs utilisant les graphes marcheront, vous pourrez (si vous le souhaitez), ajouter le code correspondant dans votre projet JavaFX (de la Phase 3). **Nous vous déconseillons fortement de le faire avant la fin de la Phase 2 !**   

