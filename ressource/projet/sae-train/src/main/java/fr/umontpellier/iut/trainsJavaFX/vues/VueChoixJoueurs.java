package fr.umontpellier.iut.trainsJavaFX.vues;

import fr.umontpellier.iut.trainsJavaFX.TrainsIHM;
import fr.umontpellier.iut.trainsJavaFX.mecanique.plateau.Plateau;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


/**
 * Cette classe correspond à une nouvelle fenêtre permettant de choisir le nombre et les noms des joueurs de la partie.
 * <p>
 * Sa présentation graphique peut automatiquement être actualisée chaque fois que le nombre de joueurs change.
 * Lorsque l'utilisateur a fini de saisir les noms de joueurs, il demandera à démarrer la partie.
 */
public class VueChoixJoueurs extends Application {

    private final ObservableList<String> nomsJoueurs;
    private Plateau plateauChoisi;
    private TextField nomJoueur;
    private Button valide;
    private Button annuler;
    private Button commencerPartie;
    private Button regle;
    private ComboBox<Plateau> comboBox;
    private TrainsIHM trainsIHM;
    private ImageView plateauImage;
    private Label explicationChoixPlateau;
    private Label explicationChoixJoueur;
    private ComboBox<Integer> zoom;
    private Label explicationZoom;
    private Stage stage;

    public VueChoixJoueurs(TrainsIHM trainsIHM) {
        nomsJoueurs = FXCollections.observableArrayList();
        this.trainsIHM=trainsIHM;
        plateauImage = new ImageView("images/OsakaSansContour.jpg");
        plateauImage.setFitHeight(150);
        plateauImage.setFitWidth(200);

        explicationZoom = new Label("Zoom ");
        explicationChoixPlateau = new Label("Choisissez un plateau de jeu !");
        explicationChoixJoueur = new Label("Entrer le nom d'un joueur");

        valide = new Button("Valider");
        annuler = new Button("Annuler");
        commencerPartie = new Button("Commencer la partie !");
        regle = new Button("Régles du jeu");

        nomJoueur = new TextField();
        nomJoueur.setPromptText("entrer le nom d'un joueur");
        nomJoueur.setMaxHeight(100);
        nomJoueur.setMaxWidth(200);

        zoom = new ComboBox<>();
        zoom.getItems().add(100);
        zoom.getItems().add(150);
        zoom.getItems().add(200);
        zoom.setValue(100);

        comboBox = new ComboBox<>();
        comboBox.getItems().add(0,Plateau.TOKYO);
        comboBox.getItems().add(1,Plateau.OSAKA);
        comboBox.setValue(Plateau.OSAKA);

        HBox champBouton = new HBox(valide,annuler);
        champBouton.setAlignment(Pos.CENTER);

        HBox hBoxZoom = new HBox(explicationZoom,zoom);
        hBoxZoom.setAlignment(Pos.CENTER);

        VBox champNomsJoueur = new VBox(
                hBoxZoom,
                explicationChoixPlateau,
                plateauImage,comboBox,
                explicationChoixJoueur,
                nomJoueur,
                champBouton,
                commencerPartie,
                regle);
        champNomsJoueur.setAlignment(Pos.CENTER);
        champNomsJoueur.setSpacing(10);

        creerBinding();

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(champNomsJoueur);
        stage = new Stage();
        Scene scene = new Scene(borderPane, 500, 450);
        stage.setScene(scene);
        stage.setMaxWidth(400);
        stage.setMinWidth(400);
        stage.setMaxHeight(500);
        stage.setMinHeight(500);
        stage.show();
    }

    private void creerBinding(){
        valide.setOnAction(actionEvent->{
            nomsJoueurs.add(nomJoueur.getText());
            nomJoueur.clear();
        });
        annuler.setOnAction(actionEvent-> nomJoueur.clear());
        commencerPartie.setOnAction(actionEvent -> {
            if(nomsJoueurs.isEmpty()){
                nomsJoueurs.add("joueur 1");
                nomsJoueurs.add("joueur 2");
            }
            plateauChoisi = comboBox.getValue();
            trainsIHM.demarrerPartie();
            stage.close();
        });
        regle.setOnAction(actionEvent -> {
            getHostServices().showDocument("https://www.youtube.com/watch?v=PfovGtlg-yg");
        });
        comboBox.valueProperty().addListener((ObservableValue,comboBox,newValue)->{
            if(newValue == Plateau.OSAKA){
               plateauImage.setImage(new Image("images/OsakaSansContour.jpg"));
            }else {
                plateauImage.setImage(new Image("images/TokyoSansContour.jpg"));
            }
        });
        zoom.valueProperty().addListener((ObservableValue,zoom,newValue)->{
            explicationChoixPlateau.setFont(new Font(newValue/6));
            explicationChoixJoueur.setFont(new Font(newValue/6));
            plateauImage.setFitHeight(newValue+50);
            plateauImage.setFitWidth(newValue+200);
            regle.setMinWidth(newValue+10);
            regle.setMaxWidth(newValue+10);
            valide.setMinWidth(newValue);
            valide.setMaxWidth(newValue);
            annuler.setMinWidth(newValue);
            annuler.setMaxWidth(newValue);
            stage.setMaxWidth(newValue+350);
            stage.setMinWidth(newValue+350);
            stage.setMaxHeight(newValue+450);
            stage.setMinHeight(newValue+450);
            comboBox.setMaxWidth(newValue+100);
            comboBox.setMinWidth(newValue);
            nomJoueur.setMaxWidth(newValue+100);
            nomJoueur.setMinWidth(newValue+100);
            commencerPartie.setMaxWidth(newValue+100);
            commencerPartie.setMinWidth(newValue+100);
        });
    }

    public List<String> getNomsJoueurs() {
        return nomsJoueurs;
    }

    /**
     * Définit l'action à exécuter lorsque la liste des participants est correctement initialisée
     */
    public void setNomsDesJoueursDefinisListener(ListChangeListener<String> quandLesNomsDesJoueursSontDefinis) {

    }

    /**
     * Vérifie que tous les noms des participants sont renseignés
     * et affecte la liste définitive des participants
     */
    protected void setListeDesNomsDeJoueurs() {
        ArrayList<String> tempNamesList = new ArrayList<>();
        for (int i = 1; i <= getNombreDeJoueurs() ; i++) {
            String name = getJoueurParNumero(i);
            if (name == null || name.equals("")) {
                tempNamesList.clear();
                break;
            }
            else
                tempNamesList.add(name);
        }
        if (!tempNamesList.isEmpty()) {
            stage.hide();
            nomsJoueurs.clear();
            nomsJoueurs.addAll(tempNamesList);
        }
    }

    /**
     * Retourne le nombre de participants à la partie que l'utilisateur a renseigné
     */
    protected int getNombreDeJoueurs() {
        return nomsJoueurs.size();
    }

    /**
     * Retourne le nom que l'utilisateur a renseigné pour le ième participant à la partie
     * @param playerNumber : le numéro du participant
     */
    protected String getJoueurParNumero(int playerNumber) {
        return nomsJoueurs.get(playerNumber);
    }

    public Plateau getPlateau() {
        return plateauChoisi;
    }

    @Override
    public void start(Stage stage) throws Exception {

    }
}
