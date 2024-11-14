package fr.umontpellier.iut.trainsJavaFX.vues;
import fr.umontpellier.iut.trainsJavaFX.GestionJeu;
import fr.umontpellier.iut.trainsJavaFX.ICarte;
import fr.umontpellier.iut.trainsJavaFX.IJeu;
import fr.umontpellier.iut.trainsJavaFX.IJoueur;
import fr.umontpellier.iut.trainsJavaFX.mecanique.cartes.Carte;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

/**
 * Cette classe correspond à la fenêtre principale de l'application.
 * <p>
 * Elle est initialisée avec une référence sur la partie en cours (Jeu).
 * <p>
 * On y définit les bindings sur les éléments internes qui peuvent changer
 * (le joueur courant, ses cartes en main, son score, ...)
 * ainsi que les listeners à exécuter lorsque ces éléments changent
 */
public class VueJoueurCourant extends VBox{

    private final IJeu jeu;
    private Label instruction;
    private Label nomJoueur;
    private Button passer;
    private HBox hbox;
    private Label argent;
    private Label nbDeck;
    private Label nbDefausse;
    private Label pointRail;
    private Label nbRails;
    private Label score;
    private FlowPane carteAchetable;
    private VueCarte vueCarte;
    private CouleursJoueurs couleursJoueurs;

    // les listeners
    private ChangeListener<? super Number> argentListener;
    private ChangeListener<? super Number> JetonsRailListener;
    private ChangeListener<? super Number> scoreListener;
    private ListChangeListener<Carte> piocheListener;
    private ListChangeListener<Carte> defausseListener;

    public VueJoueurCourant(IJeu jeu) {
        this.jeu = jeu;
        couleursJoueurs= new CouleursJoueurs();
        carteAchetable = new FlowPane(); //HBox des cartes à afficher
        getChildren().add(carteAchetable);

        int tailleIcone = 15;

        ImageView sous = new ImageView("images/boutons/coins.png"); // icone argent
        sous.setFitHeight(tailleIcone);
        sous.setPreserveRatio(true);

        ImageView deck = new ImageView("images/boutons/deck.png"); // icone deck
        deck.setFitHeight(tailleIcone);
        deck.setPreserveRatio(true);

        ImageView defausse = new ImageView("images/boutons/defausse.png"); // icone defausse
        defausse.setFitHeight(tailleIcone);
        defausse.setPreserveRatio(true);

        ImageView nbRailImage = new ImageView("images/boutons/rail.png"); //icone nbrail
        nbRailImage.setFitHeight(tailleIcone);
        nbRailImage.setPreserveRatio(true);

        ImageView imageScore = new ImageView("images/boutons/score.png"); // iconte
        imageScore.setFitHeight(tailleIcone);
        imageScore.setPreserveRatio(true);


        ImageView imagePasser = new ImageView("images/boutons/passer.png");

        this.nbDeck = new Label();
        this.nbDefausse = new Label();
        this.nbRails = new Label();
        this.argent = new Label();
        this.score = new Label();

        HBox hBoxDeck = new HBox(deck, nbDeck);
        hBoxDeck.setAlignment(Pos.CENTER);
        hBoxDeck.setSpacing(5);
        HBox hBoxDefausse = new HBox(defausse, nbDefausse);
        hBoxDefausse.setAlignment(Pos.CENTER);
        hBoxDefausse.setSpacing(5);
        HBox hBoxSous = new HBox(sous, argent);
        hBoxSous.setAlignment(Pos.CENTER);
        hBoxSous.setSpacing(5);
        HBox hBoxRails = new HBox(nbRailImage, nbRails);
        hBoxRails.setAlignment(Pos.CENTER);
        hBoxRails.setSpacing(5);
        HBox hBoxScore = new HBox(imageScore, score);
        hBoxScore.setAlignment(Pos.CENTER);
        hBoxScore.setSpacing(5);

        this.nomJoueur = new Label();
        HBox elementPage = new HBox(
                nomJoueur,
                hBoxSous,
                hBoxRails,
                hBoxScore,
                hBoxDeck,
                hBoxDefausse
        );
        elementPage.setSpacing(10);
        elementPage.setAlignment(Pos.CENTER);
        getChildren().add(elementPage);

        this.instruction = new Label();
        instruction.setFont(new Font(20));
        this.passer = new Button();
        imagePasser.setFitHeight(30);
        imagePasser.setFitWidth(30);
        this.passer.setGraphic(imagePasser);
        HBox instructionAvecPasser = new HBox(passer, instruction);
        instructionAvecPasser.setSpacing(50);
        instructionAvecPasser.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        instructionAvecPasser.setAlignment(Pos.CENTER);
        instructionAvecPasser.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
        getChildren().add(instructionAvecPasser);
        this.hbox = new HBox();
        getChildren().add(hbox);

        // argent
        this.argentListener = ((src, and, nouv) -> updateAll());
        // nb jetons rails
        this.JetonsRailListener = ((src, and, nouv) -> updateAll());
        // score
        this.scoreListener = ((src, and, nouv) -> updateAll());
        // pioche
        this.piocheListener = (change -> updateAll());
        // defausse
        this.defausseListener = (change -> updateAll());

        creerBindings();
    }
    //utiliser un gridpane pour afficher la réserve et StackPane
    public void creerBindings() {

        instruction.textProperty().bind(jeu.instructionProperty());
        //nomJoueur.textProperty().bind(jeu.joueurCourantProperty().asString());
        passer.setOnMouseClicked(actionPasserParDefaut);
        jeu.joueurCourantProperty().addListener((observableValue, iJoueur, t1) -> {
            if (iJoueur != null) {
                iJoueur.mainProperty().removeListener(cartesListener);
            }
            nomJoueur.setText(t1.getNom() + " :");
            hbox.getChildren().clear();
            for(Carte carte : t1.mainProperty().getValue()){
                Button b = new Button();
                b.setText(carte.getNom());

                vueCarte = new VueCarte(carte);
                vueCarte.ajouteImageABouton(b);

                hbox.getChildren().add(b);
                hbox.setAlignment(Pos.CENTER);
                EventHandler<? super MouseEvent> carteAction = mouseEvent -> {
                    System.out.println("Vous avez choisi " + carte.getNom());
                    getJeu().uneCarteAChoisirChoisie(carte.getNom());
                    t1.uneCarteDeLaMainAEteChoisie(carte.getNom());
                };
                b.setOnMouseClicked(carteAction);
            }
            t1.mainProperty().addListener(cartesListener);

            enleverListenerAll();
            ajouterListener(t1);

            updateAll();

            this.setBackground(new Background(new BackgroundFill(couleursJoueurs.convertiCouleurJoueur(t1.getCouleur()),CornerRadii.EMPTY, Insets.EMPTY)));
        });
    }

    private void ajouterListener(IJoueur joueur) {

        joueur.argentProperty().addListener(this.argentListener);
        joueur.nbJetonsRailsProperty().addListener(this.JetonsRailListener);
        joueur.scoreProperty().addListener(this.scoreListener);
        joueur.piocheProperty().addListener(this.piocheListener);
        joueur.defausseProperty().addListener(this.defausseListener);
    }

    private void enleverListenerAll() {

        for (IJoueur joueur : GestionJeu.getJeu().getJoueurs()) {

            joueur.argentProperty().removeListener(this.argentListener);
            joueur.nbJetonsRailsProperty().removeListener(this.JetonsRailListener);
            joueur.scoreProperty().removeListener(this.scoreListener);
            joueur.piocheProperty().removeListener(this.piocheListener);
            joueur.defausseProperty().removeListener(this.defausseListener);
        }
    }

    private void updateAll() {
        // argent
        this.argent.setText("" + GestionJeu.getJeu().joueurCourantProperty().get().argentProperty().get());
        // nb jetons rails
        this.nbRails.setText("" + GestionJeu.getJeu().joueurCourantProperty().get().nbJetonsRailsProperty().get());
        // score
        this.score.setText("" + GestionJeu.getJeu().joueurCourantProperty().get().scoreProperty().get());
        // pioche
        this.nbDeck.setText("" + GestionJeu.getJeu().joueurCourantProperty().get().piocheProperty().size());
        // defausse
        this.nbDefausse.setText("" + GestionJeu.getJeu().joueurCourantProperty().get().defausseProperty().size());
    }

    public IJeu getJeu() {
        return jeu;
    }

    public Button trouverBoutonCarte(ICarte carteATrouver){
        for(Node bouton : hbox.getChildren()){
            if(((Button) bouton).getText().equals(carteATrouver.getNom())) return ((Button) bouton);
        }
        return null;
    }

    EventHandler<? super MouseEvent> actionPasserParDefaut = mouseEvent -> {System.out.println("Vous avez choisi Passer");
        getJeu().passerAEteChoisi();};

    private final ListChangeListener<ICarte> cartesListener = change -> {
        while(change.next()){
            if (change.wasRemoved()) {
                for(ICarte carte : change.getRemoved()){
                    hbox.getChildren().remove(trouverBoutonCarte(carte));
                    System.out.println(carte.getNom() + " retirée");
                }
            }
        }
    };

}

